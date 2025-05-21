package com.varcal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Aplicación JavaFX para análisis de Cadenas de Markov
 */
public class App extends Application {

    @Override
   public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/varcal/markov_chain.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Calculadora de cadenas de Markov");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}