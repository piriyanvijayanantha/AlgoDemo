package ch.fhnw.mergeSort.section;

import ch.fhnw.mergeSort.Engine.MergeState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Arrays;

public class ActionSection extends VBox {

    private final Label titleLabel;
    private final Label descriptionLabel;
    private final VBox detailContainer; // Für die visuelle Erklärung

    public ActionSection() {
        setPrefWidth(280);
        setPadding(new Insets(20));
        setSpacing(15);
        setStyle("-fx-background-color: #F1F1EE; -fx-border-color: #deded9; -fx-border-width: 0 0 0 2;");

        // Äussere Box
        VBox actionContainer = new VBox(12);
        actionContainer.setStyle(
                "-fx-background-color: white; -fx-border-color: #767573; " +  // Text und Border Grau
                        "-fx-border-radius: 8; -fx-background-radius: 8; " +
                        "-fx-padding: 20; -fx-border-width: 2;"
        );

        // Titel
        titleLabel = new Label("Aktion");
        titleLabel.setStyle("-fx-text-fill: #4c4c4c;"); // Dunkelgrau

        // Beschreibungstext
        descriptionLabel = new Label("Bereit zum Starten...");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setFont(Font.font("System", 13));
        descriptionLabel.setStyle("-fx-text-fill: #767573; -fx-line-spacing: 4px;"); // Text und Border Grau

        // Container für visuelle Details (Array-Darstellungen etc.)
        detailContainer = new VBox(10);
        detailContainer.setPadding(new Insets(10, 0, 0, 0));

        actionContainer.getChildren().addAll(titleLabel, descriptionLabel, detailContainer);
        getChildren().add(actionContainer);
    }

    public void update(MergeState state, MergeState prevState) {
        if (state == null) return;

        detailContainer.getChildren().clear();

        switch (state.getPhase()) {
            case MergeState.PHASE_START:
                showStartExplanation(state);
                break;
            case MergeState.PHASE_DIVIDE:
                showDivideExplanation(state);
                break;
            case MergeState.PHASE_MERGE:
                showMergeExplanation(state, prevState);
                break;
            case MergeState.PHASE_DONE:
                showDoneExplanation(state);
                break;
        }
    }


    private void showStartExplanation(MergeState state) {
        titleLabel.setText("Start");
        descriptionLabel.setText("Das Array ist bereit. Klicke 'Nächster Schritt' um mit dem Sortieren zu beginnen.");

        // Array anzeigen
        addArrayDisplay("Array:", state.getArray(), "#0065A4");  // FHNW Blau
    }

    private void showDivideExplanation(MergeState state) {
        titleLabel.setText("Divide");

        int left = state.getLeft();
        int right = state.getRight();
        int mid = state.getMid();
        int[] array = state.getArray();

        descriptionLabel.setText(String.format(
                "Teile den Bereich [%d..%d] in der Mitte (Index %d) in zwei Hälften.",
                left, right, mid
        ));

        // Linke Hälfte
        int[] leftPart = Arrays.copyOfRange(array, left, mid + 1);
        addArrayDisplay(String.format("Links [%d..%d]:", left, mid), leftPart, "#df305b");// Fehlerrot

        // Rechte Hälfte
        int[] rightPart = Arrays.copyOfRange(array, mid + 1, right + 1);
        addArrayDisplay(String.format("Rechts [%d..%d]:", mid + 1, right), rightPart, "#df305b");// Fehlerrot
    }

    private void showMergeExplanation(MergeState state, MergeState prevState) {
        titleLabel.setText("Merge");

        int left = state.getLeft();
        int right = state.getRight();
        int mid = state.getMid();

        descriptionLabel.setText(state.getDescription());

        // Werte VOR dem Merge (aus prevState oder aus Description)
        if (prevState != null) {
            int[] prevArray = prevState.getArray();
            int[] leftBefore = Arrays.copyOfRange(prevArray, left, mid + 1);
            int[] rightBefore = Arrays.copyOfRange(prevArray, mid + 1, right + 1);

            addArrayDisplay("Linke Hälfte:", leftBefore, "#df305b");// Fehlerrot
            addArrayDisplay("Rechte Hälfte:", rightBefore, "#df305b");// Fehlerrot
        }

        // Pfeil
        Label arrow = new Label("        ↓  Zusammenführen  ↓");
        arrow.setFont(Font.font("System", FontWeight.BOLD, 13));
        arrow.setStyle("-fx-text-fill: #0065A4;");// FHNW Blau
        detailContainer.getChildren().add(arrow);

        // Ergebnis NACH dem Merge
        int[] mergedResult = Arrays.copyOfRange(state.getArray(), left, right + 1);
        addArrayDisplay("Ergebnis:", mergedResult, "#0065A4");// FHNW Blau
    }

    private void showDoneExplanation(MergeState state) {
        titleLabel.setText("Fertig");
        descriptionLabel.setText("Das Array ist vollständig sortiert!");

        addArrayDisplay("Sortiertes Array:", state.getArray(), "#0065A4");// FHNW Blau
    }

    private void addArrayDisplay(String label, int[] values, String color) {
        Label arrayLabel = new Label(label);
        arrayLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        arrayLabel.setStyle("-fx-text-fill: #767573;");

        // Array-Boxen
        FlowPane boxes = new FlowPane(4, 4);
        boxes.setAlignment(Pos.CENTER_LEFT);

        for (int val : values) {
            Label box = new Label(String.valueOf(val));
            box.setFont(Font.font("System", FontWeight.BOLD, 14));
            box.setAlignment(Pos.CENTER);
            box.setMinWidth(35);
            box.setMinHeight(30);
            box.setStyle(String.format(
                    "-fx-border-color: %s; -fx-border-width: 2; -fx-border-radius: 4; " +
                            "-fx-background-radius: 4; -fx-background-color: white; -fx-text-fill: %s;",
                    color, color
            ));
            boxes.getChildren().add(box);
        }

        detailContainer.getChildren().addAll(arrayLabel, boxes);
    }
}