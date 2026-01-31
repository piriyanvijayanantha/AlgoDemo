package ch.fhnw.binarysearch.engine;

import ch.fhnw.binarysearch.section.InvariantType;

import java.util.Stack;

public class BinarySearchEngine {
    private Stack<State> history;

    private int[] array;
    private int target;
    private State currentState;
    private InvariantType invariant;

    public BinarySearchEngine() {
        history = new Stack<>();
    }

    public void start(int[] array, int target, InvariantType invariant) {
        this.invariant = invariant;
        this.array = array;
        this.target = target;
        int i = invariant.getIStart();
        int j = invariant.getJStart(array.length);
        int m = invariant.getmCalculation(i,j);
        currentState = new State(i, j, m, false);
        history.clear();
        history.push(currentState); //speichert Startzustand
    }

    public void step() {
        if (invariant.canContinue(currentState.i(), currentState.j())) {
            if (target == array[currentState.m()]) {
                currentState = new State(currentState.i(), currentState.j(), currentState.m(), true);
            } else if (target < array[currentState.m()]) {
                history.push(currentState);
                int newJ = invariant.getUpdatedJ(currentState.m());
                int newM = invariant.getmCalculation(currentState.i(), newJ);
                currentState = new State(currentState.i(), newJ, newM, false);
            } else {
                history.push(currentState);
                int newI = invariant.getUpdatedI(currentState.m());
                int newM = invariant.getmCalculation(newI, currentState.j());
                currentState = new State(newI, currentState.j(), newM, false);
            }
        }
    }

    public void undo() {
        if (hasHistoryStates()) {
            currentState = history.pop();
        }
    }

    public int getI() {
        return currentState.i();
    }

    public int getM() {
        return currentState.m();
    }

    public int getJ() {
        return currentState.j();
    }

    public InvariantType getInvariant() {
        return invariant;
    }

    public boolean isFound() {
        return currentState.isFound();
    }

    public boolean hasHistoryStates() {
        return !history.isEmpty();
    }
}
