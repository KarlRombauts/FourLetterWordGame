import util.Dictionary;
import model.Node;
import model.TreeNode;
import model.WordGraph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StateTreeTest {

    private static List<String> testCase1;
    private static List<String> testCase2;

    private HashSet<Node> visitedNodes;
    private WordGraph graph;
    private Set<TreeNode> expectedLeafNodes;
    private HashSet<TreeNode> actualLeafNodes;

    @BeforeAll
    static void init() throws FileNotFoundException {
        testCase1 = Dictionary.load("src/main/java/dictionaries/test-case-1.txt");
        testCase2 = Dictionary.load("src/main/java/dictionaries/test-case-2.txt");
    }

    @BeforeEach
    void setup() {
        graph = new WordGraph();
        visitedNodes = new HashSet<>();
        expectedLeafNodes = new HashSet<>();
        actualLeafNodes = new HashSet<>();
    }

    @Test
    void findsAllLeafNodes_TestCase1() {
        TreeNode blip = new TreeNode("blip", null);
        TreeNode slip = new TreeNode("slip", blip);
        TreeNode slap = new TreeNode("slap", slip);
        TreeNode slop = new TreeNode("slop", slap);
        TreeNode slab = new TreeNode("slab", slap);
        TreeNode slop2 = new TreeNode("slop", slip);
        TreeNode slap2 = new TreeNode("slap", slop2);
        TreeNode slab2 = new TreeNode("slab", slap2);

        expectedLeafNodes.add(slop);
        expectedLeafNodes.add(slab);
        expectedLeafNodes.add(slab2);

        graph.createGraph(testCase1);
        Node startNode = graph.findNode(testCase1.get(0));
        StateTreeFactory stateTreeFactory = new StateTreeFactory(startNode, visitedNodes, 10);

        new TreeNode("slip", null);
        while (stateTreeFactory.hasNext()) {
            actualLeafNodes.add(stateTreeFactory.next());
        }

        assertEquals(expectedLeafNodes, actualLeafNodes);
    }

    @Test
    void findsAllLeafNodes_TestCase2() {
        TreeNode blip = new TreeNode("blip", null);
        TreeNode slip = new TreeNode("slip", blip);
        TreeNode slap = new TreeNode("slap", slip);
        TreeNode slop = new TreeNode("slop", slap);
        TreeNode stop = new TreeNode("stop", slop);
        TreeNode step = new TreeNode("step", stop);
        TreeNode shop = new TreeNode("shop", stop);

        TreeNode shop2 = new TreeNode("shop", slop);
        TreeNode stop2 = new TreeNode("stop", shop2);
        TreeNode step2 = new TreeNode("step", stop2);

        TreeNode slop2 = new TreeNode("slop", slip);
        TreeNode stop3 = new TreeNode("stop", slop2);
        TreeNode step3 = new TreeNode("step", stop3);
        TreeNode shop3 = new TreeNode("shop", stop3);

        TreeNode shop4 = new TreeNode("shop", slop2);
        TreeNode stop4 = new TreeNode("stop", shop4);
        TreeNode step4 = new TreeNode("step", stop4);

        TreeNode slap2 = new TreeNode("slap", slop2);

        expectedLeafNodes.add(step);
        expectedLeafNodes.add(shop);
        expectedLeafNodes.add(step2);
        expectedLeafNodes.add(step3);
        expectedLeafNodes.add(shop3);
        expectedLeafNodes.add(step4);
        expectedLeafNodes.add(slap2);

        graph.createGraph(testCase2);
        Node startNode = graph.findNode(testCase2.get(0));
        StateTreeFactory stateTreeFactory = new StateTreeFactory(startNode, visitedNodes, 10);

        new TreeNode("slip", null);
        while (stateTreeFactory.hasNext()) {
            actualLeafNodes.add(stateTreeFactory.next());
        }

        assertEquals(expectedLeafNodes, actualLeafNodes);
    }
}
