package Launcher;

import GUI.HomePage;

public class Main {
    private static Object lock = new Object(); // TODO ??
    public static void main(String[] args) {
        new HomePage();
    }
}
