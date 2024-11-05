package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class LogFilter {
    private final String file;

    public LogFilter(String file) {
        this.file = file;
    }

    public List<String> filter() {
        List<String> result = null;
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            result = in.lines().
                    filter(s -> "404".equals(s.split(" ")[s.split(" ").length - 2])).
                    collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        LogFilter logFilter = new LogFilter("data/log.txt");
        logFilter.filter().forEach(System.out::println);
    }
}