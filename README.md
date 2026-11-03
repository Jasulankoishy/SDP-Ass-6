# Assignment 6 Visitor Pattern

Java 17 in-memory file system. Run from the project root with JDK 17+ and Maven.

```sh
mvn test
mvn package
java -cp target/classes filesystem.Main
java -cp target/classes filesystem.Main map 100
```

The two optional arguments are a case-sensitive filename substring and an inclusive minimum byte size. An empty substring matches every name. The default search is `notes` with a minimum of 90 bytes.

```text
Total bytes: 740
Matches: [archive/notes-old.txt, lab/notes.txt]
Result[files=4, directories=4, maxDepth=2]
```

TreeWalker owns traversal. It visits each node once and passes its depth and root-relative path to accept. Root depth is zero. Paths omit the root name, use /, and are sorted by String.compareTo. A matching file used as the root is shown as ".".

Call calculate, search, or collect to run the corresponding visitor. These methods reset visitor state on every call. Instances can be reused sequentially; search results are immutable snapshots.

StatisticsVisitor adds a new operation using the existing Visitor interface. Adding another element type would require changing the sealed hierarchy, Visitor, and the existing visitor implementations.

