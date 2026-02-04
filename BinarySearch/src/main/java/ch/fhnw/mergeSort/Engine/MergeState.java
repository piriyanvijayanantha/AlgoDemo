package ch.fhnw.mergeSort.Engine;

public class MergeState {
    private final String phase;        // "DIVIDE" oder "MERGE", TODO: könnten Konstante oder Enum sein?
    private final int[] array;         // Array Zustand
    private final int left;
    private final int right;
    private final int mid;
    private final int depth;           // Rekursionstiefe
    private final String description;  // "Teile [0..8] bei Index 4"

    public MergeState(String phase, int[] array, int left, int right, int mid, int depth, String description) {
        this.phase = phase;
        this.array = array.clone();  // Defensive copy!
        this.left = left;
        this.right = right;
        this.mid = mid;
        this.depth = depth;
        this.description = description;
    }

    public String getPhase() { return phase; }
    public int[] getArray() { return array.clone(); }
    public int getDepth() { return depth; }

    @Override
    public String toString() {
        return String.format("[%s] depth=%d [%d..%d] mid=%d - %s",
                phase, depth, left, right, mid, description);
    }
}