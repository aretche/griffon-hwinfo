package io.github.aretche.hwlist

import griffon.javafx.JavaFXGriffonApplication

public class Launcher {
    public static void main(String[] args) throws Exception {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "info")
        System.setProperty("griffon.full.stacktrace", "true")
        System.setProperty("griffon.exception.output","true")
        JavaFXGriffonApplication.main(args)
    }
}