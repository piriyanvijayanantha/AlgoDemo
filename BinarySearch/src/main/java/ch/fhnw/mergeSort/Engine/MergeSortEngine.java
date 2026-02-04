package ch.fhnw.mergeSort.Engine;

import java.util.ArrayList;
import java.util.List;

public class MergeSortEngine {
    private List<MergeState> allSteps;
    private int currentStepIndex;
    private int[] originalArray;

    public MergeSortEngine() {
        //Speichert jeden einzelnen State als einen Step (nach einem Devide oder Merge)
        this.allSteps = new ArrayList<>();
        this.currentStepIndex = 0;

        //Hardcodiert
        this.originalArray = new int[]{5, 2, 7, 9, 6, 2, 1, 0, 8};
    }

    public void generateSteps() {
        allSteps.clear();
        currentStepIndex = 0;

        // Start-State
        int[] workArray = originalArray.clone(); //eine Kopie vom original wird Erstellt, (nur workArray wird dadurch verändert)
        allSteps.add(new MergeState(
                "START",
                workArray,
                0, workArray.length - 1, -1, 0, //mid = -1 (mid wird später berechnet)
                "Start mit Array"
        ));

        // MergeSort durchführen und States sammeln
        mergeSort(workArray, 0, workArray.length - 1, 0);

        // End-State
        allSteps.add(new MergeState(
                "DONE",
                workArray,
                0, workArray.length - 1, -1, 0,
                "Sortierung abgeschlossen!"
        ));
    }

    private void mergeSort(int[] array, int left, int right, int depth) {
        if (left < right) {
            divide(array, left, right, depth);
        }
    }


    private void divide(int[] array, int left, int right, int depth) {
        int mid = (left + right) / 2;

        // divide State speichern
        allSteps.add(new MergeState(
                "DIVIDE",
                array.clone(), //Kopie vom Array
                left, right, mid, depth,
                String.format("Teile [%d..%d] bei Index %d", left, right, mid)
        ));

        // Rekursiv links
        mergeSort(array, left, mid, depth + 1);

        // Rekursiv rechts
        mergeSort(array, mid + 1, right, depth + 1);

        // Merge
        merge(array, left, mid, right, depth);
    }

    private void merge(int[] array, int left, int mid, int right, int depth) {
        // Temporäre Arrays erstellen
        int[] leftPart = new int[mid - left + 1];
        int[] rightPart = new int[right - mid];

        // Linke hälfte ins Temp kopieren
        for (int i = 0; i < leftPart.length; i++) {
            leftPart[i] = array[left + i];
        }

        // Rechte hälfte ins Temp kopieren
        for (int i = 0; i < rightPart.length; i++) {
            rightPart[i] = array[mid + 1 + i];
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
                String.format("Merge [%d..%d] abgeschlossen", left, right)
        ));
    }

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

    public boolean canStepForward() {
        return currentStepIndex < allSteps.size() - 1;
    }

    public boolean canStepBackward() {
        return currentStepIndex > 0;
    }

    //Methoden für den Test aktuell, aber wird fürs GUI auch benötigt

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
}
