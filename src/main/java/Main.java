import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.mutNode;

public class Main {
    public static void main(String[] args) throws IOException {

        // Get 50 words from dictionary
        String[] words = new String[50];
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

        // Create Graph
        Graph g = graph("example2").directed().with(nodes);

        // Export Graph as PNG
        Graphviz.fromGraph(g).totalMemory(1000000000).width(3000).render(Format.PNG).toFile(new File("example/ex1.png"));
    }
}
