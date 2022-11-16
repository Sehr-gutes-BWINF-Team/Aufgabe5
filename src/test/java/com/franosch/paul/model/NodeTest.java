package com.franosch.paul.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void id() {
        Node node = new Node(2);
        int id = node.id();

        Assertions.assertEquals(2, id);
    }
}