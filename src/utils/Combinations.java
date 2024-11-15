package utils;

import java.util.ArrayList;
import java.util.Random;

public class Combinations {
    public static ArrayList<Integer> withoutRepetition(Random r, int n, int k) {
        ArrayList<Integer> availableIndexes = new ArrayList<>();
        ArrayList<Integer> pickedIndexes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            availableIndexes.add(i);
        }
        for (int i = 0; i < k; i++) {
            int indexOfPickedIndex = r.nextInt(0, availableIndexes.size());
            Integer pickedIndex = availableIndexes.get(indexOfPickedIndex);
            availableIndexes.remove(indexOfPickedIndex);
            pickedIndexes.add(pickedIndex);
        }
        return pickedIndexes;
    }
}
