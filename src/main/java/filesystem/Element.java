package filesystem;

import java.util.List;

public sealed interface Element permits Node {
    String name();
    void accept(Visitor visitor, String path, int depth);

    default List<Element> children() {
        return List.of();
    }
}
