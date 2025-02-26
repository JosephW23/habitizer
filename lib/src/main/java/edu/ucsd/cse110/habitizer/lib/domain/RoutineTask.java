package edu.ucsd.cse110.habitizer.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;
public class RoutineTask {
    private final @Nullable Integer id;
    private @Nullable Integer routineId;
    private @NonNull String title;
    private boolean isChecked;
    private String elapsedTime;
    private int sortOrder;

    public RoutineTask(@Nullable Integer id, @NonNull String title, boolean isChecked, int sortOrder) {
        if (title.isBlank()) {
            throw new IllegalArgumentException("RoutineTask title cannot be blank");
        }

        this.id = id;
        this.title = title;
        this.isChecked = isChecked;
        this.elapsedTime = "-";
        this.sortOrder = sortOrder;
    }

    public @Nullable Integer id() { return id; }
    public @NonNull String title() { return title; }

    public int sortOrder() { return sortOrder; }

    public Integer routineId() { return this.routineId; }

    // initialize task
    public void initialize() {
        this.isChecked = false;
        this.elapsedTime = "-";
    }

    public void setRoutineId(Integer routineId) {
        this.routineId = routineId;
    }

    public boolean isChecked() { return isChecked; }

    public String elapsedTime() { return elapsedTime; }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

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
                isChecked == compare.isChecked();
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, isChecked);
    }
}
