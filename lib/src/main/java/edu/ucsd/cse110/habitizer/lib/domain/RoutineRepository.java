package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.List;

public interface RoutineRepository {
    // return a List of Routine
    List<Routine> getRoutineList();

    // return a List of RoutineTask
    List<RoutineTask> getTaskList(String name);

    RoutineTask getTaskWithIdandName(String name, int id);

    // Add a new task to a routine
    void addTaskToRoutine(String routineName, String taskName);
}
