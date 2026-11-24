package com.empresa.atendimento.shared.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public final class IdGenerator {

    private IdGenerator() {}

    public static UUID generateId() {
        return UUID.randomUUID();
    }

    public static String generateTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static String sanitizeInput(String input) {
        if (input == null) return null;
        return input.strip().replaceAll("[<>]", "");
    }
}
