package ch.fhnw.mergeSort.Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MergeSortEngine {
    private List<MergeState> allSteps;
    private int currentStepIndex;
    private int[] originalArray;
    private Stack<String> callStack;


    public MergeSortEngine() {
        //Speichert jeden einzelnen State als einen Step (nach einem Devide oder Merge)
        this.allSteps = new ArrayList<>();
        this.currentStepIndex = 0;
        this.callStack = new Stack<>();
        this.originalArray = new int[]{5, 2, 7, 9, 6, 2, 1, 0, 8,}; //Default
    }

    public void generateSteps() {
        allSteps.clear();
        currentStepIndex = 0;

        // Start-State
        int[] workArray = originalArray.clone(); // Defensive Kopie
        //Start-State mit leerem Call Stack
        allSteps.add(new MergeState(
                MergeState.PHASE_START,
                workArray,
                0, workArray.length, -1, 0,
                "Start mit Array",
                new ArrayList<>()
        ));

        // MergeSort durchführen und States sammeln
        mergeSort(workArray, 0, workArray.length, 0);

        // End-State mit leerem Call Stack
        allSteps.add(new MergeState(
                MergeState.PHASE_DONE,
                workArray,
                0, workArray.length, -1, 0,
                "Sortierung abgeschlossen!",
                new ArrayList<>()
        ));
    }

    private void mergeSort(int[] array, int left, int right, int depth) {
        // CallStack: Methodenaufruf mergeSort wird gepusht
        callStack.push(String.format("mergeSort(%d, %d)", left, right));
        //abbruch bedingung
        if (right - left > 1) {
            divide(array, left, right, depth);
        }
        // CallStack: Methodenaufruf ist fertig
        callStack.pop();
    }


    private void divide(int[] array, int left, int right, int depth) {
        int mid = (left + right) / 2;

        // divide State speichern
        allSteps.add(new MergeState(
                "DIVIDE",
                array.clone(), //Kopie vom Array
                left, right, mid, depth,
                String.format("Teile [%d..%d] bei Index %d", left, right, mid),
                new ArrayList<>(callStack)
        ));

        // Rekursiv links
        mergeSort(array, left, mid, depth + 1);
        // Rekursiv rechts
        mergeSort(array, mid, right, depth + 1);
        // Merge
        merge(array, left, mid, right, depth);
    }

    private void merge(int[] array, int left, int mid, int right, int depth) {
        // Temporäre Arrays erstellen
        int[] leftPart = new int[mid - left];
        int[] rightPart = new int[right - mid];
        // Linke hälfte ins Temp kopieren
        for (int i = 0; i < leftPart.length; i++) {
            leftPart[i] = array[left + i];
        }
        // Rechte hälfte ins Temp kopieren
        for (int i = 0; i < rightPart.length; i++) {
            rightPart[i] = array[mid + i];
        }
        // Merge-Prozess
        int i = 0;
        int j = 0;
        int k = left;

        while (i < leftPart.length && j < rightPart.length) {
            if (leftPart[i] <= rightPart[j]) {
                array[k] = leftPart[i];
                i++;
            } else {
                array[k] = rightPart[j];
                j++;
            }
            k++;
        }
        // Rest von leftPart
        while (i < leftPart.length) {
            array[k] = leftPart[i];
            i++;
            k++;
        }
        // Rest von rightPart
        while (j < rightPart.length) {
            array[k] = rightPart[j];
            j++;
            k++;
        }

        // MERGE-State speichern
        allSteps.add(new MergeState(
                "MERGE",
                array.clone(),
                left, right, mid, depth,
                String.format("Merge [%d..%d] abgeschlossen", left, right),
                new ArrayList<>(callStack)
        ));
    }

   //NAVIGATION -----------------------------------
    public void step() {
        if (canStepForward()) {
            currentStepIndex++;
        }
    }

    public void undo() {
        if (canStepBackward()) {
            currentStepIndex--;
        }
    }

    //zum Start zurück
    public void reset() {
        currentStepIndex = 0;
    }

    //Rekursionsbaum Zustand berechnen -----------------------
    public List<TreeNodeInfo> computeTreeState() {
        List<TreeNodeInfo> nodes = new ArrayList<>();

        for (int s = 0; s <= currentStepIndex && s < allSteps.size(); s++) {
            MergeState step = allSteps.get(s);

            switch (step.getPhase()) {
                case MergeState.PHASE_START:
                    // Wurzelknoten: ganzes Array sichtbar
                    nodes.add(new TreeNodeInfo(
                            0, originalArray.length, 0,
                            originalArray.clone(),
                            "INITIAL"
                    ));
                    break;

                case MergeState.PHASE_DIVIDE:
                    applyDivide(nodes, step);
                    break;

                case MergeState.PHASE_MERGE:
                    applyMerge(nodes, step);
                    break;
            }
        }
        // Aktuellen Schritt als "aktiv" markieren
        markActiveNode(nodes);

        return nodes;
    }

    private void applyDivide(List<TreeNodeInfo> nodes, MergeState step) {
        int left = step.getLeft();
        int right = step.getRight();
        int mid = step.getMid();
        int depth = step.getDepth();
        int[] arrayState = step.getArray();

        // Elternknoten finden und als "geteilt" markieren
        for (TreeNodeInfo node : nodes) {
            if (node.left == left && node.right == right && node.depth == depth) {
                node.status = "DIVIDED";
                node.values = null; // Werte verschwinden nach oben
                break;
            }
        }

        // Linkes Kind [left..mid] hinzufügen
        boolean leftIsLeaf = (mid - left == 1);
        int[] leftValues = leftIsLeaf ? new int[]{arrayState[left]} : null;
        nodes.add(new TreeNodeInfo(left, mid, depth + 1, leftValues, leftIsLeaf ? "LEAF" : "EMPTY"));


        // Rechtes Kind [mid..right] hinzufügen
        boolean rightIsLeaf = (right - mid == 1);
        int[] rightValues = rightIsLeaf ? new int[]{arrayState[mid]} : null;
        nodes.add(new TreeNodeInfo(mid, right, depth + 1, rightValues, rightIsLeaf ? "LEAF" : "EMPTY"));
    }

    private void applyMerge(List<TreeNodeInfo> nodes, MergeState step) {
        int left = step.getLeft();
        int right = step.getRight();
        int depth = step.getDepth();
        int[] arrayState = step.getArray();

        for (TreeNodeInfo node : nodes) {
            if (node.left == left && node.right == right && node.depth == depth) {
                // Sortierte Werte aus dem Array extrahieren
                int[] mergedValues = new int[right - left];
                System.arraycopy(arrayState, left, mergedValues, 0, mergedValues.length);
                node.values = mergedValues;
                node.status = "MERGED";
                break;
            }
        }
    }

    private void markActiveNode(List<TreeNodeInfo> nodes) {
        MergeState current = getCurrentState();
        if (current == null) return;

        String phase = current.getPhase();
        if (!phase.equals(MergeState.PHASE_DIVIDE) && !phase.equals(MergeState.PHASE_MERGE)) {
            return; // START und DONE haben keinen aktiven Knoten
        }

        int left = current.getLeft();
        int right = current.getRight();
        int depth = current.getDepth();

        for (TreeNodeInfo node : nodes) {
            if (node.left == left && node.right == right && node.depth == depth) {
                if (phase.equals(MergeState.PHASE_DIVIDE)) {
                    node.status = "ACTIVE_DIVIDE";
                }
                // MERGE: bleibt "MERGED" aber mit Hervorhebung
                // → wird im GUI speziell behandelt
                break;
            }
        }
    }

    public boolean canStepForward() {
        return currentStepIndex < allSteps.size() - 1;
    }

    public boolean canStepBackward() {
        return currentStepIndex > 0;
    }

    public MergeState getCurrentState() {
        if (currentStepIndex < allSteps.size()) {
            return allSteps.get(currentStepIndex);
        }
        return null;
    }

    public int getCurrentStepNumber() {
        return currentStepIndex + 1;
    }

    public int getTotalSteps() {
        return allSteps.size();
    }

    public int[] getOriginalArray() {
        return originalArray.clone();
    }
    public MergeState getPreviousState() {
        if (currentStepIndex > 0) {
            return allSteps.get(currentStepIndex - 1);
        }
        return null;
    }
    public void setArray(int[] newArray) {
        this.originalArray = newArray.clone();
        generateSteps(); // Schritte neu berechnen
    }
}
