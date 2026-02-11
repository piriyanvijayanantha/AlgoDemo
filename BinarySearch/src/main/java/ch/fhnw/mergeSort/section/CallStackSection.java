package ch.fhnw.mergeSort.section;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;


public class CallStackSection extends VBox {

    private final Label titleLabel;
    private final VBox stackContainer;

    public CallStackSection() {
        setSpacing(8);
        setStyle(
                "-fx-background-color: white; -fx-border-color: #e0e0e0; " +
                        "-fx-border-radius: 8; -fx-background-radius: 8; " +
                        "-fx-padding: 15; -fx-border-width: 1;"
        );

        titleLabel = new Label("Call Stack");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        titleLabel.setStyle("-fx-text-fill: #343d46;");

        stackContainer = new VBox(4); // Enger Abstand zwischen Stack-Einträgen

        getChildren().addAll(titleLabel, stackContainer);
    }

    public void update(List<String> callStackEntries) {
        stackContainer.getChildren().clear();

        if (callStackEntries == null || callStackEntries.isEmpty()) {
            Label empty = new Label("(leer)");
            empty.setStyle("-fx-text-fill: #999; -fx-font-style: italic; -fx-padding: 4;");
            stackContainer.getChildren().add(empty);
            return;
        }

        // Stack von unten nach oben anzeigen (ältester Aufruf oben)
        for (int i = 0; i < callStackEntries.size(); i++) {
            boolean isActive = (i == callStackEntries.size() - 1); // Letzter = aktiv
            addStackItem(callStackEntries.get(i), isActive);
        }
    }

    private void addStackItem(String text, boolean active) {
        Label item = new Label(text);
        item.setFont(Font.font("Monospaced", active ? FontWeight.BOLD : FontWeight.NORMAL, 12));
        item.setMaxWidth(Double.MAX_VALUE);
        item.setStyle(
                "-fx-background-color: " + (active ? "#fff8e1" : "#f8f9fa") + "; " +
                        "-fx-padding: 6 10; " +
                        "-fx-border-radius: 4; -fx-background-radius: 4; " +
                        "-fx-border-width: 0 0 0 3; " +
                        "-fx-border-color: " + (active ? "#ffd700" : "#ddd") + ";"
        );
        stackContainer.getChildren().add(item);
    }
}