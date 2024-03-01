package cn.denghanxi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        LOGGER.debug("START...");
//        new TSLiker().test();
        new TSLiker().start();
    }
}