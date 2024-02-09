package com.example.tiles;

import java.util.Arrays;
import java.util.List;

public class LetterScoreMap {
    public static List<Character> keys = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
    public static List<Integer> values = Arrays.asList(1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4,4, 8, 4, 10);
    public static List<Integer> frequency =Arrays.asList(9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1);
    public static int getScore(Character character){
        int index = keys.indexOf(character);
        return values.get(index);
    }
}

