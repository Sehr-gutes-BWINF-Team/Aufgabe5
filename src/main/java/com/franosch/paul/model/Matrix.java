package com.franosch.paul.model;

import java.util.Arrays;

public record Matrix(int[][] values) {

    public int getLength() {
        return values.length;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Matrix matrix = (Matrix) o;

        return Arrays.deepEquals(values, matrix.values);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(values);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] value : values) {
            stringBuilder.append(Arrays.toString(value)).append("\n");
        }
        return "Matrix{\n" + stringBuilder + '}';
    }
}
