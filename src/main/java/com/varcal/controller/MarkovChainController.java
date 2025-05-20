package com.varcal.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.varcal.logica.MarkovChain;
import com.varcal.logica.ResultCanonical;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

public class MarkovChainController {

    // FXML Elements
    @FXML
    private Spinner<Integer> numStatesSpinner;
    @FXML
    private Spinner<Integer> stepsSpinner;
    @FXML
    private GridPane matrixGrid;
    @FXML
    private HBox initialStateContainer;
    @FXML
    private VBox errorContainer;
    @FXML
    private Label errorMessage;
    @FXML
    private VBox resultContainer;

    // State variables
    private int numStates = 3;
    private int steps = 1;
    private TextField[][] matrixFields;
    private TextField[] initialStateFields;
    private List<String> stateLabels = new ArrayList<>();

    // Format for displaying decimal numbers
    private final DecimalFormat df = new DecimalFormat("0.####");

    @FXML
    public void initialize() {
        // Initialize spinners
        numStatesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 10, 3));
        numStatesSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            numStates = newValue;
        });

        stepsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        stepsSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            steps = newValue;
        });

        // Initial setup
        initializeDefaultStateLabels();
        generateMatrixUI();
    }

    private void initializeDefaultStateLabels() {
        stateLabels.clear();
        for (int i = 0; i < 10; i++) { // Pre-initialize for maximum size
            stateLabels.add(String.valueOf(i));
        }
    }

    @FXML
    private void handleGenerateMatrix() {
        numStates = numStatesSpinner.getValue();
        generateMatrixUI();
    }

    private void generateMatrixUI() {
        // Clear existing grid
        matrixGrid.getChildren().clear();
        initialStateContainer.getChildren().clear();

        // Initialize arrays
        matrixFields = new TextField[numStates][numStates];
        initialStateFields = new TextField[numStates];

        // Add column labels
        for (int j = 0; j < numStates; j++) {
            TextField labelField = new TextField(stateLabels.get(j));
            labelField.setPrefWidth(60);
            labelField.setAlignment(Pos.CENTER);

            final int index = j;
            labelField.textProperty().addListener((obs, oldValue, newValue) -> {
                stateLabels.set(index, newValue);
            });

            matrixGrid.add(labelField, j + 1, 0);
        }

        // Create matrix input fields
        for (int i = 0; i < numStates; i++) {
            // Row labels
            Label rowLabel = new Label(stateLabels.get(i));
            rowLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
            matrixGrid.add(rowLabel, 0, i + 1);

            for (int j = 0; j < numStates; j++) {
                TextField field = createDoubleTextField();
                // Default values for demonstration
                if (i == j) {
                    field.setText("0.5");
                } else if (j == (i + 1) % numStates) {
                    field.setText("0.5");
                } else {
                    field.setText("0.0");
                }

                matrixFields[i][j] = field;
                matrixGrid.add(field, j + 1, i + 1);
            }
        }

        // Create initial state vector inputs
        for (int i = 0; i < numStates; i++) {
            HBox stateBox = new HBox(5);
            stateBox.setAlignment(Pos.CENTER_LEFT);

            Label stateLabel = new Label(stateLabels.get(i) + ":");
            TextField stateField = createDoubleTextField();

            if (i == 0) {
                stateField.setText("1.0");
            } else {
                stateField.setText("0.0");
            }

            initialStateFields[i] = stateField;
            stateBox.getChildren().addAll(stateLabel, stateField);
            initialStateContainer.getChildren().add(stateBox);
        }
    }

    private TextField createDoubleTextField() {
        TextField field = new TextField();
        field.setPrefWidth(60);
        field.setAlignment(Pos.CENTER);

        // Create text formatter to only allow numeric input
        StringConverter<Double> converter = new DoubleStringConverter();
        TextFormatter<Double> textFormatter = new TextFormatter<>(converter, 0.0,
                change -> {
                    String newText = change.getControlNewText();
                    if (newText.isEmpty()) {
                        return change;
                    }

                    try {
                        Double value = Double.parseDouble(newText);
                        if (value >= 0 && value <= 1) {
                            return change;
                        }
                    } catch (NumberFormatException e) {
                        // Not a valid double
                    }
                    return null;
                });

        field.setTextFormatter(textFormatter);
        return field;
    }

    @FXML
    private void handleCalculateProjection() {
        try {
            double[][] matrix = getTransitionMatrix();

            // Verify if matrix is stochastic
            if (!MarkovChain.isStochastic(matrix)) {
                showError("La matriz no es estocástica. Cada fila debe sumar 1.");
                return;
            }

            // Calculate P^n
            double[][] result = MarkovChain.power(matrix, steps);

            // Show result
            showMatrixResult("Matriz de Transición P^" + steps, result);

            // Get initial state vector
            double[] initialState = getInitialStateVector();

            // Calculate state after n steps: π₀P^n
            double[] stateAfterNSteps = MarkovChain.multiplyVectorMatrix(initialState, result);

            // Show vector result
            showVectorResult("Estado después de " + steps + " pasos", stateAfterNSteps);

            errorContainer.setVisible(false);
        } catch (Exception e) {
            showError("Error en el cálculo: " + e.getMessage());
        }
    }

    @FXML
    private void handleDetectAbsorbingStates() {
        try {
            double[][] matrix = getTransitionMatrix();

            // Verify if matrix is stochastic
            if (!MarkovChain.isStochastic(matrix)) {
                showError("La matriz no es estocástica. Cada fila debe sumar 1.");
                return;
            }

            boolean[] absorbingStates = MarkovChain.detectAbsorbingStates(matrix);

            // Show result
            StringBuilder result = new StringBuilder("Estados Absorbentes:\n");
            boolean hasAbsorbingStates = false;

            for (int i = 0; i < absorbingStates.length; i++) {
                if (absorbingStates[i]) {
                    result.append("• Estado ").append(stateLabels.get(i)).append("\n");
                    hasAbsorbingStates = true;
                }
            }

            if (!hasAbsorbingStates) {
                result.append("No se encontraron estados absorbentes.");
            }

            showTextResult("Detección de Estados Absorbentes", result.toString());
            errorContainer.setVisible(false);
        } catch (Exception e) {
            showError("Error en la detección: " + e.getMessage());
        }
    }

    @FXML
    private void handleCalculateCanonicalForm() {
        try {
            double[][] matrix = getTransitionMatrix();

            // Verify if matrix is stochastic
            if (!MarkovChain.isStochastic(matrix)) {
                showError("La matriz no es estocástica. Cada fila debe sumar 1.");
                return;
            }

            boolean[] absorbingStates = MarkovChain.detectAbsorbingStates(matrix);

            // Check if there are absorbing states
            boolean hasAbsorbingStates = false;
            for (boolean state : absorbingStates) {
                if (state) {
                    hasAbsorbingStates = true;
                    break;
                }
            }

            if (!hasAbsorbingStates) {
                showError("No hay estados absorbentes en la matriz.");
                return;
            }

            double[][] canonical = MarkovChain.reorderCanonical(matrix, absorbingStates);

            // Show result
            showMatrixResult("Matriz en Forma Canónica", canonical);
            errorContainer.setVisible(false);
        } catch (Exception e) {
            showError("Error en el cálculo de la forma canónica: " + e.getMessage());
        }
    }

    @FXML
    private void handleCalculateFundamentalMatrix() {
        calculateAbsorbingMatrices("N");
    }

    @FXML
    private void handleCalculateAbsorptionProbabilities() {
        calculateAbsorbingMatrices("B");
    }

    private void calculateAbsorbingMatrices(String matrixType) {
        try {
            double[][] matrix = getTransitionMatrix();

            // Verify if matrix is stochastic
            if (!MarkovChain.isStochastic(matrix)) {
                showError("La matriz no es estocástica. Cada fila debe sumar 1.");
                return;
            }

            boolean[] absorbingStates = MarkovChain.detectAbsorbingStates(matrix);

            // Check if there are absorbing states
            boolean hasAbsorbingStates = false;
            for (boolean state : absorbingStates) {
                if (state) {
                    hasAbsorbingStates = true;
                    break;
                }
            }

            if (!hasAbsorbingStates) {
                showError("No hay estados absorbentes en la matriz.");
                return;
            }

            ResultCanonical result = MarkovChain.computeCanonicalMatrices(matrix, absorbingStates);

            // Show result based on request
            if (matrixType.equals("N")) {
                showMatrixResult("Matriz Fundamental (N)", result.N);
            } else if (matrixType.equals("B")) {
                showMatrixResult("Matriz de Probabilidades de Absorción (B)", result.B);
            }

            errorContainer.setVisible(false);
        } catch (Exception e) {
            showError("Error en el cálculo: " + e.getMessage());
        }
    }

    private double[][] getTransitionMatrix() {
        double[][] matrix = new double[numStates][numStates];

        for (int i = 0; i < numStates; i++) {
            for (int j = 0; j < numStates; j++) {
                matrix[i][j] = Double.parseDouble(matrixFields[i][j].getText());
            }
        }

        return matrix;
    }

    private double[] getInitialStateVector() {
        double[] vector = new double[numStates];

        for (int i = 0; i < numStates; i++) {
            vector[i] = Double.parseDouble(initialStateFields[i].getText());
        }

        return vector;
    }

    private void showError(String message) {
        errorMessage.setText(message);
        errorContainer.setVisible(true);
    }

    private void showMatrixResult(String title, double[][] matrix) {
        // Create result section
        VBox resultSection = createResultSection(title);

        // Create grid for matrix
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                Label cell = new Label(df.format(matrix[i][j]));
                cell.setPrefWidth(60);
                cell.setStyle("-fx-border-color: #ddd; -fx-padding: 5px; -fx-alignment: center;");
                grid.add(cell, j, i);
            }
        }

        // Add to result section
        resultSection.getChildren().add(grid);
    }

    private void showVectorResult(String title, double[] vector) {
        // Create result section
        VBox resultSection = createResultSection(title);

        // Create HBox for vector
        HBox vectorBox = new HBox(10);
        vectorBox.setPadding(new Insets(10));
        vectorBox.setAlignment(Pos.CENTER_LEFT);

        for (int i = 0; i < vector.length; i++) {
            Label stateLabel = new Label(stateLabels.get(i) + ": ");
            Label valueLabel = new Label(df.format(vector[i]));
            valueLabel.setStyle("-fx-font-weight: bold;");

            HBox cellBox = new HBox(2);
            cellBox.getChildren().addAll(stateLabel, valueLabel);

            vectorBox.getChildren().add(cellBox);
        }

        // Add to result section
        resultSection.getChildren().add(vectorBox);
    }

    private void showTextResult(String title, String text) {
        // Create result section
        VBox resultSection = createResultSection(title);

        // Create text label
        Label textLabel = new Label(text);
        textLabel.setWrapText(true);
        textLabel.setPadding(new Insets(10));

        // Add to result section
        resultSection.getChildren().add(textLabel);
    }

    private VBox createResultSection(String title) {
        // Create section
        VBox section = new VBox(10);
        section.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 10px;");

        // Add title
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        section.getChildren().add(titleLabel);

        // Add to results container
        resultContainer.getChildren().add(0, section); // Add at top

        return section;
    }
}