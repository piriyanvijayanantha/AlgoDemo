package org.example.binarysearch.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ControlSection extends VBox {
    //Konstruktor, bekommt die Runnables, einen Startknopf, für die Methoden in der GUI Klasse
    public ControlSection(Runnable onStart, Runnable onStep, Runnable onUndo) {
        setPadding(new Insets(20));
        setSpacing(10);
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-background-color: #c0c5ce;");

        Button startBtn = createButton("Start", onStart, "#343d46");
        Button nextStepBtn = createButton("Next Step ->", onStep, "#a7adba");
        Button undoBtn = createButton("<- Undo", onUndo, "#a7adba");
        getChildren().addAll(startBtn, nextStepBtn, undoBtn);
    }

    private Button createButton(String text, Runnable action, String color) {
        Button button = new Button(text);
        button.setPrefWidth(120);
        button.setPrefHeight(35);

        // Modernes Button-Design
        button.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: derive(" + color + ", -10%);" + //10% dünkeler
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;"
        ));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;"
        ));

        button.setOnAction(e -> action.run());
        return button;
    }
}
