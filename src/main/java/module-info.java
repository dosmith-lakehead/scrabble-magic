module com.example.scrabblemagictest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.scrabblemagictest to javafx.fxml;
    exports com.example.scrabblemagictest;
    exports com.example.tiles;
    opens com.example.tiles to javafx.fxml;
}