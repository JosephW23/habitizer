package edu.ucsd.cse110.habitizer.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;
public class RoutineTask {
    private @Nullable Integer id;
    private Integer routineId;
    private @NonNull String title;
    private boolean isChecked;
    private String elapsedTime;
    private int sortOrder;

    public RoutineTask(@Nullable Integer id, Integer routineId, @NonNull String title, boolean isChecked, int sortOrder) {
        if (title.isBlank()) {
            throw new IllegalArgumentException("RoutineTask title cannot be blank");
        }

        this.id = id;
        this.routineId = routineId;
        this.title = title;
        this.isChecked = isChecked;
        this.elapsedTime = "-";
        this.sortOrder = sortOrder;
    }

    public @Nullable Integer id() { return id; }
    public String title() { return title; }
    public int sortOrder() { return sortOrder; }
    public Integer routineId() { return this.routineId; }
    public boolean isChecked() { return isChecked; }
    public String elapsedTime() { return elapsedTime; }

    // initialize task
    public void initialize() {
        this.isChecked = false;
        this.elapsedTime = "-";
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void checkOff(String elapsedTime) {
        this.isChecked = true;
        this.elapsedTime = elapsedTime; // set elapsed time when task is done
    }

    // Updates task name when in edit task dialog
    public void setTitle(String newTitle) {
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
