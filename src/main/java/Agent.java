import agent.RandomWalker;
import model.Node;
import model.Words;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Agent {
    public static void main(String[] args) throws IOException {
        List<Node> nodes = getNodes();

        RandomWalker randomWalk = new RandomWalker();

        Node currentNode = nodes.get(nodes.size() - 10);
        System.out.println("Current model.Node: " + currentNode.getName());
        for(Node node: currentNode.getLinks()) {
            System.out.println(node.getName() + " " + randomWalk.calculateWinLoseRatio(nodes.get(45), 5000));
        }
    }


    private static List<Node> getNodes() {

        // Create Nodes
        List<Node> nodes = new ArrayList<>();
        for (String word : Words.fourLetterWords) {
            nodes.add(new Node(word));
        }

        // Create Links
        for (Node node1 : nodes) {
            for (Node node2 : nodes) {
                if (!Words.formsEdge(node1.getName(), node2.getName()))
                    continue;
                node1.addLink(node2);
            }
        }
        return nodes;
    }
}
