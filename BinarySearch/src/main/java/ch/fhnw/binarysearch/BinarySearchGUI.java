package ch.fhnw.binarysearch;

import ch.fhnw.binarysearch.section.ArrayVisualizationSection;
import ch.fhnw.binarysearch.section.ControlSection;
import ch.fhnw.binarysearch.section.InfoSection;
import ch.fhnw.binarysearch.section.InputSection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ch.fhnw.binarysearch.section.*;
import ch.fhnw.binarysearch.engine.BinarySearchEngine;

public class BinarySearchGUI extends Application {
    private BinarySearchEngine binarySearchEngine;
    private ArrayVisualizationSection arrayVisualizationSection;
    private InputSection inputSection;
    private InfoSection infoSection;
    private ControlSection controlSection;
    private int[] currentArray = new int[]{1, 3, 4, 5, 8, 11, 13};

    // Startet die JavaFX Anwendung mit den 4 Sections die initialisiert werden.
    @Override
    public void start(Stage stage) {
        binarySearchEngine = new BinarySearchEngine();
        BorderPane root = new BorderPane();

        arrayVisualizationSection = new ArrayVisualizationSection(currentArray, binarySearchEngine);
        inputSection = new InputSection(this::handleApply);
        controlSection = new ControlSection(
                this::handleStart,
                this::handleStep,
                this::handleUndo
        );
        infoSection = new InfoSection();

        root.setTop(inputSection);
        root.setCenter(arrayVisualizationSection);
        root.setRight(controlSection);
        root.setLeft(infoSection);
        Scene scene = new Scene(root, 1100, 500);
        stage.setTitle("Binary Search Demonstrator");
        stage.setScene(scene);
        stage.show();
    }


    //Action für Start Knopf, Startet die Engine
    private void handleStart() {
        try {
            int target = inputSection.getTarget();
            binarySearchEngine.start(currentArray, target, inputSection.getInvariante());
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
        if (binarySearchEngine.hasHistoryStates()) {
            binarySearchEngine.undo();
            updateDisplay();
        } else {
            arrayVisualizationSection.showError("Keine vorherigen Schritte");
        }
    }
    //Probiert die Inputs anzuwenden
    private void handleApply() {
        try {
            int[] array = inputSection.getArray();
            int target = inputSection.getTarget();

            if (!InputSection.isSorted(array)) {
                throw new IllegalArgumentException("Array muss sortiert sein!");
            }
            currentArray = array;
            binarySearchEngine.start(array, target, inputSection.getInvariante());
            arrayVisualizationSection.updateArray(array);
            updateDisplay();

            inputSection.showSuccess("Array und Suchwert erfolgreich geladen");

        } catch (IllegalArgumentException e) {
            inputSection.showError(e.getMessage());
        }
    }
    //Aktualisiert die neuen Variablen mit dem Display
    private void updateDisplay() {
        arrayVisualizationSection.updateState();
    }

}
