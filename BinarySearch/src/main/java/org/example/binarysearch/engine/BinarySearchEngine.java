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
        this.i = 0;
        this.j = array.length - 1;
        this.m = (i + j) / 2;

        history.clear();
        history.push(new State(i, j, m, isFound)); //speichert Startzustand
    }

    public void step() {
        if (i <= j) { // Abbruchbedung i>j
            if (target == array[m]) {
                isFound = true;
            } else if (target < array[m]) {
                j = m - 1;
                this.m = (i + j) / 2;
            } else {
                i = m + 1;
                this.m = (i + j) / 2;
            }
        }
    }

    public void undo() {
        State vorherigerStand = history.pop();
        this.i = vorherigerStand.i;
        this.j = vorherigerStand.j;
        this.m = vorherigerStand.m;
        this.isFound = vorherigerStand.isFound;
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

    public boolean isHistoryEmpty() {
        return history.isEmpty();
    }
}
