package com.franosch.paul;

import com.franosch.paul.io.FileReader;
import com.franosch.paul.model.Edge;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Matrix;
import com.franosch.paul.model.Node;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.util.*;

@RequiredArgsConstructor
public class Solver {

    private final int number;
    private final boolean useTestResources;

    private static boolean solutionFound = false;

    public void solve() {
        FileReader fileReader = new FileReader(useTestResources);
        final Graph graph = fileReader.read("huepfburg" + number);

        final int cap = 10000;

        Result result = findTargetNode(graph, cap);
        if (result == null) throw new NullPointerException("THERE WAS NO RESULT FOUND IN " + cap + " STEPS!");

        Node a = new Node(1);
        Node b = new Node(2);

        Set<List<Node>> pathFromA = showNeighbours(graph, a, result.node, result.steps);
        solutionFound = false;
        Set<List<Node>> pathFromB = showNeighbours(graph, b, result.node, result.steps);

        for (final List<Node> nodes : pathFromA) {
            System.out.println("Pfad von 1 nach " + result.node.id() + ": " + nodes);
        }
        for (final List<Node> nodes : pathFromB) {
            System.out.println("Pfad von 2 nach " + result.node.id() + ": " + nodes);
        }

    }

    private Result findTargetNode(Graph graph, int cap) {
        Matrix matrix = graph.toAdjacencyMatrix();

        List<Matrix> previous = new ArrayList<>();

        for (int i = 1; i < cap; i++) {
            Matrix exp = MatrixManipulator.expButMakeItStupid(matrix, i);
            int[] reachableFirst = exp.values()[0];
            int[] reachableSecond = exp.values()[1];

            for (int j = 0; j < reachableFirst.length; j++) {
                if (reachableFirst[j] != 0 && reachableFirst[j] == reachableSecond[j]) {
                    // System.out.println("MATRIX " + exp);
                    System.out.println("SOLUTION IS NODE " + (j + 1) + " IN " + i + " STEPS");
                    return new Result(i, new Node(j + 1));
                }
            }

            if (isNew(previous, exp)) {
                System.out.println("Es ist nicht möglich den Parkour erfolgreich zu absolvieren, Matrix in Schritt "
                        + (previous.size() + 1) + " ist identisch zu einer vorherigen Matrix.");
                System.exit(0);
            }
            previous.add(exp);

        }
        System.out.println("No solution found in range 0 to " + cap);
        return null;
    }

    private boolean isNew(List<Matrix> matrices, Matrix matrix) {
        for (int i = 0; i < matrices.size(); i++) {
            final Matrix current = matrices.get(i);
            if (current.equals(matrix)) {
                System.out.println(matrix + " is the same as matrix with index " + i + ": " + current);
                return true;
            }
        }
        return false;
    }

    private Set<List<Node>> showNeighbours(Graph graph, Node start, Node target, int steps) {
        Set<List<Node>> paths = new HashSet<>();
        showNeighbours(graph, start, target, 0, new ArrayList<>(), paths, steps);
        return paths;
    }

    private void showNeighbours(Graph graph, Node current, Node target, int depth,
                                List<Node> path, Set<List<Node>> paths, int steps) {
        if (solutionFound) return;
        path.add(current);
        // System.out.println("current " + current + " target " + target + " depth " + depth + " steps " + steps);
        if (depth == steps) {
            if (current.equals(target)) {
                paths.add(path);
                solutionFound = true;
                return;
            }
            return;
        }
        final Set<Node> nodes = graph.getNeighbours().get(current);
        for (Node node : nodes) {
            int depthNew = depth + 1;
            showNeighbours(graph, node, target, depthNew, new ArrayList<>(path), paths, steps);
        }
    }

    @RequiredArgsConstructor
    @Getter
    class Result {
        private final int steps;
        private final Node node;
    }


}
