package com.example.tiles;

import java.util.ArrayList;

public class SpellingField extends TileCollection{
    public SpellingField(){
        super();
    }

    public void acceptTile(LetterTile tile){
        tile.spellingPosition = tiles.size();
        tiles.add(tile);
        realignTiles();
    }

    public LetterTile returnTile(int tileIndex){
        LetterTile tile = tiles.remove(tileIndex);
        for (int i=0; i<tiles.size(); i++){
            tiles.get(i).spellingPosition = i;
        }
        realignTiles();
        return tile;
    }

    public void realignTiles() {
        for (int i = 0; i < tiles.size(); i++){
            tiles.get(i).updateTarget(new double[]{200 + (400 - tiles.size() * 50)/2.0 + i * 50, 400});
        }
    }

    public LetterTile checkIfClicked(double[] clickPos){
        for (LetterTile tile : tiles){
            if ((tile.getTopLeft()[0] < clickPos[0]) && (clickPos[0] < tile.getTopLeft()[0] + 50)) {
                return tile;
            }
        }
        return null;
    }

    public String getWord(){
        String word = "";
        for (int i = 0; i<tiles.size(); i++){
            word = word + tiles.get(i).getLetter().toString();
        }
        return word;
    }

    public int doDamage(){
        int damage = 0;
        for (int i = 0; i<tiles.size(); i++){
            damage += tiles.get(i).getValue();
            tiles.get(i).updateTarget(new double[]{1200, 250});
        }
        tiles.clear();
        return damage;
    }
}
