package org.example.binarysearch.engine;

public class State {
    final int i;
    final int j;
    final int m;
    final boolean isFound;

    State(int i, int j, int m, boolean isFound) {
        this.i = i;
        this.j = j;
        this.m = m;
        this.isFound = isFound;
    }
}
