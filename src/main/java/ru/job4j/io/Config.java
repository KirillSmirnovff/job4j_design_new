package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class Config {

    private final String path;
    private final Map<String, String> values = new HashMap<String, String>();

    public Config(final String path) {
        this.path = path;
    }

    public void load() {
        String[] out = toString().split(System.lineSeparator());
        for (String line : out) {
            line = line.strip();
            if (!line.isEmpty() && !line.startsWith("#")) {
                long count = line.chars().
                        filter(ch -> ch == 61).
                        count();
                String[] pair = line.split("=", 2);
                if (pair[0].isEmpty() || pair[1].isEmpty() || (pair[1].contains("=") && pair[1].length() == count - 1)) {
                    throw new IllegalArgumentException();
                }
                values.put(pair[0], pair[1]);
            }
        }
    }

    public String value(String key) {
        if (!values.containsKey(key)) {
            throw new NoSuchElementException();
        }
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner output = new StringJoiner(System.lineSeparator());
        try (BufferedReader reader = new BufferedReader(new FileReader(this.path))) {
            reader.lines().forEach(output::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Config("data/app.properties"));
    }

}