package org.example.binarysearch.engine;

import java.util.Stack;

public class BinarySearchEngine {
    private Stack<State> history;


    private int[] array;
    private int target;
    private int i;
    private int m;
    private int j;
    private boolean isFound;

    public BinarySearchEngine() {
        history = new Stack<>();
    }

    public void start(int[] array, int target) {
        this.isFound = false;
        this.array = array;
        this.target = target;
        this.i = 0 ;
        this.j = array.length -1;
        this.m = (i + j) / 2;

        history.clear();
        history.push(new State(i, j, m, isFound)); //speichert Startzustand
    }

    public boolean step() {
        if (i > j) {
            System.out.println("nichts Gefunden");
            return false;
        }
        history.push(new State(i, j, m, isFound));
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
    public boolean undo() {
        if (history.isEmpty()) {
            System.out.println("Keine vorherigen Schritte vorhanden");
            return false;
        }

        State vorherigerStand = history.pop();
        this.i = vorherigerStand.i;
        this.j = vorherigerStand.j;
        this.m = vorherigerStand.m;
        this.isFound = vorherigerStand.isFound;

        System.out.println("Undo: i=" + i + " j=" + j + " m=" + m);
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
