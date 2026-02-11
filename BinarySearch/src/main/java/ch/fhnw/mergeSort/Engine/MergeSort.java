package ch.fhnw.mergeSort.Engine;

import java.util.Arrays;

//Zum Lernen von Mergesort
public class MergeSort {
    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            divide(array, left, right);
        }
    }

    private static void divide(int[] array, int left, int right) {
        int mid = (left + right) / 2;

        // Rekursiv links sortieren
        mergeSort(array, left, mid);

        // Rekursiv rechts sortieren
        mergeSort(array, mid + 1, right);

        // Beide Hälften zusammenführen
        merge(array, left, mid, right);
    }

    private static void merge(int[] array, int left, int mid, int right){

        int[] leftPart = new int[mid - left + 1];
        int[] rightPart = new int[right - mid];

        for (int i = 0; i < leftPart.length; i++) {
            leftPart[i] = array[left + i];
        }
        for (int i = 0; i < rightPart.length; i++) {
            rightPart[i] = array[mid + 1 + i];
        }
        // Merge-Prozess
        int i = 0;  // Index für leftPart
        int j = 0;  // Index für rightPart
        int k = left;  // Index für array

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

        // Rest von leftPart kopieren (falls vorhanden)
        while (i < leftPart.length) {
            array[k] = leftPart[i];
            i++;
            k++;
        }

        // Rest von rightPart kopieren (falls vorhanden)
        while (j < rightPart.length) {
            array[k] = rightPart[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        int[] array = {7,3,5,8};

        System.out.println("Original: " + Arrays.toString(array));

        mergeSort(array, 0, array.length - 1);

        System.out.println("Sortiert: " + Arrays.toString(array));
    }
}
