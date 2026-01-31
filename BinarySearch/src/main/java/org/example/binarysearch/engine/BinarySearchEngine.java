package org.example.binarysearch.engine;

import org.example.binarysearch.section.InvariantType;

import java.util.Stack;

public class BinarySearchEngine {
    private Stack<State> history;

    private int[] array;
    private int target;
    private int i;
    private int m;
    private int j;
    private boolean isFound;
    private InvariantType invariant;

    public BinarySearchEngine() {
        history = new Stack<>();
    }

    public void start(int[] array, int target, InvariantType invariant) {
        this.invariant = invariant;
        this.isFound = false;
        this.array = array;
        this.target = target;
        this.i = invariant.getIStart();
        this.j = invariant.getJStart(array.length);
        this.m = invariant.getmCalculation(i,j);

        history.clear();
        history.push(new State(i, j, m, false)); //speichert Startzustand
    }

    public void step() {
        switch (invariant) {
            case BOTH_INCLUSIVE:
                stepBothInclusive();
                break;
            case LEFT_INCLUSIVE:
                stepLeftInclusive();
                break;
            case BOTH_EXCLUSIVE:
                stepBothExclusive();
                break;
            case RIGHT_INCLUSIVE:
                stepRightInclusive();
                break;
        }
    }

    // [i..j] - beide inklusiv
    private void stepBothInclusive() {
        if (invariant.canContinue(i,j)) {
            if (target == array[m]) {
                isFound = true;
            } else if (target < array[m]) {
                history.push(new State(i, j, m, isFound));
                j = m - 1;
                this.m = invariant.getmCalculation(i,j);
            } else {
                history.push(new State(i, j, m, isFound));
                i = m + 1;
                this.m = invariant.getmCalculation(i,j);
            }
        }

    }

    // [i..j) - i inklusiv, j exklusiv
    private void stepLeftInclusive() {
        if (invariant.canContinue(i, j)) {  // ← i < j (nicht <=)

            if (target == array[m]) {
                isFound = true;
            } else if (target < array[m]) {
                history.push(new State(i, j, m, isFound));
                j = m;  // ← j = m (nicht m-1)
                this.m = invariant.getmCalculation(i,j);
            } else {
                history.push(new State(i, j, m, isFound));
                i = m + 1;
                this.m = invariant.getmCalculation(i,j);
            }
        }
    }

    // (i..j) - beide exklusiv
    private void stepBothExclusive() {
        if (invariant.canContinue(i, j)) {  // ← Mindestens 1 Element dazwischen

            if (target == array[m]) {
                isFound = true;
            } else if (target < array[m]) {
                history.push(new State(i, j, m, isFound));
                j = m;  // ← j = m
                this.m = invariant.getmCalculation(i,j);
            } else {
                history.push(new State(i, j, m, isFound));
                i = m;  // ← i = m
                this.m = invariant.getmCalculation(i,j);
            }
        }
    }

    // (i..j] - i exklusiv, j inklusiv
    private void stepRightInclusive() {
        if (invariant.canContinue(i, j)) {
            history.push(new State(i, j, m, isFound));

            if (target == array[m]) {
                isFound = true;
            } else if (target < array[m]) {
                history.push(new State(i, j, m, isFound));
                j = m - 1;
                this.m = (i + j + 1) / 2; // aufrunden, da sonst m auf i landen könnte
            } else {
                history.push(new State(i, j, m, isFound));
                i = m;  // ← i = m (nicht m+1)
                this.m = (i + j + 1) / 2; //aufrunden, da sonst m auf i landen könnte
            }
        }
    }

    public void undo() {
        State vorherigerStand = history.pop();
        this.i = vorherigerStand.i();
        this.j = vorherigerStand.j();
        this.m = vorherigerStand.m();
        this.isFound = vorherigerStand.isFound();
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

    public InvariantType getInvariant() {
        return invariant;
    }

    public boolean isFound() {
        return isFound;
    }

    public boolean isHistoryEmpty() {
        return history.isEmpty();
    }
}
