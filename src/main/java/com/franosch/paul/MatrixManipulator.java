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
                    result[i][j] += valuesA[i][k] * valuesB[k][j]; // abbrechen nach 1 möglich → laufzeitverbesserung
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

    // https://stackoverflow.com/a/1375033
    public static Matrix getIdentity(int size) {
        int[][] values = new int[size][size];
        for (int i = 0; i < size; i++) {
            values[i][i] = 1;
        }
        return new Matrix(values);
    }

    public static Matrix exp(Matrix matrix, int exponent) {
        Matrix current = matrix;
        for (int i = 0; i < exponent - 1; i++) {
            current = multiply(current, matrix);
        }
        return current;
    }

    public static Matrix expButMakeItStupid(Matrix matrix, int exponent) {
        Matrix current = matrix;
        for (int i = 0; i < exponent - 1; i++) {
            current = multiply(current, matrix);
            for (int j = 0; j < current.values().length; j++) {
                for (int k = 0; k < current.values()[j].length; k++) {
                    if (current.values()[j][k] != 0) {
                        current.values()[j][k] = 1;
                    }
                }
            }
        }
        return current;
    }


    public static Matrix calcReachableNodes(Matrix matrix, int steps) {
        Matrix current = getIdentity(matrix.getLength());
        for (int i = 0; i < steps; i++) {
            Matrix exp = exp(matrix, i + 1);
            current = add(current, exp);
        }

        for (final int[] value : current.values()) {
            for (int i = 0; i < value.length; i++) {
                if (value[i] != 0) {
                    value[i] = 1;
                }
            }
        }
        return current;
    }

}
