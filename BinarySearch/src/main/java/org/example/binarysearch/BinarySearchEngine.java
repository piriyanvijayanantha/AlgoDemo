package org.example.binarysearch;

public class BinarySearchEngine {

    private int[] array;
    private int target;
    private int i;
    private int m;
    private int j;
    private boolean isFound;

    public BinarySearchEngine() {
    }

    public void start(int[] array, int target) {
        this.isFound = false;
        this.array = array;
        this.target = target;
        this.i = 0 ;
        this.j = array.length -1;
        this.m = (i + j) / 2;
    }

    public boolean step() {
        if (i > j) {
            System.out.println("nichts Gefunden");
            return false;
        }

        if (target == array[m]){
            System.out.println("Gefunden!");
            isFound = true;
            return false;
        }else if (target < array[m]) {
            j = m - 1;
        } else {
            i = m + 1;
        }

        this.m = (i + j) / 2;
        System.out.println("Prüfe: i=" + i + " j=" + j + " m=" + m + " array[m]=" + array[m]);

        return true;
    }

    public int[] getArray() {
        return array;
    }

    public int getTarget() {
        return target;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getI() {
        return i;
    }

    public int getM() {
        return m;
    }

    public int getJ() {
        return j;
    }

    public boolean isFound() {
        return isFound;
    }
}
