package com.guance.javaagent;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyMainClass {

    static final Logger logger = LoggerFactory.getLogger(MyMainClass.class);

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        Config config = Config.parse(args);
        JavaAgentLoader.loadAgent(config);
    }
}