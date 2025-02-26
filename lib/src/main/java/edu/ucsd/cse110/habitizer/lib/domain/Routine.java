package edu.ucsd.cse110.habitizer.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.habitizer.lib.util.SimpleSubject;

public class Routine {
    private final @Nullable Integer id;
    private @NonNull String title;
    private int sortOrder;
    private List<RoutineTask> tasks;

    private boolean isInProgress;
    private boolean isDone;

    private String routineElapsedTime;
    private String taskElapsedTime;
    private String goalTime;

    public Routine(@Nullable int id, @NonNull String title, int sortOrder, boolean isInProgress, boolean isdone,
                   String routineElapsedTime, String taskElapsedTime, String goalTime) {
        if (title.isBlank()) {
            throw new IllegalArgumentException("Routine title cannot be blank");
        }
        this.id = id;
        this.title = title;
        this.sortOrder = sortOrder;
        this.isInProgress = isInProgress;
        this.isDone = isdone;

        this.routineElapsedTime = routineElapsedTime;
        this.taskElapsedTime = taskElapsedTime;
        this.goalTime = goalTime;

        this.tasks = new ArrayList<>();
    }

    public void setTasks(List<RoutineTask> tasks) {
        for (var task: tasks) {
            this.tasks.add(task);
        }
    }

    public void setGoalTime(String goalTime) {
        this.goalTime = goalTime;
    }

    public int id() { return id; }
    public String title() { return title; }
    public int sortOrder() { return sortOrder; }
    public boolean isInProgress() { return isInProgress; }
    public boolean isDone() { return isDone; }
    public String routineElapsedTime() { return routineElapsedTime; }
    public String taskElapsedTime() { return taskElapsedTime; }
    public String goalTime() { return goalTime; }
    public List<RoutineTask> tasks() { return List.copyOf(tasks); }

    public void addTask(RoutineTask task) {
        tasks.add(task);
    }

    public void setElapsedTime(String routineElapsedTime, String taskElapsedTime) {
        this.routineElapsedTime = routineElapsedTime;
        this.taskElapsedTime = taskElapsedTime;
    }
}
