package agent;

import model.Node;

import java.util.*;

public class RandomWalker {
    private Node currentNode;
    private Set<Node> previouslyVisitedNodes = new HashSet<>();
    private Set<Node> visitedNodes = new HashSet<>();

    public RandomWalker setPreviouslyVisitedNodes(Set<Node> nodes) {
       previouslyVisitedNodes = nodes;
       return this;
    }

    public double calculateWinLoseRatio(Node startingNode, int iterations) {
        int wins = 0;
        int losses = 0;

        for (int i = 0; i < iterations; i++) {
            int length = walk(startingNode);
            if ((length % 2 == 1)) {
                wins++;
            } else {
                losses++;
            }
        }

        return (double) wins / (double) (wins + losses);
    }

    public int walk(Node startingNode) {
        visitedNodes.clear();
        currentNode = startingNode;
        int walkLength = 0;
        boolean walking = true;

        while (walking) {
            visitedNodes.add(currentNode);
            List<Node> unvisitedNodes = getUnvisitedConnectedNodes();

            if (unvisitedNodes.size() == 0) {
                walking = false;
            } else {
                currentNode = getRandomNode(unvisitedNodes);
                walkLength++;
            }
        }

        return walkLength;
    }

    private Node getRandomNode(List<Node> nodes) {
        return nodes.get(new Random().nextInt(nodes.size()));
    }

    public List<Node> getUnvisitedConnectedNodes() {
        List<Node> unvisitedNodes = new ArrayList<>();

        for (Node node : currentNode.getLinks()) {
            if (!visitedNodes.contains(node) && !previouslyVisitedNodes.contains(node)) {
                unvisitedNodes.add(node);
            }
        }

        return unvisitedNodes;
    }
}
