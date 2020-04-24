package com.entities;

public class Todo {

    private String taskName;
    private boolean done = false;

    public Todo(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return taskName + ", " + (done ? "done" : "not done");
    }
}
