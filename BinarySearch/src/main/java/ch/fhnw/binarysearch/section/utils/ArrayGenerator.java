package ch.fhnw.binarysearch.section.utils;

import java.util.Arrays;
import java.util.Random;

public class ArrayGenerator {
    private static final Random random = new Random();

    public static int[] generateSortedArray(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Größe muss positiv sein");
        }
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100) + 1;
        }
        Arrays.sort(array);

        return array;
    }
}
