package ru.job4j.io.duplicate;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {

    private final Map<FileProperty, List<Path>> files = new HashMap<>();

    public Map<FileProperty, List<Path>> getFiles() {
        return files;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileProperty fileProperty = new FileProperty(file.toFile().length(), file.toFile().getName());
        if (!files.containsKey(fileProperty)) {
            files.put(fileProperty, new ArrayList<>(List.of(file.toAbsolutePath())));
        } else {
            files.get(fileProperty).add(file.toAbsolutePath());
        }
        return super.visitFile(file, attrs);
    }

    public void output() {
        for (Map.Entry<FileProperty, List<Path>> entry : getFiles().entrySet()) {
            if (entry.getValue().size() > 1) {
                System.out.printf("%s - %sBytes" + System.lineSeparator(), entry.getKey().getName(), entry.getKey().getSize());
                for (Path value : entry.getValue()) {
                    System.out.println("    " + value);
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        DuplicatesVisitor visitor = new DuplicatesVisitor();
        Files.walkFileTree(Path.of("D:/test"), visitor);
        visitor.output();
    }
}