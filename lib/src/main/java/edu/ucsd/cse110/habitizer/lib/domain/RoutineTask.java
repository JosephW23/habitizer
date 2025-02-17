package edu.ucsd.cse110.habitizer.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;
public class RoutineTask {
    private final @Nullable Integer id;
    private @NonNull String title;
    private final int priority;
    private boolean isChecked;

    private String elapsedTime;

    public RoutineTask(@Nullable Integer id, @NonNull String title, int priority, boolean isChecked) {
        if (title.isBlank()) {
            throw new IllegalArgumentException("RoutineTask title cannot be blank");
        }
        if (priority <= 0) {
            throw new IllegalArgumentException("RoutineTask priority must be an integer greater than 0");
        }
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.isChecked = isChecked;
        this.elapsedTime = "-";
    }

    public @Nullable Integer id() { return id; }
    public @NonNull String title() { return title; }

    public int priority() { return priority; }

    // initialize task
    public void initialize() {
        this.isChecked = false;
        this.elapsedTime = "-";
    }

    public boolean isChecked() { return isChecked; }

    public String getElapsedTime() { return elapsedTime; }

    public void checkOff(String elapsedTime) {
        this.isChecked = true;
        this.elapsedTime = elapsedTime; // set elapsed time when task is done
    }

    // Updates task name when in edit task dialog
    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoutineTask compare = (RoutineTask) o;
        return title.equals(compare.title()) &&
                priority == compare.priority() &&
                isChecked == compare.isChecked();
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, priority, isChecked);
    }
}
