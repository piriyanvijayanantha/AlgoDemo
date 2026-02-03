package ch.fhnw.binarysearch.section;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class InfoSection extends VBox {
    private Label invariantName;
    private Label abortInfo;
    private VBox updateBox;

    public InfoSection() {
        setPadding(new Insets(20));
        setSpacing(12);
        setAlignment(Pos.TOP_LEFT);
        setPrefWidth(270);
        setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #343d46; -fx-border-width: 0 1 0 0;");

        // Titel
        Label title = createTitle("Invariante");
        invariantName = createHighlight();
        // Abbruch
        Label abortTitle = createTitle("Abbruch wenn");
        abortInfo = createHighlight();

        // Updates
        Label updateTitle = createTitle("Update-Regeln");
        updateBox = new VBox(5);
        updateBox.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-color: #65737e; -fx-border-radius: 5; -fx-background-radius: 5;");

        getChildren().addAll(
                title, invariantName,
                new Separator(),
                abortTitle, abortInfo,
                new Separator(),
                updateTitle, updateBox
        );

        updateInfo(InvariantType.BOTH_INCLUSIVE);
    }

    private Label createTitle(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        label.setStyle("-fx-text-fill: #4f5b66;");
        return label;
    }

    private Label createHighlight() {
        Label label = new Label("");
        label.setFont(Font.font("Monospace", FontWeight.BOLD, 13));
        label.setStyle("-fx-text-fill: #0065A4; -fx-background-color: #e8f4ff; -fx-padding: 5 10; -fx-background-radius: 3;");
        return label;
    }

    private HBox createUpdateRow(String symbol, String value) {
        HBox row = new HBox(8);
        row.setAlignment(Pos.CENTER_LEFT);

        Label symbolLabel = new Label(symbol);
        symbolLabel.setFont(Font.font("Monospace", FontWeight.BOLD, 12));
        symbolLabel.setStyle("-fx-text-fill: #C4071B;");
        symbolLabel.setMinWidth(15);

        Label arrow = new Label("→");
        arrow.setStyle("-fx-text-fill: #65737e;");

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Monospace", 12));
        valueLabel.setStyle("-fx-text-fill: #343d46;");

        row.getChildren().addAll(symbolLabel, arrow, valueLabel);
        return row;
    }

    public void updateInfo(InvariantType invariant) {
        invariantName.setText(invariant.toString());

        switch (invariant) {
            case BOTH_INCLUSIVE -> {
                abortInfo.setText("i > j");
                updateBox.getChildren().clear();
                updateBox.getChildren().addAll(
                        createUpdateRow("j", "m - 1  (wenn < m)"),
                        createUpdateRow("i", "m + 1  (wenn > m)")
                );
            }
            case LEFT_INCLUSIVE -> {
                abortInfo.setText("i ≥ j");
                updateBox.getChildren().clear();
                updateBox.getChildren().addAll(
                        createUpdateRow("j", "m  (wenn < m)"),
                        createUpdateRow("i", "m + 1  (wenn > m)")
                );
            }
            case BOTH_EXCLUSIVE -> {
                abortInfo.setText("i + 1 ≥ j");
                updateBox.getChildren().clear();
                updateBox.getChildren().addAll(
                        createUpdateRow("j", "m  (wenn < m)"),
                        createUpdateRow("i", "m  (wenn > m)")
                );
            }
            case RIGHT_INCLUSIVE -> {
                abortInfo.setText("i ≥ j");
                updateBox.getChildren().clear();
                updateBox.getChildren().addAll(
                        createUpdateRow("j", "m - 1  (wenn < m)"),
                        createUpdateRow("i", "m  (wenn > m)")
                );
            }
        }
    }
}