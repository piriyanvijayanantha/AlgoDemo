package ch.fhnw.binarysearch.section;

import java.util.function.BiPredicate;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

public enum InvariantType {
    BOTH_INCLUSIVE("[i..j] (beide inklusiv)", 0, -1, (i, j) -> i <= j, (i, j) -> (i + j) / 2, m -> m + 1, m -> m - 1),
    LEFT_INCLUSIVE("[i..j) (i inkl, j exkl)", 0, 0, (i, j) -> i < j, (i, j) -> (i + j) / 2, m -> m + 1, m -> m),
    BOTH_EXCLUSIVE("(i..j) (beide exklusiv)", -1, 0, (i, j) -> i + 1 < j, (i, j) -> (i + j + 1) / 2, m -> m, m -> m), //Aufrunden
    RIGHT_INCLUSIVE("(i..j] (i exkl, j inkl)", -1, -1, (i, j) -> i < j, (i, j) -> (i + j + 1) / 2, m -> m, m -> m - 1); //Aufrunden

    private final String displayName;
    private final int iGrenze;
    private final int jGrenze;
    private final BiPredicate<Integer, Integer> continueCondition; //umgekehrte Abbruchbedinung
    private final IntBinaryOperator mCalculation;
    private final IntUnaryOperator iBoundaryUpdate;
    private final IntUnaryOperator jBoundaryUpdate;

    InvariantType(String displayName, int iGrenze, int jGrenze, BiPredicate<Integer, Integer> continueCondition, IntBinaryOperator mCalculation, IntUnaryOperator iBoundaryUpdate, IntUnaryOperator jBoundaryUpdate) {
        this.displayName = displayName;
        this.iGrenze = iGrenze;
        this.jGrenze = jGrenze;
        this.continueCondition = continueCondition;
        this.mCalculation = mCalculation;
        this.iBoundaryUpdate = iBoundaryUpdate;
        this.jBoundaryUpdate = jBoundaryUpdate;
    }

    // Überprüft das Gegenteil der Abbruchbedingung
    public boolean canContinue(int i, int j) {
        return continueCondition.test(i, j);
    }

    // Prüft ob Index im Suchbereich liegt
    public boolean isInRange(int idx, int i, int j) {
        return switch (this) {
            case BOTH_INCLUSIVE -> idx >= i && idx <= j;
            case LEFT_INCLUSIVE -> idx >= i && idx < j;
            case BOTH_EXCLUSIVE -> idx > i && idx < j;
            case RIGHT_INCLUSIVE -> idx > i && idx <= j;
        };
    }

    public int getIStart() {
        return iGrenze;
    }

    public int getJStart(int arrayLength) {
        return arrayLength + jGrenze;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public int getmCalculation(int i, int j) {
        return mCalculation.applyAsInt(i, j);
    }

    public int getUpdatedI(int m) {
        return iBoundaryUpdate.applyAsInt(m);
    }

    public int getUpdatedJ(int m) {
        return jBoundaryUpdate.applyAsInt(m);
    }
}