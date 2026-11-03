package filesystem;

public final class FileNode extends Node {
    private final long size;

    public FileNode(String name, long size) {
        super(name);
        if (size < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        this.size = size;
    }

    public long size() {
        return size;
    }

    public void accept(Visitor visitor, String path, int depth) {
        visitor.visit(this, path, depth);
    }
}
