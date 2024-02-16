package ru.cod331n;

import java.util.UUID;

public class IdGenerator {
    public static long generateId() {
        long id = UUID.randomUUID().getMostSignificantBits();
        while (id < 0) {
            id = UUID.randomUUID().getMostSignificantBits();
        }
        return id;
    }
}
