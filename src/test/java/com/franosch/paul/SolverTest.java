package com.franosch.paul;

import com.franosch.paul.io.FileReader;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SolverTest {
    private static Graph graph;

    @BeforeAll
    static void setUp() {
        FileReader fileReader = new FileReader(true);
        graph = fileReader.read("huepfburg9");
    }

    @Test
    void adjacentMatrix() {
        Assertions.assertNotNull(graph);
        Matrix matrix = graph.toAdjacencyMatrix();
        Assertions.assertNotNull(matrix);
    }

    @Test
    void solve0() {
        Solver solver = new Solver(0, true);
        Assertions.assertDoesNotThrow(solver::solve);
    }

    @Test
    void solve1() {
        Solver solver = new Solver(1, true);
        Assertions.assertDoesNotThrow(solver::solve);
    }

    @Test
    void solve2() {
        Solver solver = new Solver(2, true);
        Assertions.assertDoesNotThrow(solver::solve);
    }

    @Test
    void solve3() {
        Solver solver = new Solver(3, true);
        Assertions.assertDoesNotThrow(solver::solve);
    }

    @Test
    void solve4() {
        Solver solver = new Solver(4, true);
        Assertions.assertDoesNotThrow(solver::solve);
    }


}
