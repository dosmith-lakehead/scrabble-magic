package com.example.tiles;

public class Deck extends TileCollection {
    public Deck () {
        super();
        for (int i = 0; i < LetterScoreMap.keys.size(); i++) {
            for (int j = 0; j < LetterScoreMap.frequency.get(i); j++) {
                tiles.add(new LetterTile(LetterScoreMap.keys.get(i)));
            }
        }
    }
    public LetterTile deal(){
        return tiles.removeFirst();
    }

    public int size(){
        return tiles.size();
    }
}
