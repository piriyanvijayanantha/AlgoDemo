package ch.fhnw.binarysearch.section;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import ch.fhnw.components.StyledButton;

public class ControlSection extends VBox {
    //Konstruktor, bekommt die Runnables, einen Startknopf, für die Methoden in der GUI Klasse
    public ControlSection(Runnable onStep, Runnable onUndo, Runnable onBack) {
        setPadding(new Insets(20));
        setSpacing(10);
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-background-color: #c0c5ce;");

        Button nextStepBtn = new StyledButton("Next Step ->", onStep, "#a7adba");
        Button undoBtn = new StyledButton("<- Undo", onUndo, "#a7adba");
        Button backBtn = new StyledButton("<- Menü", onBack, "#343d46");
        getChildren().addAll(nextStepBtn, undoBtn, backBtn);
    }
}
