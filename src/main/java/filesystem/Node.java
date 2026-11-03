package filesystem;

abstract sealed class Node implements Element permits FileNode, DirectoryNode {
    private final String name;
    private DirectoryNode parent;

    Node(String name) {
        if (name == null || name.isBlank() || name.equals(".") || name.equals("..")
                || name.contains("/") || name.contains("\\")) {
            throw new IllegalArgumentException("Invalid name.");
        }
        this.name = name;
    }

    public final String name() {
        return name;
    }

    final DirectoryNode parent() {
        return parent;
    }

    final void attachTo(DirectoryNode directory) {
        parent = directory;
    }
}
