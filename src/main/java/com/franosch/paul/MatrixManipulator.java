package com.franosch.paul;

import com.franosch.paul.model.Matrix;

public class MatrixManipulator {

    // https://stackoverflow.com/a/17624210
    public static Matrix multiply(Matrix a, Matrix b) {

        final int[][] valuesA = a.values();
        final int[][] valuesB = b.values();

        int aRows = valuesA.length;
        int aColumns = valuesA[0].length;
        int bRows = valuesA.length;
        int bColumns = valuesA[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        int[][] result = new int[aRows][bColumns];

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    int sum = result[i][j];
                    if (sum == 0) {
                        result[i][j] = valuesA[i][k] * valuesB[k][j]; // abbrechen nach 1 möglich → laufzeitverbesserung
                    }
                }
            }
        }

        return new Matrix(result);
    }

    public static Matrix add(Matrix a, Matrix b) {
        // add matrices
        final int[][] valuesA = a.values();
        final int[][] valuesB = b.values();

        int[][] values = new int[valuesA.length][valuesA[0].length];
        for (int i = 0; i < valuesA.length; i++) {
            for (int j = 0; j < valuesA[0].length; j++) {
                values[i][j] = valuesA[i][j] + valuesB[i][j];
            }
        }
        return new Matrix(values);
    }

}
