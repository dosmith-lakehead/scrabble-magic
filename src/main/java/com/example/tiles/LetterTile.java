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
    public boolean selected;
    private boolean fired;

    private Image image;

    public LetterTile(char letter){
        setLetter(letter);
        setValue(letter);
        setImage();
        topLeft = new double[] {-50, 375};
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

    private void setImage() {
        String imageName = "images/letter-" + String.valueOf(letter) + ".png";
        System.out.println(imageName);
        this.image = new Image(LetterTile.class.getResourceAsStream(imageName));
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
                if (speed < 10) {
                    speed += 0.05;
                }
            }
            else if (Math.sqrt((targetTopLeft[0] - topLeft[0])*(targetTopLeft[0] - topLeft[0]) + (targetTopLeft[1] - topLeft[1])*(targetTopLeft[1] - topLeft[1])) < 1 * movementInitialDistance/3){
                if (speed > 0) {
                    speed -= 0.05;
                }
            }
            topLeft[0] = topLeft[0] + (targetTopLeft[0] - topLeft[0]) * 3*speed/Math.sqrt((targetTopLeft[0] - topLeft[0])*(targetTopLeft[0] - topLeft[0]) + (targetTopLeft[1] - topLeft[1])*(targetTopLeft[1] - topLeft[1]));
            topLeft[1] = topLeft[1] + (targetTopLeft[1] - topLeft[1]) * 3*speed/Math.sqrt((targetTopLeft[0] - topLeft[0])*(targetTopLeft[0] - topLeft[0]) + (targetTopLeft[1] - topLeft[1])*(targetTopLeft[1] - topLeft[1]));
            if ((Math.abs(targetTopLeft[0] - topLeft[0])) <= 5 && (Math.abs(targetTopLeft[1] - topLeft[1])) <= 5){
                topLeft = targetTopLeft;
                moving = false;
                speed = 0;
            }
        }
    }
    public void updateTarget(double[] newXYpos){
        targetTopLeft = newXYpos;
        moving = false;
    }

    public double[] getTopLeft(){
        return topLeft;
    }

    public int getHandPosition(){
        return handPosition;
    }

    public int getSpellingPosition(){
        return spellingPosition;
    }

    public Image getImage(){
        return this.image;
    }

    public Character getLetter(){
        return letter;
    }
    public int getValue(){
        return value;
    }

    public void select(){
        selected = true;
    }
    public void deselect(){
        selected = false;
    }
}
