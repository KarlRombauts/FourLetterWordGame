package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TreeNode {
    private String name;
    private TreeNode parent;
    private int numberOfChildren = 0;
    private double winLoseRatio;
    private List<TreeNode> children = new ArrayList<>();

    public TreeNode(String name, TreeNode parent) {
        this.name = name;
        createParentRelationship(parent);
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
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
        return name;
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
        return name.equals(treeNode.name) &&
                Objects.equals(parent, treeNode.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parent);
    }
}
