package model;

import java.util.Scanner;

public class HumanPlayer implements Player {
    private String name;
    private Scanner scanner;

    public HumanPlayer(String name, Scanner scanner) {
        this.name = name;
        this.scanner = scanner;
    }

    @Override
    public String takeTurn() {
        System.out.printf("%s's turn: \n", name);
        return scanner.nextLine();
    }

    @Override
    public String getName() {
        return name;
    }
}
