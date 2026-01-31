package ch.fhnw.binarysearch.section;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ch.fhnw.binarysearch.engine.BinarySearchEngine;


public class ArrayVisualizationSection extends VBox {
    private Label[] arrayLabels;
    private Label[] iArrowLabels;
    private Label[] jArrowLabels;
    private Label variabelnLabel;
    private Label statusLabel;
    private int[] array;
    private BinarySearchEngine engine;

    //Konstruktor, Bestimmt die Position und Spacing von der Section
    public ArrayVisualizationSection(int[] array, BinarySearchEngine binarySearchEngine) {
        this.engine = binarySearchEngine;
        this.array = array;
        this.arrayLabels = new Label[array.length];
        //+2 weil das IndexArray [-1] und [array.length] beinhaltet
        this.iArrowLabels = new Label[array.length+2];
        this.jArrowLabels = new Label[array.length+2];

        setAlignment(Pos.CENTER);
        setSpacing(5);

        buildVisualization();
        buildLabels();
    }

    // Bildet das Main Array mit dem I oben und J unten Pfeil
    private void buildVisualization() {
        // i-Pfeile oben
        HBox iArrowBox = new HBox(5);
        iArrowBox.setAlignment(Pos.CENTER);

        // Array in der Mitte
        HBox arrayBox = new HBox(5);
        arrayBox.setAlignment(Pos.CENTER);

        // j-Pfeile unten
        HBox jArrowBox = new HBox(5);
        jArrowBox.setAlignment(Pos.CENTER);

        for (int i = -1; i <= array.length; i++) {
            // i-Pfeil Box
            Label iArrow = new Label("");
            iArrow.setMinWidth(50);
            iArrow.setAlignment(Pos.CENTER);
            iArrowLabels[i + 1] = iArrow; // weil i = -1 im Start
            iArrowBox.getChildren().add(iArrow);

            if (i >= 0 && i < array.length) {
                // Normale Array-Box mit Wert + Index
                VBox cell = new VBox(5);
                cell.setAlignment(Pos.CENTER);

                Label valueLabel = new Label(String.valueOf(array[i]));
                valueLabel.setMinSize(50, 50);
                valueLabel.setAlignment(Pos.CENTER);
                valueLabel.setStyle(
                        "-fx-border-color: #343d46;" +
                                "-fx-border-width: 2;" +
                                "-fx-background-color: white;" +
                                "-fx-font-weight: bold;" +
                                "-fx-font-size: 14px;"
                );
                arrayLabels[i] = valueLabel;

                Label indexLabel = new Label("[" + i + "]");
                indexLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #65737e;");

                cell.getChildren().addAll(valueLabel, indexLabel);
                arrayBox.getChildren().add(cell);
            } else {
                // Nur Index-Label für [-1] und [length]
                VBox leereZelle = new VBox(5);
                leereZelle.setAlignment(Pos.CENTER);
                leereZelle.setMinWidth(50);

                // Leere Zelle statt Box
                Label emptySpace = new Label("");
                emptySpace.setMinHeight(50);

                Label grenzenIndex = new Label("[" + i + "]");
                grenzenIndex.setStyle(
                        "-fx-font-size: 10px;" +
                                "-fx-text-fill: #a7adba;" +  // Heller = "virtuell"
                                "-fx-font-style: italic;"
                );

                leereZelle.getChildren().addAll(emptySpace, grenzenIndex);
                arrayBox.getChildren().add(leereZelle);
            }
            // j-Pfeil Box
            Label jArrow = new Label("");
            jArrow.setMinWidth(50);
            jArrow.setAlignment(Pos.CENTER);
            jArrowLabels[i + 1] = jArrow; //weil i = -1 im Start
            jArrowBox.getChildren().add(jArrow);
        }
        getChildren().addAll(iArrowBox, arrayBox, jArrowBox);
    }

    //Bildet die Variablen Anzeige unter dem Main Array
    private void buildLabels() {
        variabelnLabel = new Label("Variablen: i=?  m=?  j=?");
        variabelnLabel.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #343d46;"
        );
        statusLabel = new Label("");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        getChildren().addAll(variabelnLabel, statusLabel);
    }

    //Leitet weiter an die zuständigen Methoden zum aktualisieren vom neune Status des Algos
    public void updateState() {
        int i = engine.getI();
        int j = engine.getJ();
        int m = engine.getM();
        boolean found = engine.isFound();
        InvariantType invariantType = engine.getInvariant();

        updateArrayColors(i, j, m, found, invariantType);
        updateArrows(i, j);
        updateVariabelnLabel(i, j, m, invariantType);
        updateStatusLabel(i, j, m, found, invariantType);
    }

    //Aktualisiert die Werte, bei neuem Array
    public void updateArray(int[] newArray) {
        this.array = newArray;
        this.arrayLabels = new Label[newArray.length];
        this.iArrowLabels = new Label[newArray.length + 2];
        this.jArrowLabels = new Label[newArray.length + 2];

        getChildren().clear();

        buildVisualization();
        buildLabels();
    }

    private void updateVariabelnLabel(int i, int j, int m, InvariantType invariantType) {
        if (!invariantType.canContinue(i,j)) {
            variabelnLabel.setText(String.format(
                    "Variablen: i=%d > j=%d  →  Suchbereich leer!",
                    i, j
            ));
            variabelnLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: red;");
        } else {
            variabelnLabel.setText(String.format(
                    "Variablen: i=%d  m=%d  j=%d",
                    i, m, j
            ));
            variabelnLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        }
    }

    private void updateStatusLabel(int i, int j, int m, boolean found, InvariantType invariantType) {
        if (found) {
            statusLabel.setText("Gefunden bei Index " + m);
            statusLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: green;");
        } else if (!invariantType.canContinue(i,j)) {
            statusLabel.setText("Nicht gefunden");
            statusLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: red;");
        } else {
            statusLabel.setText("Suche läuft...");
            statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: normal; -fx-text-fill: gray;");
        }
    }

    private void updateArrows(int i, int j) {
        // i-Pfeile
        for (int idx = -1; idx <= array.length; idx++) {
            if (idx == i) {
                iArrowLabels[idx+1].setText("▼ i");
                iArrowLabels[idx+1].setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: blue;");
            } else {
                iArrowLabels[idx+1].setText("");
            }
        }

        // j-Pfeile
        for (int idx = -1; idx <= array.length; idx++) {
            if (idx == j) {
                jArrowLabels[idx+1].setText("▲ j");
                jArrowLabels[idx+1].setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: red;");
            } else {
                jArrowLabels[idx+1].setText("");
            }
        }
    }

    private void updateArrayColors(int i, int j, int m, boolean found, InvariantType invariant) {
        for (int idx = 0; idx < arrayLabels.length; idx++) {
            Label label = arrayLabels[idx];
            String style = "-fx-border-color: black; -fx-border-width: 2; ";
            if (!invariant.isInRange(idx,i,j)) {
                style += "-fx-background-color: #d3d3d3; -fx-text-fill: #888888;";
            } else if (idx == m && found) {
                style += "-fx-background-color: green;";
            } else if (idx == m) {
                style += "-fx-background-color: #FFD700;";
            } else {
                style += "-fx-background-color: white;";
            }
            label.setStyle(style);
        }
    }

    //nimmt die Error Message und Screened sie auf das Status Label
    public void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");
    }
}
