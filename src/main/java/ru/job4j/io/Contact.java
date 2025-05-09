package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;

public class Contact implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int zipCode;
    private final String phone;

    public Contact(int zipCode, String phone) {
        this.zipCode = zipCode;
        this.phone = phone;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Contact{"
                + "zipCode=" + zipCode
                + ", phone='" + phone + '\''
                + '}';
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final Contact contact = new Contact(12345, "+5 (222) 2345456");

        File tempFile = Files.createTempFile(null, null).toFile();
        try (FileOutputStream out = new FileOutputStream(tempFile);
            ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(contact);
        }

        try (FileInputStream in = new FileInputStream(tempFile);
            ObjectInputStream ois = new ObjectInputStream(in)) {
            final Contact contactFromFile = (Contact) ois.readObject();
            System.out.println(contactFromFile);
        }
        System.out.println(contact);
    }
}
