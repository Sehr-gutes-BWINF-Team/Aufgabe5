package com.franosch.paul;

import com.franosch.paul.io.FileReader;
import com.franosch.paul.model.Edge;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;

import java.util.*;

public class Solver {

    public void solve(){
        FileReader fileReader = new FileReader(true);
        final Graph graph = fileReader.read("huepfburg0");
        for (Map.Entry<Node, Set<Node>> nodeSetEntry : graph.getNeighbours().entrySet()) {
            System.out.println(nodeSetEntry.getKey() + "'s neighbours: " + nodeSetEntry.getValue());
       }

        Node a = new Node(1);
        Node b = new Node(2);
        
        for (int i = 0; i < 200; i++) {
            Set<List<Node>> pathsFromA = showNeighbours(graph, a, i);
            Set<List<Node>> pathsFromB = showNeighbours(graph, b, i);
            for (List<Node> pathA : pathsFromA) {
                for (List<Node> pathB : pathsFromB) {
                    final Node nodeA = pathA.get(pathA.size() - 1);
                    final Node nodeB = pathB.get(pathB.size() - 1);
                    if(nodeA.equals(nodeB)){
                        System.out.println(pathA);
                        System.out.println(pathB);
                        System.out.println(pathA.size());
                        return;
                    }
                }
            }
        }

    }

    private Set<List<Node>> showNeighbours(Graph graph, Node current, int max){
        Set<List<Node>> paths = new HashSet<>();
        showNeighbours(graph, current, 0, new ArrayList<>(), paths, max);
        return paths;
    }

    private void showNeighbours(Graph graph, Node current, int depth, List<Node> path, Set<List<Node>> paths, int max){
        path.add(current);
        if(depth == max) {
            paths.add(path);
            return;
        }
        final Set<Node> nodes = graph.getNeighbours().get(current);
        for (Node node : nodes) {
            int depthNew = depth + 1;
            showNeighbours(graph, node, depthNew, new ArrayList<>(path), paths, max);
        }
    }



}
