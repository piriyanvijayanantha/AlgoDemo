package ch.fhnw.mergeSort.section;

import ch.fhnw.mergeSort.Engine.MergeState;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.awt.*;


public class VariablesSection extends VBox {

    // Labels für die Variablenwerte (werden bei update() aktualisiert)
    private Label leftValue;
    private Label rightValue;
    private Label midValue;
    private Label depthValue;
    private Label phaseLabel;

    // CallStack-Sektion (wird ebenfalls bei update() aktualisiert)
    private CallStackSection callStackSection;

    public VariablesSection() {
        setPrefWidth(220);
        setMinWidth(200);
        setMaxWidth(240);
        setPadding(new javafx.geometry.Insets(20));
        setSpacing(15);
        setStyle("-fx-background-color: #fafafa; -fx-border-color: #e0e0e0; -fx-border-width: 0 2 0 0;");

        // Variablen-Box
        VBox variablesBox = createStyledBox();

        Label title = new Label("Variablen");
        title.setFont(Font.font("System", FontWeight.BOLD, 14));
        title.setStyle("-fx-text-fill: #343d46;");
        variablesBox.getChildren().add(title);

        // Variablen-Zeilen erstellen
        leftValue = createVariableRow(variablesBox, "left", "-", "#df305b");
        midValue = createVariableRow(variablesBox, "mid", "-", "#EA8700");
        rightValue = createVariableRow(variablesBox, "right", "-", "#0065A4");
        depthValue = createVariableRow(variablesBox, "depth", "-", "black");

        // Phase-Badge
        phaseLabel = new Label("-");
        phaseLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        phaseLabel.setStyle(
                "-fx-background-color: #f0f0f0; -fx-text-fill: #666; " +
                        "-fx-padding: 6 12; -fx-border-radius: 4; -fx-background-radius: 4;"
        );
        variablesBox.getChildren().add(phaseLabel);

        // CallStack-Box
        callStackSection = new CallStackSection();

        getChildren().addAll(variablesBox, callStackSection);
    }

    public void update(MergeState state) {
        if (state == null) return;

        // Variablenwerte setzen
        leftValue.setText(String.valueOf(state.getLeft()));
        rightValue.setText(String.valueOf(state.getRight()));
        depthValue.setText(String.valueOf(state.getDepth()));

        // Mid ist nur bei DIVIDE und MERGE relevant
        if (state.getMid() >= 0) {
            midValue.setText(String.valueOf(state.getMid()));
        } else {
            midValue.setText("-");
        }

        // Phase-Badge aktualisieren (mit passender Farbe)
        updatePhaseLabel(state.getPhase());

        // CallStack aktualisieren
        callStackSection.update(state.getCallStack());
    }

    private void updatePhaseLabel(String phase) {
        phaseLabel.setText(phase);

        switch (phase) {
            case MergeState.PHASE_DIVIDE:
                phaseLabel.setStyle(
                        "-fx-background-color: #ffe8e8; -fx-text-fill: #C4071B; " +
                                "-fx-padding: 6 12; -fx-border-radius: 4; -fx-background-radius: 4; " +
                                "-fx-font-weight: bold;"
                );
                break;
            case MergeState.PHASE_MERGE:
                phaseLabel.setStyle(
                        "-fx-background-color: #e8f5e9; -fx-text-fill: #2e7d32; " +
                                "-fx-padding: 6 12; -fx-border-radius: 4; -fx-background-radius: 4; " +
                                "-fx-font-weight: bold;"
                );
                break;
            default:
                phaseLabel.setStyle(
                        "-fx-background-color: #e3f2fd; -fx-text-fill: #0065A4; " +
                                "-fx-padding: 6 12; -fx-border-radius: 4; -fx-background-radius: 4; " +
                                "-fx-font-weight: bold;"
                );
                break;
        }
    }


    // Erstellt eine weiss-umrandete Box
    private VBox createStyledBox() {
        VBox box = new VBox(10);
        box.setStyle(
                "-fx-background-color: white; -fx-border-color: #e0e0e0; " +
                        "-fx-border-radius: 8; -fx-background-radius: 8; " +
                        "-fx-padding: 15; -fx-border-width: 1;"
        );
        return box;
    }

    //Erstellt eine Zeile: Name (links) ... Wert (rechts, blau)
    private Label createVariableRow(VBox parent, String name, String initialValue, String color) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle(
                "-fx-background-color: #f8f9fa; -fx-padding: 8 12; " +
                        "-fx-border-radius: 4; -fx-background-radius: 4;"
        );

        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("System", FontWeight.NORMAL, 13));

        Label valueLabel = new Label(initialValue);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
        valueLabel.setStyle("-fx-text-fill: " + color);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        row.getChildren().addAll(nameLabel, spacer, valueLabel);
        parent.getChildren().add(row);

        return valueLabel; // Wird für spätere Updates zurückgegeben
    }
}