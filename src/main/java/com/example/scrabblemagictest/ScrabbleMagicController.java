package com.example.scrabblemagictest;

import com.example.tiles.Deck;
import com.example.tiles.Hand;
import com.example.tiles.LetterTile;
import com.example.tiles.SpellingField;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ScrabbleMagicController implements Initializable {
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

    private int turn;
    private Deck deck;
    private SpellingField spellingField;
    private Hand player1Hand;
    private Hand player2Hand;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        turn = 1;
        deck = new Deck();
        spellingField = new SpellingField();
        player1Hand = new Hand(1);
        player2Hand = new Hand(2);
        for (int i = 0; i<8; i++){
            player1Hand.getTile(deck.deal(), turn);
            player2Hand.getTile(deck.deal(), turn);
        }
        playField.setOnMouseClicked(mouseEvent ->{
            handleClick(mouseEvent);
        });
    }

    public void handleClick(MouseEvent mouseEvent){
        double[] clickPos = new double[]{mouseEvent.getSceneX(), mouseEvent.getSceneY()};
        if (clickPos[1] > 625 && clickPos[1] < 675){
            LetterTile clickedTile = (turn == 1 ? player1Hand : player2Hand).checkIfClicked(clickPos);
            if (clickedTile != null){
                spellingField.acceptTile((turn == 1 ? player1Hand : player2Hand).playTile(clickedTile.getHandPosition()));
            }
        }
        else if (clickPos[1] > 400 && clickPos[1] < 850){
            LetterTile clickedTile = spellingField.checkIfClicked(clickPos);
            if (clickedTile != null){
                (turn == 1 ? player1Hand : player2Hand).reclaimTile(spellingField.returnTile(clickedTile.getSpellingPosition()));
            }
        }
    }
}