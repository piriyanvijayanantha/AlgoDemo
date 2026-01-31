package org.example.binarysearch.section;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.example.binarysearch.section.components.StyledButton;

public class InputSection extends HBox {
    private TextField arrayInput;
    private TextField targetInput;
    private Label errorLabel;
    private ComboBox<InvariantType> invariantChoice;

    //Konstruktor, Nimmt ein Array und ein Target als Input, bei knopdruck wird das Runnable ausgeführt.
    public InputSection(Runnable onApplyCallback) {
        //Layout
        setPadding(new Insets(15));
        setSpacing(10);
        setStyle("-fx-background-color: #65737e;");

        //Array Input
        Label arrayLabel = new Label("Sortiertes Array:");
        arrayLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        arrayInput = new TextField();
        arrayInput.setText("1, 3, 4, 5, 8, 11, 13");
        arrayInput.setPrefWidth(300);
        arrayInput.setStyle(
                "-fx-background-radius: 5;" +
                        "-fx-padding: 5;"
        );

        //Target Input
        Label targetLabel = new Label("Suchwert:");
        targetLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        targetInput = new TextField();
        targetInput.setText("11");
        targetInput.setStyle(
                "-fx-background-radius: 5;" +
                        "-fx-padding: 5;"
        );

        //Invariantenauswahl
        Label invariantLabel = new Label("Invariante:");
        invariantLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        invariantChoice = new ComboBox<>();
        invariantChoice.getItems().addAll(
                InvariantType.BOTH_INCLUSIVE,
                InvariantType.LEFT_INCLUSIVE,
                InvariantType.BOTH_EXCLUSIVE,
                InvariantType.RIGHT_INCLUSIVE
        );
        invariantChoice.setValue(InvariantType.BOTH_INCLUSIVE);
        invariantChoice.setPrefWidth(150);

        Button applyButton = new StyledButton("Anwenden", onApplyCallback, "#343d46");
        errorLabel = new Label("");
        errorLabel.setVisible(false);


        getChildren().addAll(arrayLabel, arrayInput, targetLabel, targetInput, invariantLabel, invariantChoice, applyButton, errorLabel);
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
    public static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    //Gibt das Array vom Inputfield aus, nach Validierung
    public int[] getArray() throws IllegalArgumentException {
        String input = arrayInput.getText().trim();

        if (input.isEmpty()) {
            throw new IllegalArgumentException("Array-Eingabe darf nicht leer sein");
        }
        String[] parts = input.split(",");

        return formatTextToArray(parts);
    }

    //Gibt den Target vom InputField aus, nach Validierung
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

    public InvariantType getInvariante(){
        return invariantChoice.getValue();
    }
}
