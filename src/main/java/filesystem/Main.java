package filesystem;

public class Main {
    public static void main(String[] args) {
        DirectoryNode root = new DirectoryNode("campus");
        DirectoryNode lab = new DirectoryNode("lab");
        DirectoryNode archive = new DirectoryNode("archive");
        root.add(lab);
        root.add(archive);
        root.add(new DirectoryNode("empty"));
        lab.add(new FileNode("notes.txt", 90));
        lab.add(new FileNode("map.png", 500));
        archive.add(new FileNode("notes-old.txt", 120));
        root.add(new FileNode("notes-index.txt", 30));

        String namePart = args.length > 0 ? args[0] : "notes";
        long minimumSize = args.length > 1 ? Long.parseLong(args[1]) : 90;
        System.out.println("Total bytes: " + new TotalSizeVisitor().calculate(root));
        System.out.println("Matches: " + new SearchVisitor(namePart, minimumSize).search(root));
        System.out.println(new StatisticsVisitor().collect(root));
    }
}
