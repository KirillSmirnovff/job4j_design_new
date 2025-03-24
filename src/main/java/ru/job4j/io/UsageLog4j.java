package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        LOG.trace("trace message");
        LOG.debug("debug message");
        LOG.info("info message");
        LOG.warn("warn message");
        LOG.error("error message");
        byte size = 3;
        short age = 100;
        int numOne = 200;
        long numTwo = 300L;
        float numThree = 400.0F;
        double numFour = 500D;
        boolean variable = true;
        char character =  2;
        LOG.debug("Primitive types: {}, {}, {}, {}, {}, {}, {}, {}", size, age, numOne, numTwo, numThree, numFour, variable, character);
    }
}
