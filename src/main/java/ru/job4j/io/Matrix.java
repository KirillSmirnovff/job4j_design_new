package ru.job4j.io;

import java.io.FileOutputStream;

public class Matrix {
    public static void multiply(int size) {
        try (FileOutputStream out = new FileOutputStream("data/Multiply.txt")) {
            for (int i = 1; i <= size; i++) {
                for (int j = 1; j <= size; j++) {
                    out.write((i + " * " + j + " = " + i * j).getBytes());
                    out.write(System.lineSeparator().getBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        multiply(9);
    }
}