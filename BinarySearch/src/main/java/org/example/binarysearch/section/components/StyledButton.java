package org.example.binarysearch.section.components;

import javafx.scene.control.Button;

/**
 * Erbt von der Button Klasse. macht zusätzlich das Styling plus nimmt ein Runnable
 * um die Aktion des Buttons umzusetzen
 */
public class StyledButton extends Button {
    private final String color;

    public StyledButton(String text, Runnable action, String color) {
        super(text); //da Text im Button Konstruktor definiert wird
        this.color = color;

        setPrefWidth(120);
        setPrefHeight(35);
        applyStyle(color);
        setupHoverEffects();
        setOnAction(e -> action.run());
    }

    private void applyStyle(String backgroundColor) {
        setStyle(
                "-fx-background-color: " + backgroundColor + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;"
        );
    }

    //Hovereffekt -10%
    private void setupHoverEffects() {
        setOnMouseEntered(e -> applyStyle("derive(" + color + ", -10%)"));
        setOnMouseExited(e -> applyStyle(color));
    }
}