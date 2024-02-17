package com.example.scrabblemagictest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScrabbleMagic extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ScrabbleMagic.class.getResource("scrabble-magic-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        stage.setResizable(false);
        stage.setTitle("Scrabble X Magic");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}