package model;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final String name;
    private List<Node> links = new ArrayList<>();

    public Node(String name) {
        this.name = name;
    }

    public void createTwoWayLink(Node node) {
        addLink(node);
        node.addLink(this);
    }

    public void addLink(Node node) {
        links.add(node);
    }

    public String getName() {
       return name;
    }

    public List<Node> getLinks() {
        return links;
    }

    public boolean hasLinkWith(Node node) {
        return links.contains(node);
    }

    public int getNumberOfConnections() {
        return links.size();
    }
}
