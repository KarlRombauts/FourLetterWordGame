package agent;

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
        graph.createGraph(testCase1);
        TreeNode blip = new TreeNode(graph.findNode("blip"), null);
        TreeNode slip = new TreeNode(graph.findNode("slip"), blip);
        TreeNode slap = new TreeNode(graph.findNode("slap"), slip);
        TreeNode slop = new TreeNode(graph.findNode("slop"), slap);
        TreeNode slab = new TreeNode(graph.findNode("slab"), slap);
        TreeNode slop2 = new TreeNode(graph.findNode("slop"), slip);
        TreeNode slap2 = new TreeNode(graph.findNode("slap"), slop2);
        TreeNode slab2 = new TreeNode(graph.findNode("slab"), slap2);

        expectedLeafNodes.add(slop);
        expectedLeafNodes.add(slab);
        expectedLeafNodes.add(slab2);

        Node startNode = graph.findNode(testCase1.get(0));
        StateTreeGenerator stateTreeGenerator = new StateTreeGenerator(startNode, visitedNodes, 10);

        while (stateTreeGenerator.hasNext()) {
            actualLeafNodes.add(stateTreeGenerator.next());
        }

        assertEquals(expectedLeafNodes, actualLeafNodes);
    }

    @Test
    void findsAllLeafNodes_TestCase2() {
        graph.createGraph(testCase2);
        TreeNode blip = new TreeNode(graph.findNode("blip"), null);
        TreeNode slip = new TreeNode(graph.findNode("slip"), blip);
        TreeNode slap = new TreeNode(graph.findNode("slap"), slip);
        TreeNode slop = new TreeNode(graph.findNode("slop"), slap);
        TreeNode stop = new TreeNode(graph.findNode("stop"), slop);
        TreeNode step = new TreeNode(graph.findNode("step"), stop);
        TreeNode shop = new TreeNode(graph.findNode("shop"), stop);

        TreeNode shop2 = new TreeNode(graph.findNode("shop"), slop);
        TreeNode stop2 = new TreeNode(graph.findNode("stop"), shop2);
        TreeNode step2 = new TreeNode(graph.findNode("step"), stop2);

        TreeNode slop2 = new TreeNode(graph.findNode("slop"), slip);
        TreeNode stop3 = new TreeNode(graph.findNode("stop"), slop2);
        TreeNode step3 = new TreeNode(graph.findNode("step"), stop3);
        TreeNode shop3 = new TreeNode(graph.findNode("shop"), stop3);

        TreeNode shop4 = new TreeNode(graph.findNode("shop"), slop2);
        TreeNode stop4 = new TreeNode(graph.findNode("stop"), shop4);
        TreeNode step4 = new TreeNode(graph.findNode("step"), stop4);

        TreeNode slap2 = new TreeNode(graph.findNode("slap"), slop2);

        expectedLeafNodes.add(step);
        expectedLeafNodes.add(shop);
        expectedLeafNodes.add(step2);
        expectedLeafNodes.add(step3);
        expectedLeafNodes.add(shop3);
        expectedLeafNodes.add(step4);
        expectedLeafNodes.add(slap2);

        Node startNode = graph.findNode(testCase2.get(0));
        StateTreeGenerator stateTreeGenerator = new StateTreeGenerator(startNode, visitedNodes, 10);

        while (stateTreeGenerator.hasNext()) {
            actualLeafNodes.add(stateTreeGenerator.next());
        }

        assertEquals(expectedLeafNodes, actualLeafNodes);
    }
}
