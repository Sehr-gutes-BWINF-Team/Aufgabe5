package com.franosch.paul.io;

import com.franosch.paul.Main;
import com.franosch.paul.model.Edge;
import com.franosch.paul.model.Graph;
import com.franosch.paul.model.Node;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@RequiredArgsConstructor
public class FileReader {

    private final boolean useTestResources;

    @SneakyThrows
    public Graph read(String name) {
        File file = new File(getCurrentPath(useTestResources) + name + ".txt");
        Scanner scanner = new Scanner(file);
        Set<Node> nodes = new HashSet<>();
        Set<Edge> edges = new HashSet<>();
        boolean skip = true;
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            if (skip) {
                skip = false;
                continue;
            }
            final String[] split = data.split(" ");
            Node nodeA = parseNode(split[0]);
            Node nodeB = parseNode(split[1]);
            insertNode(nodes, nodeA);
            insertNode(nodes, nodeB);
            insertEdge(edges, nodeA, nodeB);
        }
        scanner.close();
        return new Graph(nodes, edges);
    }

    private void insertEdge(Set<Edge> edges, Node a, Node b) {
        Edge edge = new Edge(a, b);
        if (edges.contains(edge)) {
            return;
        }
        edges.add(edge);
    }

    private void insertNode(Set<Node> nodes, Node node) {
        if (nodes.contains(node)) {
            return;
        }
        nodes.add(node);
    }

    private Node parseNode(String string) {
        final int i = Integer.parseInt(string);
        return new Node(i);
    }

    private String getCurrentPath(boolean useTestResources) {
        return useTestResources ? getCurrentPathDev() : getCurrentPathProd();
    }

    @SneakyThrows
    private String getCurrentPathProd() {
        String path = new File(Main.class.getProtectionDomain()
                .getCodeSource().getLocation().toURI()).getPath();
        String[] splits = path.split("[/\\\\]");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < splits.length - 1; i++) {
            stringBuilder.append(splits[i]).append("/");
        }
        return stringBuilder.append("resources/").toString();
    }

    private String getCurrentPathDev() {
        return new File("").getAbsolutePath()
                + "/src/test/resources/";
    }

}
