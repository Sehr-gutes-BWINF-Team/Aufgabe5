package com.franosch.paul.model;


import java.util.Objects;

public record Edge(Node from, Node to) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (! (o instanceof Edge edge)) return false;
        return (from.equals(edge.from) && to.equals(edge.to));
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
