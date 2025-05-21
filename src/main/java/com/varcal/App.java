package com.varcal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Aplicación JavaFX para análisis de Cadenas de Markov
 */
public class App extends Application {

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/varcal/markov_chain.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/varcal/styles/calculator.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Calculadora de cadenas de Markov");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}