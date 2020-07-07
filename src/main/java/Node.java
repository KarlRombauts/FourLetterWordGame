import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Node {
    private final String name;
    private List<Node> links = new ArrayList<>();

    public Node(String name) {
        this.name = name;
    }

    public void addLink(Node node) {
        links.add(node);
    }

    public String getName() {
       return name;
    }

    public List<Node> getLinks() {
        return links;
    }
}
