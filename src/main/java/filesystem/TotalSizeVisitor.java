package filesystem;

public final class TotalSizeVisitor implements Visitor {
    private long total;

    public long calculate(Element root) {
        total = 0;
        TreeWalker.walk(root, this);
        return total;
    }

    public void visit(FileNode file, String path, int depth) {
        total = Math.addExact(total, file.size());
    }

    public void visit(DirectoryNode directory, String path, int depth) {
    }
}
