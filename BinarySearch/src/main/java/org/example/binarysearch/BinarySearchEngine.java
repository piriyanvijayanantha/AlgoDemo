package org.example.binarysearch;

public class BinarySearchEngine {

    private int[] array = {0, 1, 2, 6, 7};
    private int target = 6;
    private int i;
    private int m;
    private int j;
    private boolean isFound;

    public BinarySearchEngine() {
    }

    public void start(int[] array, int target) {
        this.array = array;
        this.target = target;
        this.i = 0;
        this.j = this.array.length -1;
        this.m = -1; //noch nicht berechnet;
    }

    public boolean step() {

        if (i > j) {
            System.out.println("nichts Gefunden");
            return false;
        }

        this.m = (i + j) / 2;
        System.out.println("Prüfe: i=" + i + " j=" + j + " m=" + m + " array[m]=" + array[m]);

        if (target == array[m]){
            System.out.println("Gefunden!");
            isFound = true;
            return false;
        }else if (target < array[m]) {
            j = m - 1;
        } else {
            i = m + 1;
        }

        return true;
    }

    //zum testen
    //todo JUnit Test durchführen!
    public static void main(String[] args) {
        BinarySearchEngine engine = new BinarySearchEngine();
        int[] testArray = {1, 2, 3, 5, 8, 13, 22, 44, 66, 77, 88, 99, 999, 9999};
        engine.start(testArray, 77);
        while (engine.step()) {
        }
    }
}
