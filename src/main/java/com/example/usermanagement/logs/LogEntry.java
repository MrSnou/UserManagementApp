package com.example.usermanagement.logs;

import com.example.usermanagement.logs.ActionType;

import java.time.LocalDateTime;

public class LogEntry {
    private String author;
    private String target;
    private ActionType action;
    private LocalDateTime time;

    public String getAuthor() {
        return author;
    }
    public String getTarget() {
        return target;
    }
    public ActionType getAction() {
        return action;
    }
    public LocalDateTime getTime() {
        return time;
    }

    public LogEntry(String author, ActionType action) {
        this.author = author;
        this.action = action;
        this.target = "No Target";
        this.time = LocalDateTime.now();
    }

    public LogEntry(String author, String target, ActionType action) {
        this.author = author;
        this.target = target;
        this.action = action;
        this.time = LocalDateTime.now();
    }

    @Override
    public String toString() {
        if (action == ActionType.LOGIN) {
            return time + " " + author + " " + ActionType.LOGIN;
        } else if (action == ActionType.LOGOUT) {
            return time + " " + author + " " + ActionType.LOGOUT;
        } else if (action == ActionType.CREATE_USER) {
            return time + " Guest " + ActionType.CREATE_USER + " " + author;
        }
        return time + " " + author + " " + action + " " + target;
    }
}
