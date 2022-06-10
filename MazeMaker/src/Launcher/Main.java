package Launcher;

import GUI.HomePage;

public class Main {
    public static final boolean isDebug =
            java.lang.management.ManagementFactory.getRuntimeMXBean().
                    getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
    public static void main(String[] args) {
        new HomePage();
    }
}
