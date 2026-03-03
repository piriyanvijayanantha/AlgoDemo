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
    private int[] currentArray = new int[]{};
    private Stage currentStage;

    // Startet die JavaFX Anwendung mit den 4 Sections die initialisiert werden.
    @Override
    public void start(Stage stage) {
        this.currentStage = stage;
        binarySearchEngine = new BinarySearchEngine();
        BorderPane root = new BorderPane();

        arrayVisualizationSection = new ArrayVisualizationSection(currentArray, binarySearchEngine);
        inputSection = new InputSection(this::handleApply);
        controlSection = new ControlSection(
                this::handleStep,
                this::handleUndo,
                this::handleBack
        );
        infoSection = new InfoSection();

        root.setTop(inputSection);
        root.setCenter(arrayVisualizationSection);
        root.setRight(controlSection);
        root.setLeft(infoSection);
        handleApply();
        Scene scene = new Scene(root, 1450, 500);
        stage.setTitle("Binary Search Demonstrator");
        stage.setScene(scene);
        stage.show();
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
            InvariantType invariant = inputSection.getInvariante();

            currentArray = array;
            arrayVisualizationSection.updateArray(array);
            binarySearchEngine.start(array, target, invariant);
            updateDisplay();
            infoSection.updateInfo(invariant);

            inputSection.showSuccess("Array und Suchwert erfolgreich geladen");
        } catch (IllegalArgumentException e) {
            inputSection.showError(e.getMessage());
        }
    }

    private void handleBack() {
        try {
            ch.fhnw.MainMenu menu = new ch.fhnw.MainMenu();
            Stage menuStage = new Stage();
            menu.start(menuStage);
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Aktualisiert die neuen Variablen mit dem Display
    private void updateDisplay() {
        arrayVisualizationSection.updateState();
    }

}
