package com.example.scrabblemagictest;

import com.example.tiles.Deck;
import com.example.tiles.Hand;
import com.example.tiles.SpellingField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ScrabbleMagicController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int turn = 1;
        Deck deck = new Deck();
        SpellingField spellingField = new SpellingField();
        Hand player1Hand = new Hand(1);
        Hand player2Hand = new Hand(2);
        for (int i = 0; i<8; i++){
            player1Hand.getTile(deck.deal(), turn);
            player2Hand.getTile(deck.deal(), turn);
        }
    }
}