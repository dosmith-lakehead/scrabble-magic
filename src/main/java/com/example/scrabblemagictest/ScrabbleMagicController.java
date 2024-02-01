package com.example.scrabblemagictest;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ScrabbleMagicController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}