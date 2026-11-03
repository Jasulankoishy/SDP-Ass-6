package filesystem;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FileSystemTest {
    private DirectoryNode tree() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode z = new DirectoryNode("z");
        DirectoryNode deep = new DirectoryNode("deep");
        DirectoryNode a = new DirectoryNode("a");
        root.add(z);
        root.add(a);
        z.add(deep);
        deep.add(new FileNode("log.txt", 20));
        a.add(new FileNode("log.txt", 8));
        a.add(new FileNode("photo.png", 40));
        root.add(new FileNode("zero", 0));
        return root;
    }

    @Test
    void dispatch() {
        DirectoryNode r = tree();
        int[] calls = {0, 0};
        TreeWalker.walk(r, new Visitor() {
            public void visit(FileNode file, String path, int depth) { calls[0]++; }
            public void visit(DirectoryNode dir, String path, int depth) { calls[1]++; }
        });
        assertArrayEquals(new int[]{4, 4}, calls);
    }

    @Test
    void sizeAcrossLevels() {
        assertEquals(68L, new TotalSizeVisitor().calculate(tree()));
    }

    @Test
    void bothFilters() {
        assertEquals(List.of("z/deep/log.txt"), new SearchVisitor("log", 20).search(tree()));
    }

    @Test
    void sortedRelativePaths() {
        assertEquals(List.of("a/log.txt", "z/deep/log.txt"), new SearchVisitor("log", 0).search(tree()));
    }

    @Test
    void countsAndDepth() {
        assertEquals(new StatisticsVisitor.Result(4, 4, 3), new StatisticsVisitor().collect(tree()));
    }

    @Test
    void emptyAndNoMatch() {
        DirectoryNode empty = new DirectoryNode("empty");
        assertEquals(0L, new TotalSizeVisitor().calculate(empty));
        assertEquals(new StatisticsVisitor.Result(0, 1, 0), new StatisticsVisitor().collect(empty));
        assertTrue(new SearchVisitor("xyz", 0).search(tree()).isEmpty());
    }

    @Test
    void reusableVisitors() {
        Element first = tree();
        Element second = new FileNode("log.txt", 5);
        TotalSizeVisitor size = new TotalSizeVisitor();
        SearchVisitor search = new SearchVisitor("log", 0);
        StatisticsVisitor stats = new StatisticsVisitor();
        assertEquals(68L, size.calculate(first));
        List<String> saved = search.search(first);
        assertEquals(new StatisticsVisitor.Result(4, 4, 3), stats.collect(first));
        assertEquals(5L, size.calculate(second));
        assertEquals(List.of("."), search.search(second));
        assertEquals(List.of("a/log.txt", "z/deep/log.txt"), saved);
        assertEquals(new StatisticsVisitor.Result(1, 0, 0), stats.collect(second));
    }

    @Test
    void invalidMutations() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode branch = new DirectoryNode("branch");
        root.add(branch);
        assertThrows(IllegalArgumentException.class, () -> branch.add(root));
        assertThrows(IllegalArgumentException.class, () -> root.add(root));
        assertThrows(IllegalArgumentException.class, () -> root.add(new FileNode("branch", 1)));
        assertThrows(IllegalArgumentException.class, () -> new DirectoryNode("other").add(branch));
        assertThrows(IllegalArgumentException.class, () -> new FileNode("bad", -1));
        assertThrows(UnsupportedOperationException.class, () -> root.children().clear());
        branch.add(new FileNode("valid", 7));
        assertEquals(7L, new TotalSizeVisitor().calculate(root));
    }
}
