package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class CSVReader {

    public static void handle(ArgsName argsName) throws Exception {
        String delimiter = argsName.get("delimiter");
        String out = argsName.get("out");
        StringJoiner joiner = new StringJoiner(",");
        List<String> result = new ArrayList<>();
        List<String> filter = Arrays.stream(argsName.get("filter").split(",")).toList();
        try (Scanner scanner = new Scanner(new FileInputStream(argsName.get("path"))).useDelimiter(delimiter)) {
            while (scanner.hasNext()) {
                joiner.add(scanner.next());
            }
        }
        String[] array = joiner.toString().split(System.lineSeparator());
        String[] nameOfColumns = array[0].split(",");
        Map<Integer, Integer> indexes = new HashMap<>();
        for (int i = 0; i < nameOfColumns.length; i++) {
            String column = nameOfColumns[i];
            if (filter.contains(column)) {
                indexes.putIfAbsent(i, filter.indexOf(column));
            }
        }
        for (String string : array) {
            StringJoiner stringJoiner = new StringJoiner(delimiter);
            String[] converter = new String[filter.size()];
            String[] line = string.split(",");
            for (int i = 0; i < line.length; i++) {
                if (indexes.containsKey(i)) {
                    converter[indexes.get(i)] = line[i];
                }
            }
            for (String s : converter) {
                stringJoiner.add(s);
            }
            result.add(stringJoiner.toString());
        }
        if ("stdout".equals(out)) {
            for (String line : result) {
                System.out.println(line);
            }
        } else {
            try (PrintWriter writer = new PrintWriter(new FileOutputStream(out))) {
                result.forEach(writer::println);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ArgsName validation(String[] args) {
        if (args.length != 4) {
            throw  new IllegalArgumentException("Need to set 4 arguments: root path, delimiter, out path and filter");
        }
        ArgsName argsName = ArgsName.of(args);
        String rootPath = argsName.get("path");
        String outPath = argsName.get("out");
        argsName.get("filter");
        argsName.get("delimiter");
        if (!Path.of(rootPath).toFile().exists()) {
            throw new IllegalArgumentException("Root file doesn't exist");
        }
        if (!("stdout".equals(outPath) || outPath.endsWith(".csv"))) {
            throw new IllegalArgumentException("Wrong output format");
        }
        return argsName;
    }
}

