package agent;

import model.Node;
import model.TreeNode;
import model.WordGraph;
import util.Dictionary;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public class MiniMax {
    StaticEvaluator staticEvaluator;
    StateTreeGenerator stateTreeGenerator;
    State state;
    TreeNode currentNode;
    TreeNode parent;

    public MiniMax(WordGraph graph, Set<Node> visitedNodes, StaticEvaluator staticEvaluator, StateTreeGenerator stateTreeGenerator) {
        this.staticEvaluator = staticEvaluator;
        staticEvaluator.setIterations(100);

        this.stateTreeGenerator = stateTreeGenerator;
    }

    enum State {
        MAX,
        MIN
    }

    private double maximizer() {
        state = State.MAX;
        return Math.max(parent.getWinLoseRatio(), currentNode.getWinLoseRatio());
    }

    private double minimizer() {
        state = State.MIN;
        return Math.min(parent.getWinLoseRatio(), currentNode.getWinLoseRatio());
    }

    public Node findOptimalMove() {
        state = State.MAX;
        while (stateTreeGenerator.hasNext()) {
            TreeNode leaf = stateTreeGenerator.next();
            leaf.setWinLoseRatio(staticEvaluator.evaluateProbability(leaf));

            currentNode = leaf;

            while (!currentNode.isRoot() && (currentNode == leaf || !currentNode.hasUnprocessedChildNodes())) {
                parent = currentNode.getParent();
                parent.setWinLoseRatio(getNewWinLoseRatio());
                parent.incrementProcessedChildNodes();
                currentNode = parent;
            }

//            System.out.println(leaf.getName() + ": " + leaf.getWinLoseRatio());
        }

        System.out.println("Next Moves");
        for (TreeNode nextMove : currentNode.getChildren()) {
            System.out.println(nextMove.getName() + ": " + nextMove.getWinLoseRatio());
        }

        return null;
    }

    private double getNewWinLoseRatio() {
        if (parent.getWinLoseRatio() == -1) {
            parent.setWinLoseRatio(currentNode.getWinLoseRatio());
        }

        if (parent.getDepth() % 2 == 0) {
           return minimizer();
        }
        return maximizer();
    }

    public static void main(String[] args) throws FileNotFoundException {
        WordGraph graph = new WordGraph();
        graph.createGraph(Dictionary.load("src/main/java/dictionaries/common-four-letter.txt"));

        long startTime = System.nanoTime();
        Set<Node> visitedNodes = new HashSet<>();

        StateTreeGenerator stateTreeGenerator = new StateTreeGenerator(graph.getRandomNode(), visitedNodes, 6);
        RandomWalker randomWalker = new RandomWalker().setPreviouslyVisitedNodes(new HashSet<>());
        StaticEvaluator staticEvaluator = new StaticEvaluator(graph, randomWalker);

        MiniMax miniMax = new MiniMax(graph, visitedNodes, staticEvaluator, stateTreeGenerator);
        miniMax.findOptimalMove();

        long endTime = System.nanoTime();
        System.out.println((double) (endTime - startTime) / (double) 1000000);
    }
}
