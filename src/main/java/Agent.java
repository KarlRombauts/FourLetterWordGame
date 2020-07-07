import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Agent {
    public static void main(String[] args) throws IOException {
        List<Node> nodes = getNodes(Words.fourLetterWords.length - 1);

        RandomWalk randomWalk = new RandomWalk();

        Node currentNode = nodes.get(nodes.size() - 10);
        System.out.println("Current Node: " + currentNode.getName());
        for(Node node: currentNode.getLinks()) {
            System.out.println(node.getName() + " " + randomWalk.calculateWinLoseRatio(nodes.get(45), 10000));
        }
    }


    private static List<Node> getNodes(int number) {
        // Get words from dictionary
        String[] words = new String[number];
        System.arraycopy(Words.fourLetterWords, 0, words, 0, words.length);

        // Create Nodes
        List<Node> nodes = new ArrayList<>();
        for (String word : words) {
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
