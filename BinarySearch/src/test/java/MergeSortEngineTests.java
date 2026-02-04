import ch.fhnw.mergeSort.Engine.MergeSortEngine;
import ch.fhnw.mergeSort.Engine.MergeState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

//KI Generierte Tests um Engine stets zu testen
public class MergeSortEngineTests {

    private MergeSortEngine engine;

    @BeforeEach
    void setUp() {
        engine = new MergeSortEngine();
        engine.generateSteps();
    }

    @Test
    @DisplayName("Engine initialisiert korrekt")
    void testInitialization() {
        assertNotNull(engine);
        assertNotNull(engine.getCurrentState());
        assertEquals(1, engine.getCurrentStepNumber());
    }

    @Test
    @DisplayName("generateSteps() erzeugt mehrere Schritte")
    void testGenerateSteps() {
        assertTrue(engine.getTotalSteps() > 1, "Es sollten mehrere Schritte generiert werden");
        System.out.println("Anzahl Schritte: " + engine.getTotalSteps());
    }

    @Test
    @DisplayName("Original Array ist korrekt")
    void testOriginalArray() {
        int[] expected = {5, 2, 7, 9, 6, 2, 1, 0, 8};
        assertArrayEquals(expected, engine.getOriginalArray());
    }

    @Test
    @DisplayName("step() erhöht Step-Number")
    void testStep() {
        int initialStep = engine.getCurrentStepNumber();
        engine.step();
        assertEquals(initialStep + 1, engine.getCurrentStepNumber());
    }

    @Test
    @DisplayName("undo() verringert Step-Number")
    void testUndo() {
        // Erst vorwärts gehen
        engine.step();
        engine.step();
        int stepAfterForward = engine.getCurrentStepNumber();

        // Dann rückwärts
        engine.undo();
        assertEquals(stepAfterForward - 1, engine.getCurrentStepNumber());
    }

    @Test
    @DisplayName("reset() setzt auf Schritt 1 zurück")
    void testReset() {
        // Vorwärts gehen
        for (int i = 0; i < 5; i++) {
            engine.step();
        }

        // Reset
        engine.reset();
        assertEquals(1, engine.getCurrentStepNumber());
    }

    @Test
    @DisplayName("canStepForward() gibt korrekte Werte zurück")
    void testCanStepForward() {
        assertTrue(engine.canStepForward(), "Am Anfang sollte vorwärts möglich sein");

        // Gehe bis zum Ende
        while (engine.canStepForward()) {
            engine.step();
        }

        assertFalse(engine.canStepForward(), "Am Ende sollte vorwärts nicht möglich sein");
    }

    @Test
    @DisplayName("canStepBackward() gibt korrekte Werte zurück")
    void testCanStepBackward() {
        assertFalse(engine.canStepBackward(), "Am Anfang sollte rückwärts nicht möglich sein");

        // Ein Schritt vorwärts
        engine.step();

        assertTrue(engine.canStepBackward(), "Nach vorwärts sollte rückwärts möglich sein");
    }

    @Test
    @DisplayName("Array ist am Ende sortiert")
    void testArrayIsSortedAtEnd() {
        // Gehe zum Ende
        while (engine.canStepForward()) {
            engine.step();
        }

        MergeState finalState = engine.getCurrentState();
        assertNotNull(finalState);

        int[] finalArray = finalState.getArray();
        int[] sortedArray = finalArray.clone();
        Arrays.sort(sortedArray);

        assertArrayEquals(sortedArray, finalArray, "Array sollte am Ende sortiert sein");
    }

    @Test
    @DisplayName("Alle States haben gültige Arrays")
    void testAllStatesHaveValidArrays() {
        while (engine.canStepForward()) {
            MergeState state = engine.getCurrentState();
            assertNotNull(state, "State darf nicht null sein");
            assertNotNull(state.getArray(), "Array im State darf nicht null sein");
            assertEquals(9, state.getArray().length, "Array sollte immer Länge 9 haben");
            engine.step();
        }
    }

    @Test
    @DisplayName("DIVIDE und MERGE Phasen existieren")
    void testPhasesExist() {
        boolean hasDivide = false;
        boolean hasMerge = false;

        while (engine.canStepForward()) {
            MergeState state = engine.getCurrentState();
            if ("DIVIDE".equals(state.getPhase())) {
                hasDivide = true;
            }
            if ("MERGE".equals(state.getPhase())) {
                hasMerge = true;
            }
            engine.step();
        }

        assertTrue(hasDivide, "Es sollte DIVIDE-Phasen geben");
        assertTrue(hasMerge, "Es sollte MERGE-Phasen geben");
    }

    @Test
    @DisplayName("Depth erhöht sich in Rekursion")
    void testDepthIncreasesInRecursion() {
        int maxDepth = 0;

        while (engine.canStepForward()) {
            MergeState state = engine.getCurrentState();
            maxDepth = Math.max(maxDepth, state.getDepth());
            engine.step();
        }

        assertTrue(maxDepth > 0, "Maximale Tiefe sollte größer als 0 sein");
        System.out.println("Maximale Rekursionstiefe: " + maxDepth);
    }

    @Test
    @DisplayName("Step über Ende hinaus hat keine Wirkung")
    void testStepBeyondEnd() {
        // Gehe bis zum Ende
        while (engine.canStepForward()) {
            engine.step();
        }

        int stepAtEnd = engine.getCurrentStepNumber();

        // Versuche noch einen Schritt
        engine.step();

        assertEquals(stepAtEnd, engine.getCurrentStepNumber(), "Step über Ende sollte keine Wirkung haben");
    }

    @Test
    @DisplayName("Undo am Anfang hat keine Wirkung")
    void testUndoAtStart() {
        int initialStep = engine.getCurrentStepNumber();

        // Versuche Undo am Anfang
        engine.undo();

        assertEquals(initialStep, engine.getCurrentStepNumber(), "Undo am Anfang sollte keine Wirkung haben");
    }

    @Test
    @DisplayName("Mehrfaches Undo funktioniert korrekt")
    void testMultipleUndo() {
        // 5 Schritte vorwärts
        for (int i = 0; i < 5; i++) {
            engine.step();
        }
        int stepAfterForward = engine.getCurrentStepNumber();

        // 3 Schritte rückwärts
        for (int i = 0; i < 3; i++) {
            engine.undo();
        }

        assertEquals(stepAfterForward - 3, engine.getCurrentStepNumber());
    }

    @Test
    @DisplayName("Step und Undo können kombiniert werden")
    void testStepAndUndoCombination() {
        // Vorwärts
        engine.step();
        engine.step();

        // Rückwärts
        engine.undo();

        // Wieder vorwärts
        engine.step();
        engine.step();

        assertEquals(4, engine.getCurrentStepNumber());
    }
}
