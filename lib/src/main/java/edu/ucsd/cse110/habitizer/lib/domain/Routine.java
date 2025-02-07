package edu.ucsd.cse110.habitizer.lib.domain;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Routine {
    private List<RoutineTask> tasks;
    private @NonNull final String title;

    public Routine(String title, List<RoutineTask> tasks) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Routine title cannot be blank");
        }
        this.title = title;
        this.tasks = tasks;
    }

    public List<RoutineTask> tasks() { return new ArrayList<>(tasks); }

    public String title() { return title; }
}
