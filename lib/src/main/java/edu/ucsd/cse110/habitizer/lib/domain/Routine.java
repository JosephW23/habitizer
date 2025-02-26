package edu.ucsd.cse110.habitizer.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Routine {
    private final @Nullable Integer id;
    private @NonNull String title;
    private int sortOrder;
    private List<RoutineTask> tasks;

    public Routine(@Nullable int id, @NonNull String title, @NonNull int sortOrder) {
        if (title.isBlank()) {
            throw new IllegalArgumentException("Routine title cannot be blank");
        }
        this.id = id;
        this.title = title;
        this.sortOrder = sortOrder;
        this.tasks = new ArrayList<>();
    }

    public void setTasks(List<RoutineTask> tasks) {
        for (var task: tasks) {
            this.tasks.add(task);
        }
    }

    public int id() { return id; }
    public String title() { return title; }
    public List<RoutineTask> tasks() { return new ArrayList<>(tasks); } // Return a copy for safety

    // Add a task to the routine
    public Routine addTask(RoutineTask task) {
        var routine = new Routine(this.id, this.title, 0);
        List<RoutineTask> updatedTasks = new ArrayList<>(tasks);
        updatedTasks.add(task);
        routine.setTasks(updatedTasks);
        return routine; // Return a new Routine object
    }
}
