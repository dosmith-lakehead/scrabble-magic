package com.example.scrabblemagictest;

import javafx.scene.image.Image;

import java.util.Map;

public class letterTile {
    private char letter;
    private int value;
    private int handPosition;
    private int owner;
    private int[] topLeft;
    private int[] targetTopLeft;
    private int speed;

    private Image image;

    public letterTile(char letter){
        setLetter(letter);
        setValue(letter);

    }

    private void setLetter(char letter){
        if(letterScoreMap.keys.contains(letter)){
            this.letter = letter;
        }
        else throw new IllegalArgumentException("Invalid letter value entered.");
    }

    private void setValue(char letter){
        this.value = letterScoreMap.getScore(letter);
    }






}
