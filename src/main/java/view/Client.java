package view;

import util.Dictionary;
import model.GameEngine;
import model.HumanPlayer;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        GameEngine gameEngine = new GameEngine(Dictionary.load("src/main/java/dictionaries/common-four-letter.txt"));
        gameEngine.addPlayer(new HumanPlayer("Player 1", scanner));
        gameEngine.addPlayer(new HumanPlayer("Player 2", scanner));
        gameEngine.start();
    }
}
