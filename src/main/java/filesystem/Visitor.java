package filesystem;

public interface Visitor {
    void visit(FileNode file, String path, int depth);
    void visit(DirectoryNode directory, String path, int depth);
}
