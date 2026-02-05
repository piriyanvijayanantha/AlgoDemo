package ch.fhnw.mergeSort.section;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VariablesSection extends VBox {

    public VariablesSection() {
        setPrefWidth(250);
        setPadding(new Insets(20));
        setSpacing(15);
        setStyle("-fx-background-color: #fafafa; -fx-border-color: #e0e0e0; -fx-border-width: 0 2 0 0;");

        // Variablen Section
        VBox variablesBox = createSection();

        createVariableRow(variablesBox, "left", "0");
        createVariableRow(variablesBox, "right", "8");
        createVariableRow(variablesBox, "mid", "4");
        createVariableRow(variablesBox, "depth", "0");

        // Phase Divide oder Merge
        Label phaseLabel = new Label("DIVIDE");
        phaseLabel.setStyle(
                "-fx-background-color: #ffe8e8; " +
                        "-fx-text-fill: #C4071B; " +
                        "-fx-padding: 6 12; " +
                        "-fx-border-radius: 4; " +
                        "-fx-background-radius: 4; " +
                        "-fx-font-weight: bold; "
        );
        variablesBox.getChildren().add(phaseLabel);

        VBox callStackBox = new CallStackSection();

        getChildren().addAll(variablesBox, callStackBox);
    }

    private VBox createSection() {
        VBox section = new VBox(10);
        section.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #e0e0e0; " +
                        "-fx-border-radius: 8; " +
                        "-fx-background-radius: 8; " +
                        "-fx-padding: 15; " +
                        "-fx-border-width: 1;"
        );

        Label titleLabel = new Label("Variablen");
        titleLabel.setStyle("-fx-text-fill: #343d46;");

        section.getChildren().add(titleLabel);
        return section;
    }

    private Label createVariableRow(VBox parent, String name, String value) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle(
                "-fx-background-color: #f8f9fa; " +
                        "-fx-padding: 8 12; " +
                        "-fx-border-radius: 4; " +
                        "-fx-background-radius: 4;"
        );

        Label nameLabel = new Label(name);

        Label valueLabel = new Label(value);
        valueLabel.setStyle(
                "-fx-text-fill: #0065A4; "
        );

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        row.getChildren().addAll(nameLabel, spacer, valueLabel);
        parent.getChildren().add(row);

        return valueLabel;
    }


}
