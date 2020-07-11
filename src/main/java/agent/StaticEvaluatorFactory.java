package agent;

import model.Node;
import model.WordGraph;

import java.util.Set;

public class StaticEvaluatorFactory {
    private WordGraph graph;

    public StaticEvaluatorFactory(WordGraph graph) {
        this.graph = graph;
    }

    public void getInstance(Set<Node> currentPath) {
        new StaticEvaluator(graph, new RandomWalker().setPreviouslyVisitedNodes(currentPath));
    }
}
