package com.example.tiles;

import java.util.Collections;

public class Deck extends TileCollection {
    public Deck () {
        super();
        for (int i = 0; i < LetterScoreMap.keys.size(); i++) {
            for (int j = 0; j < LetterScoreMap.frequency.get(i); j++) {
                tiles.add(new LetterTile(LetterScoreMap.keys.get(i)));
            }
        }
        Collections.shuffle(tiles);
    }
    public LetterTile deal(){
        if (tiles.size() > 0){
            return tiles.removeFirst();
        }
        else{
            return null;
        }
    }
}
