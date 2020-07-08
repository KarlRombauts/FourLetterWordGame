package model;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String name;
    private TreeNode parent;
    private List<TreeNode> children = new ArrayList<>();

    public TreeNode(String name, TreeNode parent) {
        this.name = name;
        createParentRelationship(parent);
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
}
