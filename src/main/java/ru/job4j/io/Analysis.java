package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Analysis {
    public void unavailable(String source, String target) {
        List<String> result = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(source))) {
            List<String> log = in.lines().collect(Collectors.toList());
            int status = 1;
            String[] resultLine = new String[2];
            for (String line : log) {
                String[] pair = line.split(" ");
                if (status == 1
                        && (Objects.equals(pair[0], "400") || Objects.equals(pair[0], "500"))) {
                    resultLine[0] = pair[1];
                    status = 0;
                }
                if (status == 0
                        && (Objects.equals(pair[0], "200") || Objects.equals(pair[0], "300"))) {
                    resultLine[1] = pair[1];
                    status = 1;
                    result.add(resultLine[0] + ";" + resultLine[1]);
                }
            }
            if (status == 0) {
                result.add(resultLine[0] + "; now");
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