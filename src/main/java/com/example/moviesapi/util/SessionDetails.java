package com.example.moviesapi.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SessionDetails implements CommandLineRunner {

    private static long activeUserId;

    public static void setActiveUserId(long activeUserId) {
        SessionDetails.activeUserId = activeUserId;
        System.out.println("Hello, " + activeUserId);
    }

    public static void clearActiveUser() {
        activeUserId = -1;
    }

    public static long getActiveUserId() {
        return activeUserId;
    }

    @Override
    public void run(String... args) {
        activeUserId = -1;
    }
}
