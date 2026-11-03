package filesystem;

public final class TreeWalker {
    private TreeWalker() {
    }

    public static void walk(Element root, Visitor visitor) {
        walk(root, visitor, "", 0);
    }

    private static void walk(Element node, Visitor visitor, String path, int depth) {
        node.accept(visitor, path, depth);
        for (Element child : node.children()) {
            String childPath = path.isEmpty() ? child.name() : path + "/" + child.name();
            walk(child, visitor, childPath, depth + 1);
        }
    }
}
