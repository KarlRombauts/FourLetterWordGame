import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableNode;
import model.Words;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static guru.nidi.graphviz.model.Factory.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<MutableNode> nodes = getNodes(200);


        // Create Graph
        Graph g = graph("example2").directed().with(nodes);

        // Export Graph as PNG
        Graphviz.fromGraph(g).totalMemory(1000000000).width(3000).render(Format.PNG).toFile(new File("example/ex2.png"));
    }

    private static List<MutableNode> getNodes(int number) {
        // Get 50 words from dictionary
        String[] words = new String[number];
        System.arraycopy(Words.fourLetterWords, 0, words, 0, words.length);

        // Create Nodes
        List<MutableNode> nodes = new ArrayList<>();
        for (String word : words) {
            nodes.add(mutNode(word));
        }

        // Create Links
        for (MutableNode node1 : nodes) {
            for (MutableNode node2 : nodes) {
                if (!Words.formsEdge(node1.name().toString(), node2.name().toString()))
                    continue;
                node1.addLink(node2);
            }
        }

        return nodes;
    }

}
