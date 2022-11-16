package com.franosch.paul.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Getter
public class Graph {
    private final Set<Node> nodes;
    private final Set<Edge> edges;
    private final Map<Node, Set<Node>> neighbours;

    public Graph(Set<Node> nodes, Set<Edge> edges){
        this.nodes = nodes;
        this.edges = edges;
        this.neighbours = this.calcNeighbours(nodes, edges);
    }

    private Map<Node, Set<Node>> calcNeighbours(Set<Node> nodes, Set<Edge> edges){
        Map<Node, Set<Node>> map = new HashMap<>();
        for (Node node : nodes) {
            Set<Node> set = new HashSet<>();
            for (Edge edge : edges) {
                if(edge.from().equals(node)) {
                    set.add(edge.to());
                }
            }
            map.put(node, set);
        }
        return map;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodes +
                ", edges=" + edges +
                '}';
    }
}
