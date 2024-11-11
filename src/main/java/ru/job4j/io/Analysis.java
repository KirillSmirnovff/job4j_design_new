package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Analysis {
    public void unavailable(String source, String target) {
        List<String> result = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(source))) {
            List<String> log = in.lines().toList();
            int status = 1;
            String[] resultLine = new String[2];
            for (String line : log) {
                String[] pair = line.split(" ");
                String key = pair[0];
                String value = pair[1];
                if (status == 1
                        && (Objects.equals(key, "400") || Objects.equals(key, "500"))) {
                    resultLine[0] = value;
                    status = 0;
                }
                if (status == 0
                        && (Objects.equals(key, "200") || Objects.equals(key, "300"))) {
                    resultLine[1] = value;
                    status = 1;
                    result.add(resultLine[0] + ";" + resultLine[1]);
                }
            }
            if (status == 0) {
                result.add(resultLine[0] + ";now");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(target)
                ))) {
            for (String line : result) {
                out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        analysis.unavailable("data/server.log", "data/target.csv");
    }
}