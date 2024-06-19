package org.gym.util;

import java.io.IOException;

public class ConsoleUtils {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void waitForEnter() {
        System.out.println("Pressione Enter para continuar...");
        try {
            System.in.read();
        } catch (IOException e) {
            System.out.println("Error waiting for enter: " + e.getMessage());
        }
    }
}
