package model;

import java.util.*;

public class WordGraph {
    private final Map<String, Node> nodes = new HashMap<>();

    public void createGraph(Collection<String> dictionary) {
        createNodes(dictionary);
    }

    private void createNodes(Collection<String> dictionary) {
        for (String word : dictionary) {
            nodes.put(word, new Node(word));
        }
        createLinks();
    }

    private void createLinks() {
        Collection<Node> nodeValues = nodes.values();
        for (Node node1 : nodeValues) {
            for (Node node2 : nodeValues) {
                if (formsEdge(node1, node2)) {
                    node1.addLink(node2);
                }
            }
        }
    }

    public Collection<Node> getNodes() {
        return nodes.values();
    }

    public Node findNode(String word) {
        return nodes.get(word);
    }

    private boolean formsEdge(Node node1, Node node2) {
        char[] word1Chars = node1.getName().toCharArray();
        char[] word2Chars = node2.getName().toCharArray();

        int differenceCount = 0;
        for (int i = 0; i < word1Chars.length; i++) {
            if (word1Chars[i] == word2Chars[i]) continue;
            if (differenceCount >= 1) return false;
            differenceCount++;
        }
        return differenceCount == 1;
    }

    public Node getRandomNode() {
        List<Node> nodeValues = new ArrayList<>(nodes.values());
        return  nodeValues.get(new Random().nextInt(nodeValues.size()));
    }
}
