package org.example.binarysearch.section;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class ArrayVisualizationSection extends VBox {
    private Label[] arrayLabels;
    private Label[] iArrowLabels;
    private Label[] jArrowLabels;
    private Label variabelnLabel;
    private Label statusLabel;
    private int[] array;

    //Konstruktor, Bestimmt die Position und Spacing von der Section
    public ArrayVisualizationSection(int[] array) {
        this.array = array;
        this.arrayLabels = new Label[array.length];
        this.iArrowLabels = new Label[array.length];
        this.jArrowLabels = new Label[array.length];

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

        for (int i = 0; i < array.length; i++) {
            // i-Pfeil
            Label iArrow = new Label("");
            iArrow.setMinWidth(50);
            iArrow.setAlignment(Pos.CENTER);
            iArrowLabels[i] = iArrow;
            iArrowBox.getChildren().add(iArrow);

            // ArrayValue Element
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

            // Index Element
            Label indexLabel = new Label("[" + i + "]");
            indexLabel.setStyle(
                    "-fx-font-size: 10px;" +
                            "-fx-text-fill: #65737e;"
            );
            cell.getChildren().addAll(valueLabel, indexLabel);
            arrayBox.getChildren().add(cell);

            // j-Pfeil
            Label jArrow = new Label("");
            jArrow.setMinWidth(50);
            jArrow.setAlignment(Pos.CENTER);
            jArrowLabels[i] = jArrow;
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

    //Aktualisiert die Werte, bei neuem Array
    public void updateArray(int[] newArray) {
        this.array = newArray;
        this.arrayLabels = new Label[newArray.length];
        this.iArrowLabels = new Label[newArray.length];
        this.jArrowLabels = new Label[newArray.length];

        getChildren().clear();

        buildVisualization();
        buildLabels();
    }

    //Leitet weiter an die zuständigen Methoden zum aktualisieren vom neune Status des Algos
    public void updateState(int i, int j, int m, boolean found) {
        updateArrayColors(i, j, m, found);
        updateArrows(i, j);
        updateVariabelnLabel(i, j, m);
        updateStatusLabel(i, j, m, found);
    }

    private void updateVariabelnLabel(int i, int j, int m) {
        if (i > j) {
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

    private void updateStatusLabel(int i, int j, int m, boolean found) {
        if (found) {
            statusLabel.setText("Gefunden bei Index " + m);
            statusLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: green;");
        } else if (i > j) {
            statusLabel.setText("Nicht gefunden");
            statusLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: red;");
        } else {
            statusLabel.setText("Suche läuft...");
            statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: normal; -fx-text-fill: gray;");
        }
    }

    private void updateArrows(int i, int j) {
        // i-Pfeile
        for (int idx = 0; idx < iArrowLabels.length; idx++) {
            if (idx == i) {
                iArrowLabels[idx].setText("▼ i");
                iArrowLabels[idx].setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: blue;");
            } else {
                iArrowLabels[idx].setText("");
            }
        }

        // j-Pfeile
        for (int idx = 0; idx < jArrowLabels.length; idx++) {
            if (idx == j) {
                jArrowLabels[idx].setText("▲ j");
                jArrowLabels[idx].setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: red;");
            } else {
                jArrowLabels[idx].setText("");
            }
        }
    }

    private void updateArrayColors(int i, int j, int m, boolean found) {
        for (int idx = 0; idx < arrayLabels.length; idx++) {
            Label label = arrayLabels[idx];
            String style = "-fx-border-color: black; -fx-border-width: 2; ";

            if (idx < i || idx > j) {
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
