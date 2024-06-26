package com.example.tiles;

public class Hand extends TileCollection{

    private int owner;
    public Hand(int owner){
        super();
        this.owner = owner;
    }

    public LetterTile playTile(int handPosition){
        for (LetterTile tile : tiles){
            if (tile.getHandPosition() == handPosition){
                return tiles.remove(tiles.indexOf(tile));
            }
        }
        return null;
    }

    public void reclaimTile(LetterTile tile){
        for (int i=0; i<tiles.size(); i++){
            if (tiles.get(i).handPosition > tile.handPosition){
                tiles.add(i, tile);
                break;
            }
            if (i == tiles.size() - 1){
                tiles.add(tile);
                break;
            }
        }
        for (LetterTile tile2 : tiles) {
            tile2.updateTarget(new double[]{200 + tile2.handPosition * 50, 525});
        }
    }

    public void getTile(LetterTile newTile, int turn){
        if (newTile != null) {
            newTile.setOwner(owner);
            newTile.handPosition = tiles.size();
            tiles.add(newTile);
            if (owner == turn) {
                newTile.updateTarget(new double[]{200 + newTile.handPosition * 50, 525});
            } else {
                newTile.updateTarget(new double[]{200 + newTile.handPosition * 50, 25});
            }
        }
    }

    public void readjustHand(){
        for (int i = 0; i< tiles.size(); i++){
            tiles.get(i).handPosition = i;
            tiles.get(i).updateTarget(new double[]{200 + tiles.get(i).handPosition * 50, 525});
        }
    }

    public LetterTile checkIfClicked(double[] clickPos){
        for (LetterTile tile : tiles){
            if (tile.getTopLeft()[0] < clickPos[0] && clickPos[0] < tile.getTopLeft()[0] + 50) {
                System.out.println("clicked tile # " + tile.handPosition);
                return tile;
            }
        }
        return null;
    }

    public void changeTurn(int turn){
        for (int i = 0; i< tiles.size(); i++){
            tiles.get(i).updateTarget(new double[]{200 + tiles.get(i).handPosition * 50, (turn == owner ? 525 : 25)});
        }
    }
}
