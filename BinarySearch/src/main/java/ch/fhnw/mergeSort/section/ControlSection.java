package ch.fhnw.mergeSort.section;

import ch.fhnw.components.StyledButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ControlSection extends HBox {

    public ControlSection(Runnable onReset, Runnable onUndo, Runnable onStep) {
        setAlignment(Pos.CENTER);
        setPadding(new Insets(15, 20, 15, 20));
        setSpacing(15);
        setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #343d46; -fx-border-width: 0 0 2 0;");

        // Buttons
        StyledButton resetBtn = new StyledButton("Reset", onReset, "#a7adba");
        StyledButton undoBtn = new StyledButton("Undo", onUndo, "#a7adba");
        StyledButton stepBtn = new StyledButton("Nächste Step", onStep, "#a7adba");

        // Spacer
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        // Step Counter
        Label stepLabel = new Label("Schritt 1 von 32");

        getChildren().addAll(resetBtn, undoBtn, stepBtn, spacer, stepLabel);
    }
}
