package ch.fhnw.mergeSort.Engine;

import java.util.ArrayList;
import java.util.List;

public class MergeState {
    //Phasen im Algo
    public static final String PHASE_START = "START";
    public static final String PHASE_DIVIDE = "DIVIDE";
    public static final String PHASE_MERGE = "MERGE";
    public static final String PHASE_DONE = "DONE";

    private final String phase;
    private final int[] array;         // Array Zustand
    private final int left;
    private final int right;
    private final int mid;
    private final int depth;           // Rekursionstiefe
    private final String description;  // zb: "Teile [0..8] bei Index 4"
    private final List<String> callStack; // Snapshot des CallStacks


    public MergeState(String phase, int[] array, int left, int right, int mid, int depth, String description, List<String> callStack) {
        this.callStack = new ArrayList<>(callStack); //Defensive Kopie
        this.phase = phase;
        this.array = array.clone(); //Defensive Kopie
        this.left = left;
        this.right = right;
        this.mid = mid;
        this.depth = depth;
        this.description = description;
    }

    public String getPhase() {
        return phase;
    }

    public int[] getArray() {
        return array.clone();
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getMid() {
        return mid;
    }

    public int getDepth() {
        return depth;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getCallStack() {
        return new ArrayList<>(callStack);
    }

    @Override
    public String toString() {
        return String.format("[%s] depth=%d [%d..%d] mid=%d - %s",
                phase, depth, left, right, mid, description);
    }
}