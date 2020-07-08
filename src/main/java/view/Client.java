package view;

import model.GameEngine;
import model.HumanPlayer;
import model.Words;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameEngine gameEngine = new GameEngine(Words.fourLetterWords);
        gameEngine.addPlayer(new HumanPlayer("Player 1", scanner));
        gameEngine.addPlayer(new HumanPlayer("Player 2", scanner));

        gameEngine.start();
    }
}
