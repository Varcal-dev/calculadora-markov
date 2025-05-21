package com.varcal.logica;

public class MarkovChain {

    /**
     * Verifica si una matriz es estocástica (todas las filas suman 1).
     */
    public static boolean isStochastic(double[][] matrix) {
        for (double[] row : matrix) {
            double sum = 0.0;
            for (double val : row) {
                if (val < 0 || val > 1)
                    return false;
                sum += val;
            }
            if (Math.abs(sum - 1.0) > 1e-6)
                return false;
        }
        return true;
    }

    /**
     * Multiplica dos matrices.
     */
    public static double[][] multiply(double[][] A, double[][] B) {
        int rows = A.length;
        int cols = B[0].length;
        int inner = B.length;
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                for (int k = 0; k < inner; k++)
                    result[i][j] += A[i][k] * B[k][j];
        return result;
    }

    /**
     * Calcula P^n (n-ésima potencia de la matriz de transición).
     */
    public static double[][] power(double[][] P, int n) {
        int size = P.length;
        double[][] result = identityMatrix(size);
        double[][] temp = P;
        for (int i = 0; i < n; i++) {
            result = multiply(result, temp);
        }
        return result;
    }

    /**
     * Crea una matriz identidad de tamaño n.
     */
    public static double[][] identityMatrix(int n) {
        double[][] identity = new double[n][n];
        for (int i = 0; i < n; i++)
            identity[i][i] = 1.0;
        return identity;
    }

    /**
     * Multiplica un vector fila π₀ por una matriz Pⁿ.
     */
    public static double[] multiplyVectorMatrix(double[] vector, double[][] matrix) {
        int cols = matrix[0].length;
        double[] result = new double[cols];
        for (int j = 0; j < cols; j++)
            for (int i = 0; i < vector.length; i++)
                result[j] += vector[i] * matrix[i][j];
        return result;
    }

    /**
     * Detecta estados absorbentes (probabilidad 1 de quedarse).
     */
    public static boolean[] detectAbsorbingStates(double[][] matrix) {
        int n = matrix.length;
        boolean[] absorbing = new boolean[n];
        for (int i = 0; i < n; i++) {
            boolean isAbsorbing = matrix[i][i] == 1.0;
            for (int j = 0; j < n && isAbsorbing; j++) {
                if (i != j && matrix[i][j] != 0.0) {
                    isAbsorbing = false;
                }
            }
            absorbing[i] = isAbsorbing;
        }
        return absorbing;
    }

    // Método para obtener los índices de estados absorbentes y no absorbentes

    public static int[] getAbsorbingIndices(boolean[] absorbingStates) {
        return java.util.stream.IntStream.range(0, absorbingStates.length)
                .filter(i -> absorbingStates[i])
                .toArray();
    }

    public static int[] getNonAbsorbingIndices(boolean[] absorbingStates) {
        return java.util.stream.IntStream.range(0, absorbingStates.length)
                .filter(i -> !absorbingStates[i])
                .toArray();
    }

    /**
     * 
     * Reordena la matriz P en forma canónica: absorbentes al final.
     */
    public static double[][] reorderCanonical(double[][] matrix, boolean[] absorbingStates) {
        int[] nonAbsIdx = getNonAbsorbingIndices(absorbingStates);
        int[] absIdx = getAbsorbingIndices(absorbingStates);

        int n = matrix.length;
        double[][] reordered = new double[n][n];

        int[] allIndices = java.util.stream.IntStream.concat(
                java.util.Arrays.stream(nonAbsIdx),
                java.util.Arrays.stream(absIdx)).toArray();

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                reordered[i][j] = matrix[allIndices[i]][allIndices[j]];

        return reordered;
    }

    // Obtener submatrices Q y R

    public static double[][] extractSubMatrix(double[][] matrix, int[] rows, int[] cols) {
        double[][] sub = new double[rows.length][cols.length];
        for (int i = 0; i < rows.length; i++)
            for (int j = 0; j < cols.length; j++)
                sub[i][j] = matrix[rows[i]][cols[j]];
        return sub;
    }

    // Calcular inversa de (I - Q)

    public static double[][] invertMatrix(double[][] matrix) {
        //int n = matrix.length;
        org.apache.commons.math3.linear.RealMatrix m = new org.apache.commons.math3.linear.Array2DRowRealMatrix(matrix);
        org.apache.commons.math3.linear.RealMatrix inverse = org.apache.commons.math3.linear.MatrixUtils.inverse(m);
        return inverse.getData();
    }

    /**
     * 
     * Calcula la matriz fundamental N y la de absorción B.
     */
    public static ResultCanonical computeCanonicalMatrices(double[][] P, boolean[] absorbingStates) {
        int[] nonAbsIdx = getNonAbsorbingIndices(absorbingStates);
        int[] absIdx = getAbsorbingIndices(absorbingStates);

        double[][] canonical = reorderCanonical(P, absorbingStates);
        double[][] Q = extractSubMatrix(canonical, range(0, nonAbsIdx.length), range(0, nonAbsIdx.length));
        double[][] R = extractSubMatrix(canonical, range(0, nonAbsIdx.length), range(nonAbsIdx.length, P.length));

        double[][] I = identityMatrix(Q.length);
        double[][] IQ = subtract(I, Q);
        double[][] N = invertMatrix(IQ);
        double[][] B = multiply(N, R);

        return new ResultCanonical(Q, R, N, B, absIdx, nonAbsIdx);

    }

    public static double[][] subtract(double[][] A, double[][] B) {
        int n = A.length;
        int m = A[0].length;
        double[][] result = new double[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                result[i][j] = A[i][j] - B[i][j];
        return result;
    }

    private static int[] range(int start, int end) {
        return java.util.stream.IntStream.range(start, end).toArray();
    }
}