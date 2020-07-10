package model;

import model.Node;
import model.Player;
import model.WordGraph;

import java.util.*;

public class GameEngine {
    private WordGraph graph;
    private List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private Set<Node> visitedNodes = new HashSet<>();
    private Node currentNode;

    public GameEngine(List<String> dictionary) {
        graph = new WordGraph();
        graph.createGraph(dictionary);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void visitNode(Node node) {
        visitedNodes.add(node);
        this.currentNode = node;
    }

    public void playerTurn() {
        Player player = players.get(currentPlayerIndex);
        String word;
        do {
            word = player.takeTurn();
        } while(!isValidTurn(word));

        visitNode(graph.findNode(word));
        System.out.printf("%s played: %s \n", player.getName(), currentNode.getName());
        setIndexToNextPlayer();
    }

    private boolean isValidTurn(String word) {
        Node node = graph.findNode(word);
        if (node == null) {
            System.out.printf("Illegal move! %s is not a valid four letter word\n", word);
            return false;
        }
        if (!isNodeUnvisited(node)) {
            System.out.printf("Illegal move! %s has already been visited\n", node.getName());
            return false;
        }
        if (!currentNode.hasLinkWith(node)) {
            System.out.printf("Illegal move! You cant go to %s from %s\n", node.getName(), currentNode.getName());
            return false;
        }
        return true;
    }

    public void setIndexToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public boolean isNodeUnvisited(Node node) {
        return !visitedNodes.contains(node);
    }

    public void start() {
        visitNode(graph.getRandomNode());
        System.out.printf("Starting word: %s\n", currentNode.getName());
        while (!isGameOver()) {
            playerTurn();
        }
    }

    private boolean isGameOver() {
        for (Node node : currentNode.getLinks()) {
            if (isNodeUnvisited(node)) {
                return false;
            }
        }
        return true;
    }
}
