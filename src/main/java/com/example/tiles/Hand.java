package com.example.tiles;

public class Hand extends TileCollection{

    private int owner;
    public Hand(int owner){
        super();
        this.owner = owner;
    }

    public LetterTile playTile(int tileIndex){
        return tiles.remove(tileIndex);
    }

    public void reclaimTile(LetterTile tile){
        tiles.add(tile.handPosition, tile);
        tile.updateTarget(new double[]{200 + tile.handPosition * 50, 625});
    }

    public void getTile(LetterTile newTile, int turn){
        newTile.setOwner(owner);
        newTile.handPosition = tiles.size();
        tiles.add(newTile);
        if (owner == turn){
            newTile.updateTarget(new double[]{200 + newTile.handPosition * 50, 625});
        }
        else {
            newTile.updateTarget(new double[]{550 - newTile.handPosition * 50, 125});
        }
    }

    public void readjustHand(){
        for (int i = 0; i< tiles.size(); i++){
            tiles.get(i).handPosition = i;
            tiles.get(i).updateTarget(new double[]{200 + tiles.get(i).handPosition * 50, 625});
        }
    }

    public LetterTile checkIfClicked(double[] clickPos){
        for (LetterTile tile : tiles){
            if (tile.getTopLeft()[0] < clickPos[0] && clickPos[0] < tile.getTopLeft()[0] + 50) {
                return tile;
            }
        }
        return null;
    }
}
