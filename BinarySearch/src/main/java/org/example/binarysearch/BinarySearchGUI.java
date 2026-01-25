package org.example.binarysearch;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.binarysearch.components.ArrayVisualization;
import org.example.binarysearch.components.ControlSection;
import org.example.binarysearch.components.InfoSection;
import org.example.binarysearch.components.InputSection;
import org.example.binarysearch.engine.BinarySearchEngine;

public class BinarySearchGUI extends Application {
    private BinarySearchEngine binarySearchEngine;
    private ArrayVisualization arrayVisualization;
    private InputSection inputSection;
    private InfoSection infoSection;
    private ControlSection controlSection;
    private int[] currentArray = new int[]{1, 3, 4, 5, 8, 11, 13};



    // Startet die JavaFX Anwendung mit den 4 Sections die initialisiert werden.
    @Override
    public void start(Stage stage) {
        binarySearchEngine = new BinarySearchEngine();
        BorderPane root = new BorderPane();

        arrayVisualization = new ArrayVisualization(currentArray);
        inputSection = new InputSection(this::handleApply);
        controlSection = new ControlSection(
                this::handleStart,
                this::handleStep,
                this::handleUndo
        );
        infoSection = new InfoSection();

        root.setTop(inputSection);
        root.setCenter(arrayVisualization);
        root.setRight(controlSection);
        root.setLeft(infoSection);
        Scene scene = new Scene(root, 1100, 500);
        stage.setTitle("Binary Search Demonstrator");
        stage.setScene(scene);
        stage.show();
    }

    //Handler - TODO: Kann man in eigene Klasse packen? Unnötig?

    //Action für Start Knopf, Startet die Engine
    private void handleStart() {
        try {
            int target = inputSection.getTarget();
            binarySearchEngine.start(currentArray, target);
            updateDisplay();
            inputSection.clearMessage();
        } catch (IllegalArgumentException e) {
            inputSection.showError(e.getMessage());
        }
    }
    //nächster Schritt im algo
    private void handleStep() {
        binarySearchEngine.step();
        updateDisplay();
    }
    //letzter Schritt im Algo
    private void handleUndo() {
        if (binarySearchEngine.undo()) {
            updateDisplay();
        } else {
            arrayVisualization.showError("Keine vorherigen Schritte");
        }
    }
    //Probiert die Inputs anzuwenden
    private void handleApply() {
        try {
            int[] array = inputSection.getArray();
            int target = inputSection.getTarget();

            if (!inputSection.isSorted(array)) {
                throw new IllegalArgumentException("Array muss sortiert sein!");
            }
            currentArray = array;
            binarySearchEngine.start(array, target);
            arrayVisualization.updateArray(array);
            arrayVisualization.updateState(binarySearchEngine.getI(), binarySearchEngine.getJ(), binarySearchEngine.getM(), binarySearchEngine.isFound());

            inputSection.showSuccess("Array und Suchwert erfolgreich geladen");

        } catch (IllegalArgumentException e) {
            inputSection.showError(e.getMessage());
        }
    }
    //Aktualisiert die neuen Variabeln mit dem Display
    private void updateDisplay() {
        arrayVisualization.updateState(
                binarySearchEngine.getI(),
                binarySearchEngine.getJ(),
                binarySearchEngine.getM(),
                binarySearchEngine.isFound()
        );
    }

}
