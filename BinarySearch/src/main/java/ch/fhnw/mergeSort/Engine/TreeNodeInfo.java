package ch.fhnw.mergeSort.Engine;

public class TreeNodeInfo {
    // Array-Grenzen dieses Knotens
    public final int left;
    public final int right;

    // Tiefe im Rekursionsbaum (0 = Wurzel)
    public final int depth;

    // Werte die angezeigt werden (null = leere Boxen)
    public int[] values;

    // Aktueller Status des Knotens für Farbe zuordnung
    public String status;

    public TreeNodeInfo(int left, int right, int depth, int[] values, String status) {
        this.left = left;
        this.right = right;
        this.depth = depth;
        this.values = values;
        this.status = status;
    }

    public int size() {
        return right - left;
    }

    //ist es ein Blatt? -> ein Knoten mit einer Verbindung
    public boolean isLeaf() {
        return left - right == 1;
    }

    @Override
    public String toString() {
        return String.format("Node[%d..%d] depth=%d status=%s", left, right, depth, status);
    }
}
