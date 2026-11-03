package filesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class SearchVisitor implements Visitor {
    private final String namePart;
    private final long minimumSize;
    private final List<String> matches = new ArrayList<>();

    public SearchVisitor(String namePart, long minimumSize) {
        this.namePart = Objects.requireNonNull(namePart);
        if (minimumSize < 0) {
            throw new IllegalArgumentException("Minimum size cannot be negative.");
        }
        this.minimumSize = minimumSize;
    }

    public List<String> search(Element root) {
        matches.clear();
        TreeWalker.walk(root, this);
        matches.sort(String::compareTo);
        return List.copyOf(matches);
    }

    public void visit(FileNode file, String path, int depth) {
        if (file.name().contains(namePart) && file.size() >= minimumSize) {
            matches.add(path.isEmpty() ? "." : path);
        }
    }

    public void visit(DirectoryNode directory, String path, int depth) {
    }
}
