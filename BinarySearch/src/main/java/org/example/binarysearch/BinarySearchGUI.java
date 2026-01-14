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

public class BinarySearchGUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
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
        inputBox.setStyle("-fx-background-color: #f0f0f0;");

        Label arrayLabel = new Label("Sortiertes Array: ");

        TextField arrayInput = new TextField();
        arrayInput.setText("1, 3, 4, 5, 8, 11, 13");
        arrayInput.setPrefWidth(300);

        Label targetLabel = new Label("Suchwert: ");
        TextField targetInput = new TextField();
        targetInput.setText("11");
        Button button = new Button("Anwenden");

        inputBox.getChildren().addAll(arrayLabel, arrayInput, targetLabel, targetInput, button);
        return inputBox;
    }

    private VBox createVisualizationSection() {
        VBox visualizationBox = new VBox(20);
        visualizationBox.setPadding(new Insets(20));
        visualizationBox.setAlignment(Pos.CENTER);

        HBox arrayBox = new HBox(5);
        arrayBox.setAlignment(Pos.CENTER);

        int[] testArray = {1, 3, 4, 5, 8, 11, 13};
        Label[] arrayLabels = new Label[testArray.length];

        for (int i = 0; i < testArray.length; i++) {
            VBox cell = new VBox(5);
            cell.setAlignment(Pos.CENTER);

            // Wert
            Label valueLabel = new Label(String.valueOf(testArray[i]));
            valueLabel.setMinSize(50, 50);
            valueLabel.setAlignment(Pos.CENTER);
            valueLabel.setStyle("-fx-border-color: black; -fx-border-width: 2;");

            // Index
            Label indexLabel = new Label("[" + i + "]");
            indexLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: gray;");

            cell.getChildren().addAll(valueLabel, indexLabel);
            arrayBox.getChildren().add(cell);

            arrayLabels[i] = valueLabel;
        }

        Label varLabel = new Label("Variablen: i=0  m=3  j=6");
        varLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        visualizationBox.getChildren().addAll(arrayBox, varLabel);
        return visualizationBox;
    }

    private VBox createControlSection() {
        VBox controlBox = new VBox(10);
        controlBox.setPadding(new Insets(20));
        controlBox.setAlignment(Pos.TOP_CENTER);
        controlBox.setStyle("-fx-background-color: #e0e0e0;");

        Button startBtn = new Button("Start");
        startBtn.setPrefWidth(100);
//        startBtn.setOnAction(e -> startEngine());

        Button nextStepBtn = new Button("Next Step");
        nextStepBtn.setPrefWidth(100);

        Button undoBtn = new Button("Undo");
        undoBtn.setPrefWidth(100);

        Button resetBtn = new Button("Reset");
        resetBtn.setPrefWidth(100);

        controlBox.getChildren().addAll(startBtn, resetBtn, undoBtn, nextStepBtn);
        return controlBox;
    }
}
