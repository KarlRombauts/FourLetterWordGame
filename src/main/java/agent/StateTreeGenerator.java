package agent;

import util.Dictionary;
import model.Node;
import model.TreeNode;
import model.WordGraph;

import java.io.FileNotFoundException;
import java.util.*;


public class StateTreeGenerator {
    private int depth;
    private TreeNode currentTreeNode;
    private Set<Node> previouslyVisitedNodes;

    public StateTreeGenerator(Node startNode, Set<Node> previouslyVisitedNodes, int depth) {
        this.depth = depth;
        this.previouslyVisitedNodes = previouslyVisitedNodes;
        visitNode(startNode);
    }

    private void visitNode(Node node) {
        if (currentTreeNode != null) {
            currentTreeNode.addExploredConnection(node);
        }

        currentTreeNode = new TreeNode(node, currentTreeNode);
        currentTreeNode.setNumberOfChildNodes(getNumberOfConnectionsToExplore(node));
    }

    private void backTrack() {
        currentTreeNode.clearExploredConnections();
        currentTreeNode = currentTreeNode.getParent();
    }

    private int getNumberOfConnectionsToExplore(Node node) {
        return (int) node.getLinks().stream().filter(this::isUnvisited).count();
    }

    private Node nextUnexploredConnection(TreeNode currentTreeNode) {
        return currentTreeNode.getGraphNode().getLinks().stream()
                .filter(this::isUnvisited).findFirst()
                .orElse(null);
    }

    public boolean isUnvisited(Node node) {
        return currentTreeNode.isUnexplored(node) && !previouslyVisitedNodes.contains(node);
    }

    public boolean hasNext() {
        while (shouldBackTrack() && !isSearchComplete()) {
            backTrack();
        }
        return !isSearchComplete();
    }

    public TreeNode next() {
        if (isSearchComplete()) {
            throw new NoSuchElementException("All of the leaf nodes have been discovered");
        }

        while (true) {
            if (shouldBackTrack()) {
                return currentTreeNode;
            } else {
                exploreNextNode();
            }
        }
    }

    private void exploreNextNode() {
        visitNode(nextUnexploredConnection(currentTreeNode));
    }

    private boolean isSearchComplete() {
        return currentTreeNode.isRoot() && (currentTreeNode.getDepth() == depth || currentTreeNode.isFullyExplored());
    }

    private boolean shouldBackTrack() {
        return currentTreeNode.isFullyExplored() || currentTreeNode.getDepth() == depth;
    }

    public static void main(String[] args) throws FileNotFoundException {
        WordGraph graph = new WordGraph();
        graph.createGraph(Dictionary.load("src/main/java/dictionaries/test-case-2.txt"));

        long startTime = System.nanoTime();
        Set<Node> visitedNodes = new HashSet<>();

        StateTreeGenerator stateTreeGenerator = new StateTreeGenerator(graph.findNode("blip"), visitedNodes, 10);
        double highestProbability = -1;
        TreeNode bestNode = null;

        while (stateTreeGenerator.hasNext()) {
            TreeNode leaf = stateTreeGenerator.next();
            System.out.println(leaf.getName());

            RandomWalker randomWalker = new RandomWalker().setPreviouslyVisitedNodes(visitedNodes);
            StaticEvaluator staticEvaluator = new StaticEvaluator(graph, randomWalker).setIterations(100);
            double probability = staticEvaluator.evaluateProbability(leaf);

            System.out.println(leaf.getName() + ": " + probability);

            if (probability > highestProbability) {
                highestProbability = probability;
                bestNode = leaf;
            }
        }

        if (bestNode != null) {
            System.out.println("BEST: " + bestNode.getName() + ", Probability: " + highestProbability);
        } else {
            System.out.println("No pathways found");
        }
        long endTime = System.nanoTime();

        System.out.println((double) (endTime - startTime) / (double) 1000000);
    }
}

