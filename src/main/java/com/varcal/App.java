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
    public void start(Stage stage) throws IOException {
        // Cargar el FXML principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("markov_chain.fxml"));
        Parent root = loader.load();

        // Configurar la escena y el escenario
        Scene scene = new Scene(root);

        // Añadir estilos CSS (opcional)
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        stage.setTitle("Calculadora de Cadenas de Markov");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}