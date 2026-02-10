package ch.fhnw.components;

import ch.fhnw.utils.ArrayGenerator;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class ArrayInputComponent {
    private final TextField arrayInputField;
    private final Spinner<Integer> generateSizeSpinner;
    private final boolean requireSorted; // Muss das Array sortiert sein?

    public ArrayInputComponent(boolean requireSorted, String defaultArray) {
        this.requireSorted = requireSorted;

        // Textfeld für manuelle Eingabe
        arrayInputField = new TextField(defaultArray);
        arrayInputField.setPrefWidth(350);
        arrayInputField.setStyle(
                "-fx-font-size: 13px; " +
                        "-fx-padding: 6; " +
                        "-fx-border-color: #65737e; " +
                        "-fx-border-radius: 4; " +
                        "-fx-background-radius: 4;"
        );

        // Spinner für Array-Grösse (min 4, max 13, default 7)
        generateSizeSpinner = new Spinner<>(4, 13, 7);
        generateSizeSpinner.setEditable(true);
        generateSizeSpinner.setPrefWidth(70);
    }


     //Erstellt die komplette Eingabe-Zeile als HBox. Enthält: Label + Textfeld + "oder" + Spinner + Generieren-Button
    public HBox getInputRow() {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER);

        Label arrayLabel = new Label("Array:");
        Label orLabel = new Label("oder");

        StyledButton generateBtn = new StyledButton(
                "Generieren", this::handleGenerate, "#6ba43a"
        );

        row.getChildren().addAll(
                arrayLabel, arrayInputField, orLabel, generateSizeSpinner, generateBtn
        );

        return row;
    }

    //Liest das Array und validiert es
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
                throw new IllegalArgumentException(
                        "Ungültige Zahl an Position " + i + ": " + part
                );
            }
        }

        // Sortierungscheck nur wenn nötig (BinarySearch)
        if (requireSorted && !isSorted(array)) {
            throw new IllegalArgumentException("Array muss sortiert sein!");
        }

        return array;
    }


    public void setArrayText(String text) {
        arrayInputField.setText(text);
    }


    //Generiert ein zufälliges Array und schreibt es ins Textfeld
    private void handleGenerate() {
        int size = generateSizeSpinner.getValue();

        int[] generated;
        if (requireSorted) {
            generated = ArrayGenerator.generateSortedArray(size);
        } else {
            generated = ArrayGenerator.generateRandomArray(size);
        }

        // Array als String ins Textfeld schreiben
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < generated.length; i++) {
            sb.append(generated[i]);
            if (i < generated.length - 1) {
                sb.append(", ");
            }
        }
        arrayInputField.setText(sb.toString());
    }

    //Prüft ob ein Array sortiert ist
    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}

