package filesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class DirectoryNode extends Node {
    private final List<Element> children = new ArrayList<>();

    public DirectoryNode(String name) {
        super(name);
    }

    public void add(Node child) {
        Objects.requireNonNull(child, "Child is required.");
        if (child.parent() != null) {
            throw new IllegalArgumentException("Child already has a parent.");
        }
        for (Node ancestor = this; ancestor != null; ancestor = ancestor.parent()) {
            if (ancestor == child) {
                throw new IllegalArgumentException("Cycle is not allowed.");
            }
        }
        for (Element existing : children) {
            if (existing.name().equals(child.name())) {
                throw new IllegalArgumentException("Duplicate child name.");
            }
        }
        children.add(child);
        child.attachTo(this);
    }

    public List<Element> children() {
        return List.copyOf(children);
    }

    public void accept(Visitor visitor, String path, int depth) {
        visitor.visit(this, path, depth);
    }
}
