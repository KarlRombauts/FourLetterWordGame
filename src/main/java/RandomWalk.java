import java.util.*;

public class RandomWalk {
    private Node currentNode;
    private Set<Node> visitedNodes = new HashSet<>();

    public double calculateWinLoseRatio(Node startingNode, int iterations) {
        int wins = 0;
        int losses = 0;
        for (int i = 0; i < iterations ; i++) {
           int length = walk(startingNode);
            if ((length % 2 == 1)) {
                wins++;
            } else {
                losses++;
            }
        }

        return (double) wins / (double) (wins + losses);
    }

    public int walk(Node startingNode) {
        visitedNodes.clear();
        currentNode = startingNode;
        int walkLength = 0;
        boolean walking = true;

        while(walking) {
            visitedNodes.add(currentNode);
//            System.out.println("Visiting node: " + currentNode.getName());
            List<Node> unvisitedNodes = getUnvisitedConnectedNodes();

            if (unvisitedNodes.size() == 0) {
                walking = false;
            } else {
                currentNode = unvisitedNodes.get(new Random().nextInt(unvisitedNodes.size()));
                walkLength++;
            }
        }

//        System.out.println("Walk length: " + walkLength + " " + ((walkLength % 2 == 0) ? "Lose": "Win"));
        return walkLength;
    }


    public List<Node> getUnvisitedConnectedNodes() {
        List<Node> unvisitedNodes = new ArrayList<>();

        for (Node node: currentNode.getLinks()) {
            if (!visitedNodes.contains(node)) {
                unvisitedNodes.add(node);
            }
        }

        return unvisitedNodes;
    }
}
