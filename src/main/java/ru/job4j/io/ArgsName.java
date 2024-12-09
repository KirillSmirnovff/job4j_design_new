package ru.job4j.io;

import java.util.HashMap;
import java.util.Map;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException("This key: '" + key + "' is missing");
        }
        return values.get(key);
    }

    private void parse(String[] args) {
        for (String arg : args) {
            if (!arg.contains("=")) {
                throw new IllegalArgumentException("Error: This argument '" + arg + "' does not contain an equal sign");
            }
            if (!arg.startsWith("-")) {
                throw new IllegalArgumentException("Error: This argument '" + arg + "' does not start with a '-' character");
            }
            String[] pair = arg.replaceFirst("-", "").split("=", 2);
            if (pair[0].isBlank()) {
                throw new IllegalArgumentException("Error: This argument '" + arg + "' does not contain a key");
            }
            if (pair[1].isBlank()) {
                throw new IllegalArgumentException("Error: This argument '" + arg + "' does not contain a value");
            }
            values.put(pair[0], pair[1]);
        }
    }

    public static ArgsName of(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Arguments not passed to program");
        }
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    public static void main(String[] args) {
        ArgsName jvm = ArgsName.of(new String[] {"-Xmx=512", "-encoding=UTF-8"});
        System.out.println(jvm.get("Xmx"));

        ArgsName zip = ArgsName.of(new String[] {"-out=project.zip", "-encoding=UTF-8"});
        System.out.println(zip.get("out"));
    }
}