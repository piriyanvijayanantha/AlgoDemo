package ch.fhnw.mergeSort;

import ch.fhnw.mergeSort.section.ActionSection;
import ch.fhnw.mergeSort.section.ControlSection;
import ch.fhnw.mergeSort.section.RecursionTreeSection;
import ch.fhnw.mergeSort.section.VariablesSection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MergeSortGUI extends Application {

    private ControlSection controlSection;
    private VariablesSection variablesSection;
    private RecursionTreeSection recursionTreeSection;
    private ActionSection actionSection;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        // Sections erstellen
        controlSection = new ControlSection(
                this::handleReset,
                this::handleUndo,
                this::handleStep
        );

        variablesSection = new VariablesSection();
        recursionTreeSection = new RecursionTreeSection();
        actionSection = new ActionSection();

        // Layout aufbauen
        root.setTop(controlSection);
        root.setLeft(variablesSection);
        root.setCenter(recursionTreeSection);
        root.setRight(actionSection);

        Scene scene = new Scene(root, 1400, 700);
        stage.setTitle("MergeSort");
        stage.setScene(scene);
        stage.show();
    }

    private void handleReset() {
        System.out.println("Reset geklickt");
    }

    private void handleUndo() {
        System.out.println("Undo geklickt");
    }

    private void handleStep() {
        System.out.println("Step geklickt");
    }

    public static void main(String[] args) {
        launch(args);
    }
}