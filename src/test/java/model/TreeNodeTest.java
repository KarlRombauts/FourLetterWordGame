package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TreeNodeTest {
    @Test
    void equals_ReturnsTrue_IfNameIsTheSame() {
        assertEquals(new TreeNode("Test", null), new TreeNode("Test", null));
    }

    @Test
    void equals_ReturnsFalse_IfNameIsDifferent() {
        assertNotEquals(new TreeNode("Test1", null), new TreeNode("Test2", null));
    }

    @Test
    void equals_ReturnsTrue_IfParentIsTheSame() {
        TreeNode parent1 = new TreeNode("Parent", null);
        TreeNode parent2 = new TreeNode("Parent", null);
        assertEquals(new TreeNode("Test", parent1), new TreeNode("Test", parent2));
    }

    @Test
    void equals_ReturnsTrue_IfParentIsDifferent() {
        TreeNode parent1 = new TreeNode("Parent1", null);
        TreeNode parent2 = new TreeNode("Parent2", null);
        assertNotEquals(new TreeNode("Test", parent1), new TreeNode("Test", parent2));
    }

    @Test
    void equals_ReturnsTrue_IfGrandParentIsTheSame() {
        TreeNode grandParent1 = new TreeNode("GrandParent", null);
        TreeNode grandParent2 = new TreeNode("GrandParent", null);
        TreeNode parent1 = new TreeNode("Parent", grandParent1);
        TreeNode parent2 = new TreeNode("Parent", grandParent2);
        assertEquals(new TreeNode("Test", parent1), new TreeNode("Test", parent2));
    }

    @Test
    void equals_ReturnsTrue_IfGrandParentIsDifferent() {
        TreeNode grandParent1 = new TreeNode("GrandParent1", null);
        TreeNode grandParent2 = new TreeNode("GrandParent2", null);
        TreeNode parent1 = new TreeNode("Parent1", grandParent1);
        TreeNode parent2 = new TreeNode("Parent1", grandParent2);
        assertNotEquals(new TreeNode("Test", parent1), new TreeNode("Test", parent2));
    }
}
