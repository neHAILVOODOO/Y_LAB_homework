package ru.ylab;

public class Main {
    public static void main(String[] args) {

        SessionPanel sessionPanel = new SessionPanel();

        sessionPanel.setupSession();
        sessionPanel.startSessionThread();

        sessionPanel.run();


        }
    }

