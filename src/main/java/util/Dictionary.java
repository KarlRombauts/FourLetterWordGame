package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dictionary {
    public static List<String> load(String filepath) throws FileNotFoundException {
        List<String> result = new ArrayList<>();
        try (Scanner s = new Scanner(new FileReader(filepath))) {
            while (s.hasNext()) {
                result.add(s.nextLine());
            }
            return result;
        }
    }

    public static void main(String[] args) {
        try {
            List<String> words = Dictionary.load("src/main/java/dictionaries/common-four-letter.txt");
            for (String word: words) {
                System.out.println(word);
            }
            System.out.println(words.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
