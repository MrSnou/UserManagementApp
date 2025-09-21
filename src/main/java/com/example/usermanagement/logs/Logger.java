package com.example.usermanagement.logs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Logger {
    private static final List<LogEntry> logsList = new ArrayList<LogEntry>();

    public static void log(String author, String target, ActionType action) {
        LogEntry entry;
        if (action == ActionType.LOGIN || action == ActionType.LOGOUT || action == ActionType.LOGIN_FAILURE_WRONG_PASS || action == ActionType.CREATE_USER) {
            entry = new LogEntry(author, action);
            logsList.add(entry);
        } else {
            entry = new LogEntry(author, target, action);
            logsList.add(entry);
        }
        if (entry == null) {
            System.out.println("[ERROR] Log Entry is null");
        }
        System.out.println("[LOG] | " + entry + " | [LOG]");
    }

    public static List<LogEntry> getLogsList() {
        return Collections.unmodifiableList(logsList);
    }
}
