package org.example.binarysearch;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.binarysearch.engine.BinarySearchEngine;

public class BinarySearchGUI extends Application {
    private BinarySearchEngine binarySearchEngine;
    private int[] array;
    private Label[] arrayLabels;
    private Label variabelnLabel;
    private TextField arrayInput;
    private TextField targetInput;
    private Label statusLabel;
    private BorderPane root;
    private HBox arrayBox;
    private Label[] arrowLabels;

    @Override
    public void start(Stage stage) throws Exception {
        binarySearchEngine = new BinarySearchEngine();
        root = new BorderPane();
        root.setTop(createInputSection());
        root.setCenter(createVisualizationSection());
        root.setRight(createControlSection());
        Scene scene = new Scene(root, 900, 500);
        stage.setTitle("Binary Search Demonstrator");
        stage.setScene(scene);
        stage.show();
    }

    private HBox createInputSection() {
        HBox inputBox = new HBox(10);
        inputBox.setPadding(new Insets(15));
        inputBox.setStyle("-fx-background-color: white;");

        Label arrayLabel = new Label("Sortiertes Array: ");

        arrayInput = new TextField();
        arrayInput.setText("1, 3, 4, 5, 8, 11, 13");
        arrayInput.setPrefWidth(300);

        Label targetLabel = new Label("Suchwert: ");
        targetInput = new TextField();
        targetInput.setText("11");
        Button button = new Button("Anwenden");
        button.setOnAction(e -> handleApply());

        inputBox.getChildren().addAll(arrayLabel, arrayInput, targetLabel, targetInput, button);
        return inputBox;
    }

    private VBox createVisualizationSection() {
        VBox visualizationBox = new VBox(20);
        visualizationBox.setPadding(new Insets(20));
        visualizationBox.setAlignment(Pos.CENTER);

        arrayBox = new HBox(5);
        arrayBox.setAlignment(Pos.CENTER);

        array = formatTextToArray(arrayInput.getText().split(","));

        arrayLabels = new Label[array.length];

        buildArrayBoxes(array, arrayBox, visualizationBox);

        variabelnLabel = new Label("Variablen: i=?  m=?  j=?");
        variabelnLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        statusLabel = new Label("");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: green;");

        visualizationBox.getChildren().addAll(variabelnLabel, statusLabel);
        return visualizationBox;
    }

    private VBox createControlSection() {
        VBox controlBox = new VBox(10);
        controlBox.setPadding(new Insets(20));
        controlBox.setAlignment(Pos.TOP_CENTER);
        controlBox.setStyle("-fx-background-color: #e0e0e0;");

        Button startBtn = new Button("Start");
        startBtn.setPrefWidth(100);
        startBtn.setOnAction(e -> handleStart());

        Button nextStepBtn = new Button("Next Step");
        nextStepBtn.setPrefWidth(100);
        nextStepBtn.setOnAction(e -> handleStep());


        Button undoBtn = new Button("Undo");
        undoBtn.setPrefWidth(100);
        undoBtn.setOnAction(e -> {
            if (binarySearchEngine.undo()) {
                updateDisplay();
            } else {
                statusLabel.setText("Keine vorherigen Schritte!");
                statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");
            }
        });

        Button resetBtn = new Button("Reset");
        resetBtn.setPrefWidth(100);
        resetBtn.setOnAction(e -> handleReset());  // ← Das fehlte!

        controlBox.getChildren().addAll(startBtn, resetBtn, undoBtn, nextStepBtn);
        return controlBox;
    }

    private void handleStart() {
        binarySearchEngine.start(array, Integer.parseInt(targetInput.getText()));
        System.out.println("Start gedrückt!");
        updateDisplay();
    }

    private void handleStep() {
        binarySearchEngine.step();
        updateDisplay();
    }

    private void handleApply() {
        try {
            array = formatTextToArray(arrayInput.getText().split(","));

            //Suchwert validieren
            String targetText = targetInput.getText().trim();
            if (targetText.isEmpty()) {
                throw new IllegalArgumentException("Suchwert darf nicht leer sein");
            }
            int newTarget = Integer.parseInt(targetText);

            //Array sortiert?
            if (!isSorted(array)) {
                throw new IllegalArgumentException("Array muss sortiert sein!");
            }

            binarySearchEngine.start(array, newTarget);
            root.setCenter(createVisualizationSection());

            statusLabel.setText("Array und Suchwert erfolgreich geladen");
            statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: green;");

        } catch (NumberFormatException e) {
            statusLabel.setText("Suchwert muss eine ganze Zahl sein");
            statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: red;");
        } catch (IllegalArgumentException e) {
            // Fehler-Feedback (Rot)
            statusLabel.setText("✗ " + e.getMessage());
            statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: red;");
        }
    }

    private void handleReset() {
        binarySearchEngine.start(array, Integer.parseInt(targetInput.getText()));

        for (Label label : arrayLabels) {
            label.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white;");
        }
        variabelnLabel.setText(String.format("Variablen: i=%d  m=%d  j=%d",
                binarySearchEngine.getI(),
                binarySearchEngine.getM(),
                binarySearchEngine.getJ()));
    }

    private void updateDisplay() {
        int engineM = binarySearchEngine.getM();
        int engineI = binarySearchEngine.getI();
        int engineJ = binarySearchEngine.getJ();
        for (int i = 0; i < arrayLabels.length; i++) {
            Label label = arrayLabels[i];
            String style = "-fx-border-color: black; -fx-border-width: 2; ";

            if (i < engineI || i > engineJ) {
                // Außerhalb des Suchbereichs → ausgrauen
                style += "-fx-background-color: #d3d3d3; -fx-text-fill: #888888;";
            } else if (i == engineM && binarySearchEngine.isFound()) {
                // ← GEÄNDERT: m gefunden → grün
                style += "-fx-background-color: green;"; // Hellgrün
            } else if (i == engineM) {
                // m noch am Suchen → gold
                style += "-fx-background-color: #FFD700;";
            } else {
                // Im Suchbereich aber nicht m → weiß
                style += "-fx-background-color: white;";
            }

            label.setStyle(style);
        }

        for (int i = 0; i < arrowLabels.length; i++) {
            if (i == engineI) {
                arrowLabels[i].setText("▲ i");  // Links von Element i
                arrowLabels[i].setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: blue;");
            } else if (i == engineJ + 1) {  // ← WICHTIG: j+1 für rechts vom Element j
                arrowLabels[i].setText("▲ j");
                arrowLabels[i].setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: red;");
            } else {
                arrowLabels[i].setText("");
            }
        }

        variabelnLabel.setText(String.format("Variablen: i=%d  m=%d  j=%d",
                binarySearchEngine.getI(),
                binarySearchEngine.getM(),
                binarySearchEngine.getJ()));
        if (binarySearchEngine.isFound()) {
            statusLabel.setText("Gefunden bei Index " + binarySearchEngine.getM() + "!");
            statusLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: green;");
        } else if (binarySearchEngine.getI() > binarySearchEngine.getJ()) {
            statusLabel.setText("Nicht gefunden!");
            statusLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: red;");
        } else {
            statusLabel.setText("Suche läuft...");
            statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: normal; -fx-text-fill: gray;");
        }
    }

    private int[] formatTextToArray(String[] parts) {
        int[] newArray = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            String trimmed = parts[i].trim();
            if (trimmed.isEmpty()) {
                throw new IllegalArgumentException("Leeres Glied an Position: " + i);
            }
            if (!trimmed.matches("-?\\d+")) { //Regex:  eine oder mehrere ganze Zahlen ohne Dezimal
                throw new IllegalArgumentException("Wert: '" + trimmed + "' auf position " + i + " ist keine ganze Zahl");
            }
            newArray[i] = Integer.parseInt(trimmed);
        }
        return newArray;
    }

    private void buildArrayBoxes(int[] array, HBox arrayBox, VBox visualizationBox) {
        for (int i = 0; i < array.length; i++) {
            VBox cell = new VBox(5);
            cell.setAlignment(Pos.CENTER);

            Label valueLabel = new Label(String.valueOf(array[i]));
            valueLabel.setMinSize(50, 50);
            valueLabel.setAlignment(Pos.CENTER);
            valueLabel.setStyle("-fx-border-color: black; -fx-border-width: 2;");

            Label indexLabel = new Label("[" + i + "]");
            indexLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: gray;");

            cell.getChildren().addAll(valueLabel, indexLabel);
            arrayBox.getChildren().add(cell);

            arrayLabels[i] = valueLabel;
        }
        visualizationBox.getChildren().add(arrayBox);

        //Pfeile
        HBox arrowBox = new HBox(5);
        arrowBox.setAlignment(Pos.CENTER);
        arrowLabels = new Label[array.length + 1]; // +1 für Position nach letztem Element

        for (int i = 0; i <= array.length; i++) {
            Label arrow = new Label("");
            arrow.setMinWidth(50);
            arrow.setAlignment(Pos.CENTER);
            arrow.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            arrowLabels[i] = arrow;
            arrowBox.getChildren().add(arrow);
        }

        visualizationBox.getChildren().add(arrowBox);
    }

    private boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
