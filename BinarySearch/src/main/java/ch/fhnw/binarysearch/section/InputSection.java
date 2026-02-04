package ch.fhnw.binarysearch.section;

import ch.fhnw.components.StyledButton;
import ch.fhnw.utils.ArrayGenerator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InputSection extends VBox {
    private TextField arrayInputField;
    private TextField targetField;
    private ComboBox<InvariantType> invariantChoice;
    private Spinner<Integer> generateSizeSpinner;
    private Label messageLabel;
    private Runnable onApply;

    public InputSection(Runnable onApply) {
        this.onApply = onApply;

        setAlignment(Pos.CENTER);
        setPadding(new Insets(15));
        setSpacing(12);
        setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #343d46; -fx-border-width: 0 0 2 0;");

        HBox arrayRow = new HBox(10);
        arrayRow.setAlignment(Pos.CENTER);

        Label arrayLabel = new Label("Sortiertes Array:");

        arrayInputField = new TextField("1, 3, 5, 7, 9, 11, 13");
        arrayInputField.setPrefWidth(350);
        arrayInputField.setStyle(
                "-fx-font-size: 13px; " +
                        "-fx-padding: 6; " +
                        "-fx-border-color: #65737e; " +
                        "-fx-border-radius: 4; " +
                        "-fx-background-radius: 4;"
        );

        // Generate Section
        Label orLabel = new Label("oder");

        generateSizeSpinner = new Spinner<>(5, 13, 7);
        generateSizeSpinner.setEditable(true);
        generateSizeSpinner.setPrefWidth(70);

        StyledButton generateBtn = new StyledButton("Generieren", this::handleGenerate, "#6ba43a");

        arrayRow.getChildren().addAll(
                arrayLabel,
                arrayInputField,
                orLabel,
                generateSizeSpinner,
                generateBtn
        );

        // Zweite Zeile: Target + Invariante + Apply
        HBox controlRow = new HBox(20);
        controlRow.setAlignment(Pos.CENTER);

        // Target
        VBox targetBox = new VBox(5);
        targetBox.setAlignment(Pos.CENTER);

        Label targetLabel = new Label("Suchwert:");
        targetLabel.setStyle("-fx-text-fill: #4f5b66; -fx-font-size: 11px; -fx-font-weight: bold;");

        targetField = new TextField("5");
        targetField.setPromptText("Zahl");
        targetField.setPrefWidth(80);
        targetField.setStyle(
                "-fx-font-size: 13px; " +
                        "-fx-padding: 6; " +
                        "-fx-border-color: #65737e; " +
                        "-fx-border-radius: 4; " +
                        "-fx-background-radius: 4;"
        );

        targetBox.getChildren().addAll(targetLabel, targetField);

        // Invariante
        VBox invariantBox = new VBox(5);
        invariantBox.setAlignment(Pos.CENTER);

        Label invariantLabel = new Label("Invariante:");

        invariantChoice = new ComboBox<>();
        invariantChoice.getItems().addAll(InvariantType.values());
        invariantChoice.setValue(InvariantType.BOTH_INCLUSIVE);
        invariantChoice.setPrefWidth(280);
        invariantChoice.setStyle("-fx-font-size: 12px;");

        invariantBox.getChildren().addAll(invariantLabel, invariantChoice);

        // Apply Button
        StyledButton applyButton = new StyledButton("Anwenden", this::handleApply, "#0065A4");

        controlRow.getChildren().addAll(targetBox, invariantBox, applyButton);

        // Message Label
        messageLabel = new Label();
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(800);
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setStyle("-fx-font-size: 11px;");

        getChildren().addAll(arrayRow, controlRow, messageLabel);
    }

    private void handleGenerate() {
        int size = generateSizeSpinner.getValue();
        int[] generated = ArrayGenerator.generateSortedArray(size);

        //  ins Array Feld schreiben
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < generated.length; i++) {
            sb.append(generated[i]);
            if (i < generated.length - 1) {
                sb.append(", ");
            }
        }
        arrayInputField.setText(sb.toString());
        clearMessage();
    }

    private void handleApply() {
        try {
            if (onApply != null) {
                onApply.run();
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    public int[] getArray() throws IllegalArgumentException {
        String input = arrayInputField.getText().trim();

        if (input.isEmpty()) {
            throw new IllegalArgumentException("Array-Eingabe darf nicht leer sein");
        }

        String[] parts = input.split(",");
        int[] array = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].trim();
            if (part.isEmpty()) {
                throw new IllegalArgumentException("Leeres Element an Position: " + i);
            }
            try {
                array[i] = Integer.parseInt(part);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Ungültige Zahl an Position " + i + ": " + part);
            }
        }

        if (!isSorted(array)) {
            throw new IllegalArgumentException("Array muss sortiert sein!");
        }

        return array;
    }

    public int getTarget() throws IllegalArgumentException {
        String targetText = targetField.getText().trim();
        if (targetText.isEmpty()) {
            throw new IllegalArgumentException("Suchwert darf nicht leer sein");
        }
        try {
            return Integer.parseInt(targetText);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Suchwert muss eine ganze Zahl sein");
        }
    }

    public InvariantType getInvariante() {
        return invariantChoice.getValue();
    }

    public void showError(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: #C4071B; -fx-font-weight: bold; -fx-font-size: 11px;");
    }

    public void showSuccess(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: #6ba43a; -fx-font-weight: bold; -fx-font-size: 11px;");
    }

    public void clearMessage() {
        messageLabel.setText("");
    }

    public static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}