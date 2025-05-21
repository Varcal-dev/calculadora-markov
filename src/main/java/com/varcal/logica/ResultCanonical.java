package com.varcal.logica;

// Crea esta clase para devolver los resultados:

public class ResultCanonical {
    public double[][] Q, R, N, B;
    public int[] absorbingIndices, nonAbsorbingIndices;

    public ResultCanonical(double[][] Q, double[][] R, double[][] N, double[][] B,
            int[] absorbingIndices, int[] nonAbsorbingIndices) {
        this.Q = Q;
        this.R = R;
        this.N = N;
        this.B = B;
        this.absorbingIndices = absorbingIndices;
        this.nonAbsorbingIndices = nonAbsorbingIndices;
    }
}
