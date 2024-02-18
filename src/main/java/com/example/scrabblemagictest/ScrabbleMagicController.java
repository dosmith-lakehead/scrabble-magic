package com.example.scrabblemagictest;

import com.example.tiles.*;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

import java.io.File;
import java.net.URL;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ScrabbleMagicController implements Initializable {
    @FXML
    private AnchorPane parentPane;

    @FXML
    private Label bottomPlayerHealth;

    @FXML
    private ImageView bottomPlayerHeart;

    @FXML
    private Label bottomPlayerLabel;

    @FXML
    private Canvas playField;

    @FXML
    private Label topPlayerHealth;

    @FXML
    private ImageView topPlayerHeart;

    @FXML
    private Label topPlayerLabel;

    @FXML
    private Label damageNumber;

    @FXML
    private Button modeButton;

    private int turn;
    private Deck deck;
    private SpellingField spellingField;
    private Hand player1Hand;
    private Hand player2Hand;
    private TileCollection trash;
    private Scene scene = null;
    private ArrayList<String> words;
    private String mode = "spelling";
    private LetterTile firedTile = null;
    private LetterTile hitTile = null;


    private Clock clock;
    private long lastFrameTime;
    private long actionStartTime;

    AnimationTimer draw;


    private GraphicsContext gc;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadWords();
        gc = playField.getGraphicsContext2D();
        gc.setStroke(Paint.valueOf("red"));
        gc.setLineWidth(5);
        turn = 1;
        deck = new Deck();
        spellingField = new SpellingField();
        player1Hand = new Hand(1);
        player2Hand = new Hand(2);
        trash = new TileCollection();
        for (int i = 0; i<8; i++){
            player1Hand.getTile(deck.deal(), turn);
            player2Hand.getTile(deck.deal(), turn);
        }
        playField.setOnMouseClicked(mouseEvent ->{
            handleClick(mouseEvent);
        });
        modeButton.setOnMouseClicked(mouseEvent ->{
            switchModes();
        });
        clock = Clock.systemDefaultZone();
        draw = new AnimationTimer(){
            @Override
            public void handle(long now) {
                drawToCanvas();
            }
        };
        draw.start();
    }

    public void handleClick(MouseEvent mouseEvent){
        if (scene == null){
            scene = parentPane.getScene();
            scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    confirm();
                }
            });
        }
        double[] clickPos = new double[]{mouseEvent.getX(), mouseEvent.getY()};
        if (clickPos[1] > 525 && clickPos[1] < 575){
            LetterTile clickedTile = (turn == 1 ? player1Hand : player2Hand).checkIfClicked(clickPos);
            if (mode.equals("spelling")) {
                if (clickedTile != null) {
                    spellingField.acceptTile((turn == 1 ? player1Hand : player2Hand).playTile(clickedTile.getHandPosition()));
                }
            }
            else {
                if (clickedTile != null) {
                    for (LetterTile tile : (turn ==1 ? player1Hand : player2Hand).getTiles()) {
                        tile.deselect();
                    }
                    clickedTile.select();
                }
            }
        }
        else if (clickPos[1] > 400 && clickPos[1] < 850){
            LetterTile clickedTile = spellingField.checkIfClicked(clickPos);
            if (clickedTile != null){
                (turn == 1 ? player1Hand : player2Hand).reclaimTile(spellingField.returnTile(clickedTile.getSpellingPosition()));
            }
        }
        parentPane.requestFocus();
    }

    public void drawToCanvas(){
        if (damageNumber.getOpacity() > 0){
            damageNumber.setOpacity(damageNumber.getOpacity() - 0.04);
        }
        gc.clearRect(0, 0, playField.getWidth(), playField.getHeight());
        lastFrameTime = clock.millis();
        for (LetterTile tile : player1Hand.getTiles()){
            tile.updateXYPos();
            gc.drawImage(tile.getImage(), tile.getTopLeft()[0], tile.getTopLeft()[1]);
            if (tile.selected){
                gc.strokePolygon(new double[] {tile.getTopLeft()[0], tile.getTopLeft()[0] + 50, tile.getTopLeft()[0] + 50, tile.getTopLeft()[0]}, new double[] {tile.getTopLeft()[1], tile.getTopLeft()[1], tile.getTopLeft()[1] + 50, tile.getTopLeft()[1] + 50}, 4);
            }
        }
        for (LetterTile tile : player2Hand.getTiles()){
            tile.updateXYPos();
            gc.drawImage(tile.getImage(), tile.getTopLeft()[0], tile.getTopLeft()[1]);
            if (tile.selected){
                gc.strokePolygon(new double[] {tile.getTopLeft()[0], tile.getTopLeft()[0] + 50, tile.getTopLeft()[0] + 50, tile.getTopLeft()[0]}, new double[] {tile.getTopLeft()[1], tile.getTopLeft()[1], tile.getTopLeft()[1] + 50, tile.getTopLeft()[1] + 50}, 4);
            }
        }
        for (LetterTile tile : spellingField.getTiles()){
            tile.updateXYPos();
            gc.drawImage(tile.getImage(), tile.getTopLeft()[0], tile.getTopLeft()[1]);
            if (tile.selected){
                gc.strokePolygon(new double[] {tile.getTopLeft()[0], tile.getTopLeft()[0] + 50, tile.getTopLeft()[0] + 50, tile.getTopLeft()[0]}, new double[] {tile.getTopLeft()[1], tile.getTopLeft()[1], tile.getTopLeft()[1] + 50, tile.getTopLeft()[1] + 50}, 4);
            }
        }
        for (LetterTile tile : trash.getTiles()){
            tile.updateXYPos();
            gc.drawImage(tile.getImage(), tile.getTopLeft()[0], tile.getTopLeft()[1]);
            if (tile.selected){
                gc.strokePolygon(new double[] {tile.getTopLeft()[0], tile.getTopLeft()[0] + 50, tile.getTopLeft()[0] + 50, tile.getTopLeft()[0]}, new double[] {tile.getTopLeft()[1], tile.getTopLeft()[1], tile.getTopLeft()[1] + 50, tile.getTopLeft()[1] + 50}, 4);
            }
        }
    }
    public void confirm() {
        damageNumber.setText("0");
        draw.stop();
        actionStartTime = clock.millis();
        draw = new AnimationTimer(){
            @Override
            public void handle(long now) {
                endTurnAction();
            }
        };
        draw.start();
        if (mode.equals("spelling")){
            System.out.println(spellingField.getWord());
            if (words.contains(spellingField.getWord())) {
                for (LetterTile tile : spellingField.getTiles()) {
                    trash.trashTile(tile);
                }
                int damage = spellingField.doDamage();
                damageNumber.setText(" -" + String.valueOf(damage));
                topPlayerHealth.setText(String.valueOf(Integer.valueOf(topPlayerHealth.getText()) - damage));
            }
        }
        else {
            for (int i = 0; i< player1Hand.size(); i++){
                if ((turn == 1 ? player1Hand : player2Hand).getTiles().get(i).selected){
                    firedTile = (turn == 1 ? player1Hand : player2Hand).getTiles().get(i);
                    firedTile.fire();
                    break;
                }
            }
            trash.trashTile((turn == 1 ? player1Hand : player2Hand).playTile(firedTile.getHandPosition()));
        }
    }

    public void loadWords(){
        words = new ArrayList<String>();
        try {
            File file = new File("src/main/resources/com/example/scrabblemagictest/2of12inf.txt");
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                words.add(line);
            }
            input.close();
            File file2 = new File("src/main/resources/com/example/scrabblemagictest/3of6game.txt");
            input = new Scanner(file2);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                words.add(line);
            }
            input.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void switchModes(){
        if (mode.equals("spelling")){
            mode = "shooting";
            for (int i = spellingField.size() - 1; i >= 0; i--){
                (turn == 1 ? player1Hand : player2Hand).reclaimTile(spellingField.returnTile(i));
            }
            modeButton.setText("Shooting Mode");
        }
        else {
            for (LetterTile tile : (turn == 1 ? player1Hand : player2Hand).getTiles()) {
                tile.deselect();
            }
            mode = "spelling";
            modeButton.setText("Spelling Mode");
        }
        parentPane.requestFocus();
    }

    public void changeTurn(){
        damageNumber.setTranslateY((turn == 1 ? 700 : -700));
        turn = (turn == 1 ? 2 : 1);
        player1Hand.changeTurn(turn);
        player2Hand.changeTurn(turn);
        topPlayerLabel.setText("Player " + (turn == 1 ? 2 : 1));
        bottomPlayerLabel.setText("Player " + turn);
        String tempHealth = topPlayerHealth.getText();
        topPlayerHealth.setText(bottomPlayerHealth.getText());
        bottomPlayerHealth.setText(tempHealth);
    }

    public void endTurnAction(){
        lastFrameTime = clock.millis();
        if (lastFrameTime - actionStartTime < 2000) {
            if (firedTile != null){
                if (firedTile.getTopLeft()[1] <= 75 && hitTile == null) {
                    hitTile = (turn == 2 ? player1Hand : player2Hand).getTiles().get(firedTile.getHandPosition());
                    hitTile.updateTarget(new double[]{hitTile.getTopLeft()[0], hitTile.getTopLeft()[1] - 2000});
                    trash.trashTile((turn == 2 ? player1Hand : player2Hand).playTile(hitTile.getHandPosition()));
                    int damage = (hitTile.getValue() + firedTile.getValue()) / 2;
                    damageNumber.setText(" -" + String.valueOf(damage));
                    topPlayerHealth.setText(String.valueOf(Integer.valueOf(topPlayerHealth.getText()) - damage));
                }
            }
            gc.clearRect(0, 0, playField.getWidth(), playField.getHeight());
            for (LetterTile tile : trash.getTiles()) {
                tile.updateXYPos();
                gc.drawImage(tile.getImage(), tile.getTopLeft()[0], tile.getTopLeft()[1]);
            }
            for (LetterTile tile : player2Hand.getTiles()) {
                if (tile.fired || tile.hit) {
                    tile.updateXYPos();
                }
                gc.drawImage(tile.getImage(), tile.getTopLeft()[0], tile.getTopLeft()[1]);
            }
            for (LetterTile tile : player1Hand.getTiles()) {
                if (tile.fired || tile.hit) {
                    tile.updateXYPos();
                }
                gc.drawImage(tile.getImage(), tile.getTopLeft()[0], tile.getTopLeft()[1]);
            }
            if (!damageNumber.getText().equals("0")) {
                if (lastFrameTime - actionStartTime < 1000) {
                    damageNumber.setOpacity(damageNumber.getOpacity() + 0.05);
                }
                else {
                    damageNumber.setOpacity(damageNumber.getOpacity() + 0.02);
                }
            }
        }
        else {
            if (mode.equals("spelling")){
                for (int i = (turn == 1 ? player1Hand : player2Hand).size(); i < 8; i++) {
                    (turn == 1 ? player1Hand : player2Hand).getTile(deck.deal(), turn);
                }
                (turn == 1 ? player1Hand : player2Hand).readjustHand();
                changeTurn();
            }
            else {
                (turn == 1 ? player1Hand : player2Hand).getTile(deck.deal(), turn);
                (turn == 1 ? player1Hand : player2Hand).readjustHand();
                (turn == 2 ? player1Hand : player2Hand).getTile(deck.deal(), turn);
                (turn == 2 ? player1Hand : player2Hand).readjustHand();
                changeTurn();
            }
            draw.stop();
            firedTile = null;
            hitTile = null;
            draw = new AnimationTimer(){
                @Override
                public void handle(long now) {
                    drawToCanvas();
                }
            };
            draw.start();
        }
    }
}