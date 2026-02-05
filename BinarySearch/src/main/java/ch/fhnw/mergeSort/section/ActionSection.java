package ch.fhnw.mergeSort.section;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ActionSection extends VBox {

    public ActionSection() {
        setPrefWidth(350);
        setPadding(new Insets(20));
        setSpacing(15);
        setStyle("-fx-background-color: #fafafa; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 0 2;");

        // Action Box
        VBox actionContainer = new VBox(15);
        actionContainer.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #e0e0e0; " +
                        "-fx-border-radius: 8; " +
                        "-fx-background-radius: 8; " +
                        "-fx-padding: 20; " +
                        "-fx-border-width: 2;"
        );

        // Titel
        Label title = new Label("Aktion");

        // Beschreibung
        Label descriptionLabel = new Label("Start: Array wird aufgeteilt...");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-text-fill: #4f5b66; -fx-font-size: 13px; -fx-line-spacing: 4px;");


        actionContainer.getChildren().addAll(title, descriptionLabel);
        getChildren().add(actionContainer);
    }
}

