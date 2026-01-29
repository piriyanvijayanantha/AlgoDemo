package org.example.binarysearch.section;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.example.binarysearch.section.components.StyledButton;

public class InputSection extends HBox {
    private TextField arrayInput;
    private TextField targetInput;
    private Label errorLabel;

    //Konstruktor, Nimmt ein Array und ein Target als Input, bei knopdruck wird das Runnable ausgeführt.
    public InputSection(Runnable onApplyCallback) {
        setPadding(new Insets(15));
        setSpacing(10);
        setStyle("-fx-background-color: #65737e;");
        Label arrayLabel = new Label("Sortiertes Array:");
        arrayLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");


        arrayInput = new TextField();
        arrayInput.setText("1, 3, 4, 5, 8, 11, 13");
        arrayInput.setPrefWidth(300);
        arrayInput.setStyle(
                "-fx-background-radius: 5;" +
                        "-fx-padding: 5;"
        );

        Label targetLabel = new Label("Suchwert:");
        targetLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");


        targetInput = new TextField();
        targetInput.setText("11");
        targetInput.setStyle(
                "-fx-background-radius: 5;" +
                        "-fx-padding: 5;"
        );

        Button applyButton = new StyledButton("Anwenden", onApplyCallback, "#343d46");

        errorLabel = new Label("");
        errorLabel.setVisible(false);

        getChildren().addAll(arrayLabel, arrayInput, targetLabel, targetInput, applyButton, errorLabel);
    }

    //Trennung des Strings in einzelstücke für ein Array
    public int[] getArray() throws IllegalArgumentException {
        String[] parts = arrayInput.getText().split(",");
        return formatTextToArray(parts);
    }
    //Nimmt den Target Input mit Validierung
    public int getTarget() throws IllegalArgumentException {
        String targetText = targetInput.getText().trim();
        if (targetText.isEmpty()) {
            throw new IllegalArgumentException("Suchwert darf nicht leer sein");
        }
        try {
            return Integer.parseInt(targetText);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Suchwert muss eine ganze Zahl sein");
        }
    }

    public void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-color: #C4071B;" +
                        "-fx-padding: 8;" +
                        "-fx-background-radius: 5;" +
                        "-fx-border-color: #343d46;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        );
        errorLabel.setVisible(true);
    }
    public void showSuccess(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-color: #6ba43a;" +
                        "-fx-padding: 8;" +
                        "-fx-background-radius: 5;" +
                        "-fx-border-color: #343d46;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        );
        errorLabel.setVisible(true);
    }
    public void clearMessage() {
        errorLabel.setVisible(false);
    }
    private int[] formatTextToArray(String[] parts) throws IllegalArgumentException {
        int[] newArray = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            String trimmed = parts[i].trim();
            if (trimmed.isEmpty()) {
                throw new IllegalArgumentException("Leeres Glied an Position: " + i);
            }
            if (!trimmed.matches("-?\\d+")) {
                throw new IllegalArgumentException("Wert: '" + trimmed + "' auf Position " + i + " ist keine ganze Zahl");
            }
            newArray[i] = Integer.parseInt(trimmed);
        }
        return newArray;
    }
    //Validiert ob das Array Sortiert ist, da Binary Search
    public boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
