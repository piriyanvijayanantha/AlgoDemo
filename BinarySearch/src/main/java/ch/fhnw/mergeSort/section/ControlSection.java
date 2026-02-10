package ch.fhnw.mergeSort.section;

import ch.fhnw.components.ArrayInputComponent;
import ch.fhnw.components.StyledButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ControlSection extends VBox {
    private final ArrayInputComponent arrayInput;
    private final StyledButton resetBtn;
    private final StyledButton undoBtn;
    private final StyledButton stepBtn;
    private final StyledButton applyBtn;
    private final Label stepLabel;

    public ControlSection(Runnable onReset, Runnable onUndo, Runnable onStep, Runnable onApply) {
        setPadding(new Insets(15, 20, 15, 20));
        setSpacing(10);
        setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #343d46; -fx-border-width: 0 0 2 0;");

        // Zeile 1: Array-Eingabe
        arrayInput = new ArrayInputComponent(false, "5, 2, 7, 9, 6, 2, 1, 0, 8");
        HBox arrayRow = arrayInput.getInputRow();
        applyBtn = new StyledButton("Anwenden", onApply, "#0065A4");
        arrayRow.getChildren().add(applyBtn);

        // Zeile 2: Buttons + Schritt-Anzeige
        HBox buttonRow = new HBox(15);
        buttonRow.setAlignment(Pos.CENTER);

        resetBtn = new StyledButton("Reset", onReset, "#a7adba");
        undoBtn = new StyledButton("Undo", onUndo, "#a7adba");
        stepBtn = new StyledButton("Nächste Step", onStep, "#a7adba");

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        stepLabel = new Label("Schritt 1 von ?");

        buttonRow.getChildren().addAll(resetBtn, undoBtn, stepBtn, spacer, stepLabel);

        // Beide Zeilen in die VBox
        getChildren().addAll(arrayRow, buttonRow);
    }

    public void update(int currentStep, int totalSteps, boolean canForward, boolean canBackward) {
        stepLabel.setText(String.format("Schritt %d von %d", currentStep, totalSteps));

        // Buttons aktivieren/deaktivieren
        undoBtn.setDisable(!canBackward);
        stepBtn.setDisable(!canForward);
        resetBtn.setDisable(!canBackward); // Reset nur wenn nicht am Anfang
    }

    public int[] getArray() throws IllegalArgumentException {
        return arrayInput.getArray();
    }
}
