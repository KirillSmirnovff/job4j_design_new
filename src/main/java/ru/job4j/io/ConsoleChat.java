package ru.job4j.io;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleChat {
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private final String path;
    private final String botAnswers;

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() throws IOException {
        boolean run = true;
        boolean active = true;
        List<String> phrases = readPhrases();
        List<String> log = new ArrayList<>();
        log.add("Привет! Я общительный бот! Давай поговорим!");
        System.out.println("Привет! Я общительный бот! Давай поговорим!");
        while (run) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();
            log.add(input);
            if (OUT.equals(input)) {
                log.add("Пока!");
                System.out.println("Пока!");
                run = false;
                saveLog(log);
                continue;
            }
            if (STOP.equals(input)) {
                log.add("Замолкаю");
                System.out.println("Замолкаю");
                active = false;
                continue;
            }
            if (CONTINUE.equals(input)) {
                log.add("Снова буду отвечать");
                System.out.println("Снова буду отвечать");
                active = true;
                continue;
            }
            if (active) {
                int index = (int) (Math.random() * phrases.size());
                String currentPhrase = phrases.get(index);
                log.add(currentPhrase);
                System.out.println(currentPhrase);
            }
        }
    }

    private List<String> readPhrases() {
        List<String> result = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(botAnswers))) {
            result = in.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void saveLog(List<String> log) {
        try (PrintWriter out = new PrintWriter(new FileWriter(path, StandardCharsets.UTF_8, true))) {
            log.forEach(out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ConsoleChat consoleChat = new ConsoleChat("", "");
        consoleChat.run();
    }
}