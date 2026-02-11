import ch.fhnw.mergeSort.Engine.MergeSortEngine;
import ch.fhnw.mergeSort.Engine.MergeState;
import ch.fhnw.mergeSort.Engine.TreeNodeInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Initialisierung, SchrittGenerierung, Navigation, Sortier Korrektheit und Rekursionsbaum werden Getestet.
public class MergeSortEngineTests {

    private MergeSortEngine engine;

    @BeforeEach
    void setUp() {
        engine = new MergeSortEngine();
        engine.generateSteps();
    }

    @Nested
    class Initialisierung {
        @Test
        void testOriginalArray() {
            int[] expected = {5, 2, 7, 9, 6, 2, 1, 0, 8};
            assertArrayEquals(expected, engine.getOriginalArray());
        }

        @Test
        void testOriginalArrayDefensiveCopy() {
            int[] array1 = engine.getOriginalArray();
            array1[0] = 999; // Manipulation
            int[] array2 = engine.getOriginalArray();
            assertEquals(5, array2[0]);
        }

        @Test
        void testStartsAtStepOne() {
            assertEquals(1, engine.getCurrentStepNumber());
        }

        @Test
        void testHasMultipleSteps() {
            assertTrue(engine.getTotalSteps() > 2);
        }
    }


    @Nested
    class SchrittGenerierung {

        @Test
        void testFirstStepIsStart() {
            MergeState state = engine.getCurrentState();
            assertEquals(MergeState.PHASE_START, state.getPhase());
        }

        @Test
        void testLastStepIsDone() {
            // Bis zum Ende navigieren
            while (engine.canStepForward()) {
                engine.step();
            }
            MergeState state = engine.getCurrentState();
            assertEquals(MergeState.PHASE_DONE, state.getPhase());
        }

        @Test
        void testSecondStepIsDivide() {
            engine.step();
            MergeState state = engine.getCurrentState();
            assertEquals(MergeState.PHASE_DIVIDE, state.getPhase());
        }

        @Test
        void testEqualDividesAndMerges() {
            int divides = 0;
            int merges = 0;

            while (true) {
                MergeState state = engine.getCurrentState();
                if (state.getPhase().equals(MergeState.PHASE_DIVIDE)) divides++;
                if (state.getPhase().equals(MergeState.PHASE_MERGE)) merges++;
                if (!engine.canStepForward()) break;
                engine.step();
            }
            assertEquals(divides, merges);
        }
    }

    @Nested
    class Navigation {

        @Test
        void testStepIncrements() {
            assertEquals(1, engine.getCurrentStepNumber());
            engine.step();
            assertEquals(2, engine.getCurrentStepNumber());
        }

        @Test
        void testUndoDecrements() {
            engine.step();
            engine.step();
            assertEquals(3, engine.getCurrentStepNumber());
            engine.undo();
            assertEquals(2, engine.getCurrentStepNumber());
        }

        @Test
        void testReset() {
            engine.step();
            engine.step();
            engine.step();
            engine.reset();
            assertEquals(1, engine.getCurrentStepNumber());
            assertEquals(MergeState.PHASE_START, engine.getCurrentState().getPhase());
        }

        @Test
        void testCannotUndoPastStart() {
            assertFalse(engine.canStepBackward());
            engine.undo(); // Sollte nichts tun
            assertEquals(1, engine.getCurrentStepNumber());
        }

        @Test
        void testCannotStepPastEnd() {
            while (engine.canStepForward()) {
                engine.step();
            }
            int lastStep = engine.getCurrentStepNumber();
            engine.step(); // Sollte nichts tun
            assertEquals(lastStep, engine.getCurrentStepNumber());
        }

        @Test
        void testCanStepForwardAtStart() {
            assertTrue(engine.canStepForward());
        }

        @Test
        void testCannotStepBackwardAtStart() {
            assertFalse(engine.canStepBackward());
        }

        @Test
        void testPreviousStateAtStart() {
            assertNull(engine.getPreviousState());
        }

        @Test
        void testPreviousState() {
            MergeState first = engine.getCurrentState();
            engine.step();
            MergeState prev = engine.getPreviousState();
            assertEquals(first.getPhase(), prev.getPhase());
        }
    }

    @Nested
    class SortierKorrektheit {

        @Test
        void testArrayIsSortedAtEnd() {
            while (engine.canStepForward()) {
                engine.step();
            }
            int[] result = engine.getCurrentState().getArray();
            int[] expected = {0, 1, 2, 2, 5, 6, 7, 8, 9};
            assertArrayEquals(expected, result);
        }

        @Test
        void testSameElements() {
            int[] original = engine.getOriginalArray().clone();
            java.util.Arrays.sort(original);

            while (engine.canStepForward()) {
                engine.step();
            }
            int[] result = engine.getCurrentState().getArray();

            assertArrayEquals(original, result);
        }
    }

    @Nested
    class Rekursionsbaum {

        @Test
        void testTreeAtStart() {
            List<TreeNodeInfo> tree = engine.computeTreeState();
            assertEquals(1, tree.size());
        }

        @Test
        void testRootDepthIsZero() {
            List<TreeNodeInfo> tree = engine.computeTreeState();
            assertEquals(0, tree.getFirst().depth);
        }

        @Test
        void testRootHasAllValues() {
            List<TreeNodeInfo> tree = engine.computeTreeState();
            TreeNodeInfo root = tree.getFirst();
            assertArrayEquals(engine.getOriginalArray(), root.values);
        }

        @Test
        void testRootStatusIsInitial() {
            List<TreeNodeInfo> tree = engine.computeTreeState();
            assertEquals("INITIAL", tree.getFirst().status);
        }

        @Test
        void testTreeAfterFirstDivide() {
            engine.step(); // DIVIDE
            List<TreeNodeInfo> tree = engine.computeTreeState();
            assertEquals(3, tree.size());
        }

        @Test
        void testRootStatusAfterDivide() {
            engine.step();
            List<TreeNodeInfo> tree = engine.computeTreeState();
            // Wurzel ist aktiv geteilt
            assertEquals("ACTIVE_DIVIDE", tree.getFirst().status);
        }

        @Test
        void testChildrenDepthAfterDivide() {
            engine.step();
            List<TreeNodeInfo> tree = engine.computeTreeState();
            assertEquals(1, tree.get(1).depth);
            assertEquals(1, tree.get(2).depth);
        }

        @Test
        void testIsLeaf() {
            // Bis alle Blätter sichtbar sind (letzter Divide)
            while (engine.canStepForward()) {
                engine.step();
            }
            List<TreeNodeInfo> tree = engine.computeTreeState();
            for (TreeNodeInfo node : tree) {
                if (node.left == node.right) {
                    assertTrue(node.isLeaf());
                    assertEquals(1, node.size());
                }
            }
        }

        @Test
        void testNodeSize() {
            List<TreeNodeInfo> tree = engine.computeTreeState();
            TreeNodeInfo root = tree.getFirst();
            assertEquals(9, root.size());
        }
    }
}
