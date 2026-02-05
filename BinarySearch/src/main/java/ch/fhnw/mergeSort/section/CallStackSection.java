package ch.fhnw.mergeSort.section;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CallStackSection extends VBox
{
    public CallStackSection() {
        setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #e0e0e0; " +
                        "-fx-border-radius: 8; " +
                        "-fx-background-radius: 8; " +
                        "-fx-padding: 15; " +
                        "-fx-border-width: 1;"
        );

        addCallStackItem("mergeSort(0, 8)", false);
        addCallStackItem("mergeSort(0, 4)", false);
        addCallStackItem("mergeSort(0, 2)", true);

    }

    private void addCallStackItem(String text, boolean active) {
        Label item = new Label(text);
        item.setStyle(
                "-fx-background-color: " + (active ? "#fff8e1" : "#f8f9fa") + "; " +
                        "-fx-padding: 8 10; " +
                        "-fx-border-radius: 4; " +
                        "-fx-border-width: 0 0 0 3; " +
                        "-fx-border-color: " + (active ? "#ffd700" : "#ccc") + "; " +
                        "-fx-font-weight: " + (active ? "bold" : "normal") + ";"
        );
        getChildren().add(item);
    }
}
