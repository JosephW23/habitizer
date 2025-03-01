package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.List;

import edu.ucsd.cse110.habitizer.lib.util.Subject;

public interface RoutineRepository {
    Subject<List<Routine>> findRoutineList();
    List<RoutineTask> findTaskList(int routineId);

    void saveRoutine(Routine routine);
    void saveRoutineTask(RoutineTask task);

    void saveRoutineList(List<Routine> routine);
    void saveRoutineTaskList(List<RoutineTask> task);
}
