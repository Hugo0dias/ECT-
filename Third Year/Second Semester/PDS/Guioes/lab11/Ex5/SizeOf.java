import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class SizeOf {

    private static class FileSizeCalculator extends SimpleFileVisitor<Path> {
        private final Map<Path, Long> dirSizes = new HashMap<>();
        private long totalSize = 0;

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            long fileSize = attrs.size();
            totalSize += fileSize;
            Path parent = file.getParent();
            dirSizes.put(parent, dirSizes.getOrDefault(parent, 0L) + fileSize);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            if (exc == null) {
                long dirSize = dirSizes.getOrDefault(dir, 0L);
                dirSizes.put(dir, dirSize);
                return FileVisitResult.CONTINUE;
            } else {
                throw exc;
            }
        }

        public Map<Path, Long> getDirSizes() {
            return dirSizes;
        }

        public long getTotalSize() {
            return totalSize;
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1 || args.length > 2) {
            System.out.println("Usage: java -jar sizeOf.jar [-r] <directory>");
            return;
        }

        boolean recursive = false;
        Path rootPath;

        if (args.length == 2 && args[0].equals("-r")) {
            recursive = true;
            rootPath = Paths.get(args[1]);
        } else {
            rootPath = Paths.get(args[0]);
        }

        if (!Files.isDirectory(rootPath)) {
            System.out.println("Provided path is not a directory.");
            return;
        }

        FileSizeCalculator calculator = new FileSizeCalculator();
        Files.walkFileTree(rootPath, calculator);

        Map<Path, Long> dirSizes = calculator.getDirSizes();
        long totalSize = calculator.getTotalSize();

        if (recursive) {
            for (Map.Entry<Path, Long> entry : dirSizes.entrySet()) {
                Path path = entry.getKey();
                long size = entry.getValue();
                if (Files.isDirectory(path)) {
                    System.out.printf("%s: %d kB\n", rootPath.relativize(path).toString(), size / 1024);
                } else {
                    System.out.printf("|-> %s: %d kB\n", rootPath.relativize(path).toString(), size / 1024);
                }
            }
        } else {
            for (Map.Entry<Path, Long> entry : dirSizes.entrySet()) {
                Path path = entry.getKey();
                if (path.equals(rootPath) || path.getParent().equals(rootPath)) {
                    long size = entry.getValue();
                    System.out.printf("%s: %d kB\n", rootPath.relativize(path).toString(), size / 1024);
                }
            }
        }

        System.out.printf("Total: %d kB\n", totalSize / 1024);
    }
}