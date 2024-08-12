package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoxTest {
    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Sphere");
    }

    @Test
    void isThisUnknown() {
        Box box = new Box(6, -2);
        String name = box.whatsThis();
        assertThat(name).isNotBlank().isEqualTo("Unknown object");
    }

    @Test
    void isVertexEqualToFour() {
        Box box = new Box(4, 6);
        int vertex = box.getNumberOfVertices();
        assertThat(vertex).isPositive().isEqualTo(4);
    }

    @Test
    void isVertexNegative() {
        Box box = new Box(7, 2);
        int vertex = box.getNumberOfVertices();
        assertThat(vertex).isNegative().isEqualTo(-1);
    }

    @Test
    void isBoxExist() {
        Box box = new Box(4, 6);
        boolean existence = box.isExist();
        assertThat(existence).isNotNull().isTrue();
    }

    @Test
    void isBoxNotExist() {
        Box box = new Box(5, 6);
        boolean existence = box.isExist();
        assertThat(existence).isNotNull().isFalse();
    }

    @Test
    void isAreaCloseToTwoHundredOne() {
        Box box = new Box(0, 4);
        double area = box.getArea();
        assertThat(area).isPositive().isCloseTo(201d, withPrecision(1d));
    }

    @Test
    void isAreaEqualTo6() {
        Box box = new Box(8, 1);
        double area = box.getArea();
        assertThat(area).isPositive().isEqualTo(6);
    }
}