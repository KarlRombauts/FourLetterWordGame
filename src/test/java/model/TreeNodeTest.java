package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TreeNodeTest {
    @Test
    void equals_ReturnsTrue_IfNameIsTheSame() {
        assertEquals(
                new TreeNode(new Node("Test"), null),
                new TreeNode(new Node("Test"), null));
    }

    @Test
    void equals_ReturnsFalse_IfNameIsDifferent() {
        assertNotEquals(
                new TreeNode(new Node("Test1"), null),
                new TreeNode(new Node("Test2"), null));
    }

    @Test
    void equals_ReturnsTrue_IfParentIsTheSame() {
        TreeNode parent1 = new TreeNode(new Node("Parent"), null);
        TreeNode parent2 = new TreeNode(new Node("Parent"), null);
        assertEquals(new TreeNode(new Node("Test"), parent1), new TreeNode(new Node("Test"), parent2));
    }

    @Test
    void equals_ReturnsTrue_IfParentIsDifferent() {
        TreeNode parent1 = new TreeNode(new Node("Parent1"), null);
        TreeNode parent2 = new TreeNode(new Node("Parent2"), null);
        assertNotEquals(new TreeNode(new Node("Test"), parent1), new TreeNode(new Node("Test"), parent2));
    }

    @Test
    void equals_ReturnsTrue_IfGrandParentIsTheSame() {
        TreeNode grandParent1 = new TreeNode(new Node("GrandParent"), null);
        TreeNode grandParent2 = new TreeNode(new Node("GrandParent"), null);
        TreeNode parent1 = new TreeNode(new Node("Parent"), grandParent1);
        TreeNode parent2 = new TreeNode(new Node("Parent"), grandParent2);
        assertEquals(new TreeNode(new Node("Test"), parent1), new TreeNode(new Node("Test"), parent2));
    }

    @Test
    void equals_ReturnsTrue_IfGrandParentIsDifferent() {
        TreeNode grandParent1 = new TreeNode(new Node("GrandParent1"), null);
        TreeNode grandParent2 = new TreeNode(new Node("GrandParent2"), null);
        TreeNode parent1 = new TreeNode(new Node("Parent"), grandParent1);
        TreeNode parent2 = new TreeNode(new Node("Parent"), grandParent2);
        assertNotEquals(new TreeNode(new Node("Test"), parent1), new TreeNode(new Node("Test"), parent2));
    }
}
