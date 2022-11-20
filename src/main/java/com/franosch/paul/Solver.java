package com.franosch.paul;

import com.franosch.paul.io.FileReader;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Matrix;
import com.franosch.paul.model.Node;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class Solver {

    private final int number;
    private final boolean useTestResources;

    private static boolean solutionFound = false;

    public void solve() {
        FileReader fileReader = new FileReader(useTestResources);
        final Graph graph = fileReader.read("huepfburg" + number);

        final int cap = graph.getNodes().size() * graph.getNodes().size();
        long startTime = System.currentTimeMillis();
        Result result = findTargetNode(graph, cap);
        long afterResult = System.currentTimeMillis();
        if (result == null) {
            System.out.println("Der Parkour ist unlösbar.");
            System.out.println("Dauer Zielknoten finden via Matrizen: " + (afterResult - startTime));
            return;
        }

        Node a = new Node(1);
        Node b = new Node(2);

        Set<List<Node>> pathFromA = findPathViaDFS(graph, a, result.node, result.steps);
        solutionFound = false;
        Set<List<Node>> pathFromB = findPathViaDFS(graph, b, result.node, result.steps);
        for (final List<Node> nodes : pathFromA) {
            System.out.println("Pfad von 1 nach " + result.node.id() + ": " + nodes);
        }
        for (final List<Node> nodes : pathFromB) {
            System.out.println("Pfad von 2 nach " + result.node.id() + ": " + nodes);
        }
        System.out.println("Dauer Zielknoten finden via Matrizen: " + (afterResult - startTime));
    }

    private Result findTargetNode(Graph graph, int cap) {
        Matrix adjacencyMatrix = graph.toAdjacencyMatrix();

        List<Matrix> previous = new ArrayList<>();
        Matrix current = adjacencyMatrix;
        for (int i = 1; i < cap; i++) {
            int[] reachableFirst = current.values()[0];
            int[] reachableSecond = current.values()[1];

            for (int j = 0; j < reachableFirst.length; j++) {
                if (reachableFirst[j] != 0 && reachableFirst[j] == reachableSecond[j]) {
                    System.out.println("SOLUTION IS NODE " + (j + 1) + " IN " + i + " STEPS");
                    return new Result(i, new Node(j + 1));
                }
            }
            int alreadyInSet = isNew(previous, current);
            if (alreadyInSet != -1) {
                System.out.println("Es ist nicht möglich den Parkour erfolgreich zu absolvieren, Matrix in Schritt "
                        + (previous.size() + 1) + " ist identisch zu der vorherigen Matrix in Schritt " + (alreadyInSet + 1) + ".");
                return null;
            }
            previous.add(current);

            current = MatrixManipulator.multiply(current, adjacencyMatrix);
        }
        System.out.println("Es konnte kein passender Knoten in " + cap + " Schritten ermittelt werden!");
        return null;
    }

    private int isNew(List<Matrix> matrices, Matrix matrix) {
        for (int i = 0; i < matrices.size(); i++) {
            final Matrix current = matrices.get(i);
            if (current.equals(matrix)) {
                System.out.println(matrix + " is the same as matrix with index " + i + ": " + current);
                return i;
            }
        }
        return -1;
    }

    private Set<List<Node>> findPathViaDFS(Graph graph, Node start, Node target, int steps) {
        Set<List<Node>> paths = new HashSet<>();
        findPathViaDFS(graph, start, target, 0, new ArrayList<>(), paths, steps);
        return paths;
    }

    private void findPathViaDFS(Graph graph, Node current, Node target, int depth,
                                List<Node> path, Set<List<Node>> paths, int steps) {
        if (solutionFound) return;
        path.add(current);
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
            findPathViaDFS(graph, node, target, depthNew, new ArrayList<>(path), paths, steps);
        }
    }

    @RequiredArgsConstructor
    @Getter
    class Result {
        private final int steps;
        private final Node node;
    }


}
