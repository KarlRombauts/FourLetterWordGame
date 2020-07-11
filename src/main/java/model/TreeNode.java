package model;

import java.util.*;

public class TreeNode {
    private TreeNode parent;
    private int numberOfChildNodes = 0;
    private int numberOfChildNodesProcessed = 0;
    private int depth;
    private double winLoseRatio = -1;
    private Node graphNode;
    private List<TreeNode> children = new ArrayList<>();
    private Set<Node> exploredConnections = new HashSet<>();

    public TreeNode(Node graphNode, TreeNode parent) {
        this.graphNode = graphNode;
        createParentRelationship(parent);
        this.depth = isRoot() ? 1: (parent.getDepth() + 1);
    }

    public void addExploredConnection(Node node) {
        exploredConnections.add(node);
    }

    public void clearExploredConnections() {
        exploredConnections.clear();
    }

    public Set<Node> getExploredConnections() {
        return exploredConnections;
    }

    public Node getGraphNode() {
        return graphNode;
    }

    public boolean isUnexplored(Node node) {
        return !exploredConnections.contains(node) && !pathContains(node);
    }

    public void setNumberOfChildNodes(int numberOfChildNodes) {
        this.numberOfChildNodes = numberOfChildNodes;
    }

    public void incrementProcessedChildNodes() {
        this.numberOfChildNodesProcessed++;
    }

    public boolean hasUnprocessedChildNodes() {
        return numberOfChildNodes - numberOfChildNodesProcessed > 0;
    }

    public void setWinLoseRatio(double winLoseRatio) {
        this.winLoseRatio = winLoseRatio;
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public void createChildRelationship(TreeNode child) {
        addChild(child);
        child.setParent(this);
    }

    public void createParentRelationship(TreeNode parent) {
        setParent(parent);
        if (parent != null) {
            parent.addChild(this);
        }
    }

    public String getName() {
        return graphNode.getName();
    }

    public TreeNode getParent() {
        return parent;
    }

    public List<TreeNode> getChildren() {
        return children;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode treeNode = (TreeNode) o;
        return Objects.equals(parent, treeNode.parent) &&
                graphNode.equals(treeNode.graphNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, graphNode);
    }

    public boolean pathContains(Node graphNode) {
        TreeNode current = this;
        while (current != null) {
            if (current.getGraphNode() == graphNode) {
                return true;
            }
            current = current.parent;
        }
        return false;
    }
    public boolean isRoot() {
        return parent == null;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isFullyExplored() {
        return numberOfChildNodes == exploredConnections.size();
    }

    public double getWinLoseRatio() {
        return winLoseRatio;
    }

    public int getNumberOfChildNodes() {
        return numberOfChildNodes;
    }
}
