package org.example.binarysearch.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class InfoSection extends VBox {
    //Konstruktor, ist die ganze InfoBox. Infos wurden aktuell KI Generiert
    public InfoSection() {
        setPadding(new Insets(15));
        setSpacing(8);
        setStyle("-fx-background-color: #c0c5ce;" +
                "-fx-border-color: #65737e;"
        );
        setMaxWidth(250);

        Label title = createTitle();
        Label variablenInfo = createVariablenInfo();
        Label invarianteInfo = createInvarianteInfo();
        Label abbruchInfo = createAbbruchInfo();

        getChildren().addAll(title, variablenInfo, invarianteInfo, abbruchInfo);
    }

    private Label createTitle() {
        Label title = new Label("ℹ Algorithmus-Info");
        title.setStyle("-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-text-fill: #343d46;" +
                "-fx-padding: 0 0 10 0;");
        return title;
    }

    private Label createVariablenInfo() {
        Label label = new Label("""
                Variablen:
                • i = linke Grenze (inklusiv)
                • j = rechte Grenze (inklusiv)
                • m = Mitte: (i + j) / 2""");
        label.setStyle("-fx-font-size: 11px;" + "-fx-text-fill: #4f5b66;"
        );
        return label;
    }

    private Label createInvarianteInfo() {
        Label label = new Label("""
                Invariante:
                Der Suchwert liegt (wenn vorhanden)
                im Bereich [i..j].""");
        label.setStyle("-fx-font-size: 11px;" + "-fx-font-style: italic;" + "-fx-text-fill: #4f5b66;" +
                "-fx-padding: 8 0 8 0;");
        return label;
    }

    private Label createAbbruchInfo() {
        Label label = new Label("""
                Abbruch:
                • i > j → nicht gefunden
                • array[m] == target → gefunden""");
        label.setStyle("-fx-font-size: 11px;" + "-fx-text-fill: #4f5b66;"
        );
        return label;
    }
}
