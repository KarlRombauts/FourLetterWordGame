import model.Node;
import model.TreeNode;
import model.WordGraph;
import model.Words;

import java.util.*;

enum SearchState {
    EXPLORING,
    BACKTRACKING,
}

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



    public StateTreeFactory(Node startNode, Set<Node> previouslyVisitedNodes, int depth) {
        this.startNode = startNode;
        this.depth = depth;
        this.previouslyVisitedNodes = previouslyVisitedNodes;

        searchState = SearchState.EXPLORING;
        visitNode(startNode);
    }

    private void visitNode(Node node) {
        exploredConnectionsMap.computeIfAbsent(node, k -> new HashSet<>());
        if(currentPath.size() > 0) {
            exploredConnectionsMap.get(currentGraphNode).add(node);
        }
        currentGraphNode = node;
        currentPath.push(node);

        currentTreeNode = new TreeNode(node.getName(), currentTreeNode);
    }

    private void backTrack() {
        currentTreeNode = currentTreeNode.getParent();
        exploredConnectionsMap.get(currentPath.pop()).clear();
        currentGraphNode = currentPath.lastElement();
    }

    private Node nextUnexploredConnection(Node node) {
        Set<Node> exploredConnections = exploredConnectionsMap.get(node);
        for (Node link : node.getLinks()) {
            if (!exploredConnections.contains(link) && !currentPath.contains(link) && !previouslyVisitedNodes.contains(link))
                return link;
        }
        return null;
    }

    public List<TreeNode> createStateTree() {
        int count = 0;
        nextGraphNode = nextUnexploredConnection(currentGraphNode);
        while (true) {
            nextGraphNode = nextUnexploredConnection(currentGraphNode);

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

    public static void main(String[] args) {
        // Simple test case
        Node node1 = new Node("1");
        Node node2 = new Node("2");
        Node node3 = new Node("3");
        Node node4 = new Node("4");
        Node node5 = new Node("5");

        node1.createTwoWayLink(node2);
        node2.createTwoWayLink(node3);
        node3.createTwoWayLink(node4);
        node4.createTwoWayLink(node5);

        node2.createTwoWayLink(node4);

//        TreeNode root = new StateTreeFactory(node1, 5).createStateTree();

        // Test case with real words
        WordGraph graph = new WordGraph();
        graph.createGraph(Words.fourLetterWords);
        Node randomNode = graph.getRandomNode();

        long startTime = System.nanoTime();
        List<TreeNode> leafs = new StateTreeFactory(randomNode, new HashSet<Node>(), 5).createStateTree();
        System.out.println(leafs);
        long endTime = System.nanoTime();

        System.out.println((double) (endTime - startTime) / (double) 1000000);
    }
}
