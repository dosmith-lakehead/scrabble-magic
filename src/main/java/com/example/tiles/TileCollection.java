package com.example.tiles;

import com.example.tiles.LetterScoreMap;
import com.example.tiles.LetterTile;

import java.util.ArrayList;

public class TileCollection {
    protected ArrayList<LetterTile> tiles;

    public TileCollection() {
        tiles = new ArrayList<LetterTile>();
    }

    public int size(){
        return tiles.size();
    }

    public ArrayList<LetterTile> getTiles(){
        return tiles;
    }
}
