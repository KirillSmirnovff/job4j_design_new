package ru.job4j.map;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class User {

    private String name;
    private int children;
    private Calendar birthday;

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return children == user.children && Objects.equals(name, user.name) && Objects.equals(birthday, user.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, children, birthday);
    }

    public static void main(String[] args) {
        Calendar birth = Calendar.getInstance();
        birth.set(1990, Calendar.OCTOBER, 12);
        User one = new User("Kirill", 0, birth);
        User two = new User("Kirill", 0, birth);
        HashMap<User, Object> map = new HashMap<>();
        map.put(one, new Object());
        map.put(two, new Object());
        System.out.println(map);
    }
}