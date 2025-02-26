package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.List;

import edu.ucsd.cse110.habitizer.lib.util.Subject;

public interface RoutineRepository {
    // return a List of Routine
    Subject<List<Routine>> getRoutineList();

    // return a List of RoutineTask
    Subject<List<RoutineTask>> getTaskList(int routineId);

    Subject<RoutineTask> getTaskWithId(int id, int routineId);

    // Add a new task to a routine
    void addTaskToRoutine(RoutineTask task);
}
