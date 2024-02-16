package com.example.tiles;

import javafx.scene.image.Image;

import java.util.Arrays;

public class LetterTile {
    private char letter;
    private int value;
    protected int handPosition;
    private int owner;
    private double[] topLeft;
    private double[] targetTopLeft;
    boolean moving = false;
    private double movementInitialDistance;
    private double speed;
    protected int spellingPosition;
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

    public void updateXYPos(){
        if (!Arrays.equals(targetTopLeft, topLeft)){
            if (!moving){
                moving = true;
                movementInitialDistance = Math.sqrt((targetTopLeft[0] - topLeft[0])*(targetTopLeft[0] - topLeft[0]) + (targetTopLeft[1] - topLeft[1])*(targetTopLeft[1] - topLeft[1]));
            }
            if (Math.sqrt((targetTopLeft[0] - topLeft[0])*(targetTopLeft[0] - topLeft[0]) + (targetTopLeft[1] - topLeft[1])*(targetTopLeft[1] - topLeft[1])) > 2 * movementInitialDistance/3){
                if (speed < 5) {
                    speed += 0.01;
                }
            }
            else if (Math.sqrt((targetTopLeft[0] - topLeft[0])*(targetTopLeft[0] - topLeft[0]) + (targetTopLeft[1] - topLeft[1])*(targetTopLeft[1] - topLeft[1])) < 1 * movementInitialDistance/3){
                if (speed > 0) {
                    speed -= 0.01;
                }
            }
            topLeft[0] = topLeft[0] + (targetTopLeft[0] - topLeft[0]) * speed/Math.sqrt((targetTopLeft[0] - topLeft[0])*(targetTopLeft[0] - topLeft[0]) + (targetTopLeft[1] - topLeft[1])*(targetTopLeft[1] - topLeft[1]));
            topLeft[1] = topLeft[1] + (targetTopLeft[1] - topLeft[1]) * speed/Math.sqrt((targetTopLeft[0] - topLeft[0])*(targetTopLeft[0] - topLeft[0]) + (targetTopLeft[1] - topLeft[1])*(targetTopLeft[1] - topLeft[1]));
            if ((targetTopLeft[0] - topLeft[0]) <= 1 && (targetTopLeft[1] - topLeft[1]) <= 1){
                topLeft = targetTopLeft;
                moving = false;
                speed = 0;
            }
        }
    }
    public void updateTarget(double[] newXYpos){
        targetTopLeft = newXYpos;
    }
}
