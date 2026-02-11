package ch.fhnw.mergeSort;

import ch.fhnw.mergeSort.Engine.MergeSortEngine;
import ch.fhnw.mergeSort.Engine.MergeState;
import ch.fhnw.mergeSort.Engine.TreeNodeInfo;
import ch.fhnw.mergeSort.section.ActionSection;
import ch.fhnw.mergeSort.section.ControlSection;
import ch.fhnw.mergeSort.section.RecursionTreeSection;
import ch.fhnw.mergeSort.section.VariablesSection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

public class MergeSortGUI extends Application {
    private MergeSortEngine engine;

    private ControlSection controlSection;
    private VariablesSection variablesSection;
    private RecursionTreeSection recursionTreeSection;
    private ActionSection actionSection;

    @Override
    public void start(Stage stage) {
        //Engine initialisierung und generierung von alles Schritten
        engine = new MergeSortEngine();
        engine.generateSteps();
        //Gui Sektionen initalisieren
        controlSection = new ControlSection(
                this::handleReset,
                this::handleUndo,
                this::handleStep,
                this::handleApply
        );

        variablesSection = new VariablesSection();
        recursionTreeSection = new RecursionTreeSection();
        actionSection = new ActionSection();

        BorderPane root = new BorderPane();
        root.setTop(controlSection);
        root.setLeft(variablesSection);
        root.setCenter(recursionTreeSection);
        root.setRight(actionSection);

        Scene scene = new Scene(root, 1550, 750);
        stage.setTitle("MergeSort");
        stage.setScene(scene);
        stage.show();

        // Ersten Zustand anzeigen
        updateAllSections();
    }

    //Handler
    private void handleReset() {
        engine.reset();
        updateAllSections();
    }

    private void handleUndo() {
        engine.undo();
        updateAllSections();
    }

    private void handleStep() {
        engine.step();
        updateAllSections();
    }

    private void handleApply() {
        try {
            int[] newArray = controlSection.getArray();
            engine.setArray(newArray);
            updateAllSections();
        } catch (IllegalArgumentException e) {
            //TODO: Fehlermeldung anzeigen
        }
    }

    //Aktualisiert jede Sektion mit dem aktuellen State des Algos
    private void updateAllSections() {
        MergeState currentState = engine.getCurrentState();
        if (currentState == null) return;

        // Schrittzähler + Button-Zustände
        controlSection.update(
                engine.getCurrentStepNumber(),
                engine.getTotalSteps(),
                engine.canStepForward(),
                engine.canStepBackward()
        );

        //Variablen + CallStack
        variablesSection.update(currentState);

        //Rekursionsbaum
        List<TreeNodeInfo> treeState = engine.computeTreeState();
        recursionTreeSection.update(treeState, currentState);

        // Aktions-Erklärung
        // getPreviousState() liefert den State VOR dem aktuellen (für Merge-Erklärung)
        actionSection.update(currentState, engine.getPreviousState());
    }

}