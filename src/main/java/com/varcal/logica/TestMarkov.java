package com.varcal.logica;

public class TestMarkov {
    public static void main(String[] args) {
        // Ejemplo: matriz de transición con 1 estado absorbente
        double[][] P = {
                { 0.5, 0.5, 0.0 },
                { 0.2, 0.6, 0.2 },
                { 0.0, 0.0, 1.0 } // estado 2 es absorbente
        };

        boolean[] absorbing = MarkovChain.detectAbsorbingStates(P);

        System.out.println("Es estocástica: " + MarkovChain.isStochastic(P));
        System.out.print("Estados absorbentes: ");
        for (int i = 0; i < absorbing.length; i++) {
            if (absorbing[i])
                System.out.print(i + " ");
        }
        System.out.println();

        ResultCanonical result = MarkovChain.computeCanonicalMatrices(P, absorbing);

        System.out.println("\nQ:");
        printMatrix(result.Q);
        System.out.println("\nR:");
        printMatrix(result.R);
        System.out.println("\nN (matriz fundamental):");
        printMatrix(result.N);
        System.out.println("\nB (probabilidad de absorción):");
        printMatrix(result.B);
    }

    public static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double val : row) {
                System.out.printf("%.4f\t", val);
            }
            System.out.println();
        }
    }
}