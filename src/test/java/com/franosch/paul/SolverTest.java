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
        System.out.println(matrix);
        Matrix exp = MatrixManipulator.exp(matrix, 2);
        System.out.println("Path length 2: " + exp);
        Matrix reachableNodes = MatrixManipulator.calcReachableNodes(matrix, 2);
        System.out.println("Reachable step 2: " + reachableNodes);
    }

    @Test
    void solve() {
        Solver solver = new Solver(3, true);
        solver.solve();
    }

}
