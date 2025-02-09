package edu.ucsd.cse110.habitizer.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class RoutineTask {
    private @Nullable Integer id;
    private @NonNull String title;
    private int priority;
    private boolean isChecked;

    // Todo: Need timer object to track how much time is used for this task.
    public RoutineTask(@Nullable Integer id, @NonNull String title, int priority, boolean isChecked) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("RoutineTask title cannot be blank");
        }
        if (priority <= 0) {
            throw new IllegalArgumentException("RoutineTask priority must be an integer greater than 0");
        }
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.isChecked = isChecked;
    }

    public @Nullable Integer id() { return id; }
    public @NonNull String title() { return title; }

    public int priority() { return priority; }

    public boolean isChecked() { return isChecked; }

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
