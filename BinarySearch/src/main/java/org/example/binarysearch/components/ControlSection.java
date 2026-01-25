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
        setStyle("-fx-background-color: white;");

        Button startBtn = createButton("Start", onStart);
        Button nextStepBtn = createButton("Next Step", onStep);
        Button undoBtn = createButton("Undo", onUndo);
        getChildren().addAll(startBtn, nextStepBtn, undoBtn);
    }

    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setPrefWidth(100);
        button.setOnAction(e -> action.run());
        return button;
    }
}
