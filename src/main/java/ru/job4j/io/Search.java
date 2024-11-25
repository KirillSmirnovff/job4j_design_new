package ru.job4j.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Search {
    public static void main(String[] args) throws IOException {
        Path start = validation(args);
        search(start, p -> p.toFile().getName().endsWith(args[1])).forEach(System.out::println);
    }
    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }

    private static Path validation(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Need to set 2 arguments in that order: root folder and file extension");
        }
        Path result = Paths.get(args[0]);
        if (!result.toFile().exists()) {
            throw new IllegalArgumentException("Root folder doesn't exist");
        }
        if (!args[1].startsWith(".")) {
            args[1] = "." + args[1];
        }
        return result;
    }
}