package com.example.tiles;

import javafx.scene.image.Image;

public class LetterTile {
    private char letter;
    private int value;
    private int handPosition;
    private int owner;
    private int[] topLeft;
    private int[] targetTopLeft;
    private int speed;
    private int spellingPosition;
    private boolean selected;
    private boolean fired;

    private Image image;

    public LetterTile(char letter){
        setLetter(letter);
        setValue(letter);
    }

    private void setLetter(char letter){
        if(LetterScoreMap.keys.contains(letter)){
            this.letter = letter;
        }
        else throw new IllegalArgumentException("Invalid letter value entered.");
    }

    private void setValue(char letter){
        this.value = LetterScoreMap.getScore(letter);
    }

    public void setOwner (int player){
        this.owner = player;
    }

    public void setHandPosition (int position){
        this.handPosition = position;
    }

    public void spellingPosition (int position){
        this.spellingPosition = position;
    }






}
