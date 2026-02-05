package ch.fhnw.mergeSort.section;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RecursionTreeSection extends VBox {

    public RecursionTreeSection() {
        setPadding(new Insets(20));
        setSpacing(20);
        setStyle("-fx-background-color: white;");
        setAlignment(Pos.TOP_CENTER);

        // Titel
        Label title = new Label("Rekursionsbaum");

        // Container für Baum
        VBox treeContainer = new VBox(20);
        treeContainer.setAlignment(Pos.TOP_CENTER);

        Label placeholder = new Label("Rekursionsbaum kommt hier");
        treeContainer.getChildren().add(placeholder);

        getChildren().addAll(title, treeContainer);
    }
}
