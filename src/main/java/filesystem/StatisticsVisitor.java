package filesystem;

public final class StatisticsVisitor implements Visitor {
    public record Result(int files, int directories, int maxDepth) {
    }

    private int files;
    private int directories;
    private int maxDepth;

    public Result collect(Element root) {
        files = 0;
        directories = 0;
        maxDepth = 0;
        TreeWalker.walk(root, this);
        return new Result(files, directories, maxDepth);
    }

    public void visit(FileNode file, String path, int depth) {
        files++;
        maxDepth = Math.max(maxDepth, depth);
    }

    public void visit(DirectoryNode directory, String path, int depth) {
        directories++;
        maxDepth = Math.max(maxDepth, depth);
    }
}
