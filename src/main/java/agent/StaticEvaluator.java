package agent;

import model.Node;
import model.TreeNode;
import model.WordGraph;

import java.util.HashSet;
import java.util.Set;

class StaticEvaluator {
    private TreeNode leaf;
    private WordGraph graph;
    private RandomWalker randomWalker;
    private int iterations = 100;

    public StaticEvaluator(WordGraph graph, RandomWalker randomWalker) {
        this.graph = graph;
        this.randomWalker = randomWalker;
    }

    public StaticEvaluator setIterations(int iterations) {
        this.iterations = iterations;
        return this;
    }

    public double evaluateProbability(TreeNode leaf) {
        randomWalker.setPreviouslyVisitedNodes(getVisitedNodes(leaf));
        Node graphNode = graph.findNode(leaf.getName());
        double wins = 0;

        for (int i = 0; i < iterations; i++) {
            wins += evaluateWinLose(randomWalker.walk(graphNode));
        }

        return wins / (double) iterations;
    }

    private double evaluateWinLose(int length) {
        if (length == -1) return 0.5;
        return (length % 2) == 1 ? 1 : 0;
    }

    private Set<Node> getVisitedNodes(TreeNode leaf) {
        Set<Node> visitedNodes = new HashSet<>();
        TreeNode currentNode = leaf;

        while (currentNode.getParent() != null) {
            visitedNodes.add(graph.findNode(currentNode.getName()));
            currentNode = currentNode.getParent();
        }

        return visitedNodes;
    }
}
