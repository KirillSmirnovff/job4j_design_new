package ru.job4j.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Balancer {
    public static void split(List<ArrayList<Integer>> nodes, Iterator<Integer> source) {
        int point = 0;
        while (source.hasNext()) {
            if (point >= nodes.size()) {
                point = 0;
            }
            nodes.get(point++).add(source.next());
        }
    }
}