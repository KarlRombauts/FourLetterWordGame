import model.Node;
import model.TreeNode;
import model.WordGraph;

import java.io.FileNotFoundException;
import java.util.*;


public class StateTreeFactory {
    private Node startNode;
    private int depth;
    private TreeNode currentTreeNode;
    private Node currentGraphNode;
    private Set<Node> previouslyVisitedNodes = new HashSet<>();
    private Map<Node, Set<Node>> exploredConnectionsMap = new HashMap<>();
    private Stack<Node> currentPath = new Stack<>();
    private Node nextGraphNode;
    private SearchState searchState;
    private List<TreeNode> foundLeafNodes = new ArrayList<>();

    enum SearchState {
        EXPLORING,
        BACKTRACKING,
    }

    public StateTreeFactory(Node startNode, Set<Node> previouslyVisitedNodes, int depth) {
        this.startNode = startNode;
        this.depth = depth;
        this.previouslyVisitedNodes = previouslyVisitedNodes;

        searchState = SearchState.EXPLORING;
        visitNode(startNode);
    }

    private void visitNode(Node node) {
        exploredConnectionsMap.computeIfAbsent(node, k -> new HashSet<>());
        if (currentPath.size() > 0) {
            exploredConnectionsMap.get(currentGraphNode).add(node);
        }
        currentGraphNode = node;
        currentPath.push(node);

        currentTreeNode = new TreeNode(node.getName(), currentTreeNode);
        nextGraphNode = nextUnexploredConnection(currentGraphNode);
    }

    private void backTrack() {
        currentTreeNode = currentTreeNode.getParent();
        exploredConnectionsMap.get(currentPath.pop()).clear();
        currentGraphNode = currentPath.lastElement();
        nextGraphNode = nextUnexploredConnection(currentGraphNode);
    }

    private Node nextUnexploredConnection(Node node) {
        Set<Node> exploredConnections = exploredConnectionsMap.get(node);
        for (Node link : node.getLinks()) {
            if (!exploredConnections.contains(link) && !currentPath.contains(link) && !previouslyVisitedNodes.contains(link))
                return link;
        }
        return null;
    }

    public boolean hasNext() {
        while(shouldBackTrack() && !isSearchComplete()) {
            backTrack();
        }
        return !isSearchComplete();
    }

    public TreeNode next() {
        if (isSearchComplete()) {
            throw new NoSuchElementException("All of the leaf nodes have been discovered");
        }

        while (true) {
            nextGraphNode = nextUnexploredConnection(currentGraphNode);

            if (shouldBackTrack()) {
                searchState = SearchState.BACKTRACKING;
                return currentTreeNode;
            } else {
                searchState = SearchState.EXPLORING;
                visitNode(nextGraphNode);
            }
        }
    }

    public List<TreeNode> createStateTree() {
        int count = 0;
        while (true) {
            if (isSearchComplete()) {
                break;
            }
            if (shouldBackTrack()) {
                if (searchState == SearchState.EXPLORING) {
                    foundLeafNodes.add(currentTreeNode);
                }
                searchState = SearchState.BACKTRACKING;
                backTrack();
            } else {
                searchState = SearchState.EXPLORING;
                visitNode(nextGraphNode);
            }
            count++;
        }

        System.out.printf("Depth: %d, traversals: %d\n", depth, count);
        return foundLeafNodes;
    }

    private boolean isSearchComplete() {
        return currentGraphNode == startNode && (currentPath.size() == depth || nextGraphNode == null);
    }

    private boolean shouldBackTrack() {
        return nextGraphNode == null || currentPath.size() == depth;
    }

    public static void main(String[] args) throws FileNotFoundException {
        WordGraph graph = new WordGraph();
        graph.createGraph(Dictionary.load("src/main/java/dictionaries/test-case-2.txt"));

        long startTime = System.nanoTime();
        Set<Node> visitedNodes = new HashSet<>();

        StateTreeFactory stateTreeFactory = new StateTreeFactory(graph.findNode("blip"), visitedNodes, 10);
        double highestProbability = 0;
        String bestWord = "";

        while (stateTreeFactory.hasNext()) {
            TreeNode leaf = stateTreeFactory.next();
            System.out.println(leaf.getName());
            StaticEvaluator staticEvaluator = new StaticEvaluator(graph).setIterations(100);
            double probability = staticEvaluator.evaluateProbability(leaf);

            System.out.println(leaf.getName() + ": " + probability);

            if (probability > highestProbability) {
                highestProbability = probability;
                bestWord = leaf.getName();
            }
        }

        System.out.println("BEST: " + bestWord + ", Probability: " + highestProbability);
        long endTime = System.nanoTime();

        System.out.println((double) (endTime - startTime) / (double) 1000000);
    }
}

