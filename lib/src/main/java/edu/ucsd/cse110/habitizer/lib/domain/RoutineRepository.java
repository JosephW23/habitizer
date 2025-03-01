package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.List;

import edu.ucsd.cse110.habitizer.lib.util.Subject;

public interface RoutineRepository {
    Subject<List<Routine>> getRoutineList();
    Subject<Routine> getRoutineWithId(int routineId);
    Subject<Routine> getInProgressRoutine();
    Subject<Routine> getInEditRoutine();


    Subject<List<RoutineTask>> getTaskList(int routineId);
    Subject<RoutineTask> getTaskWithId(int id, int routineId);


    void addTask(RoutineTask task);
    void addTaskList(List<RoutineTask> tasks);
    void deleteTask(int id, int routineId);
    void clearTaskTable();


    void addRoutine(Routine routine);
    void addRoutineList(List<Routine> routines);
    void deleteRoutine(int routineId);
    void clearRoutineTable();


    void checkOffTask(int id, int routineId);
    boolean getIsTaskChecked(int id, int routineId);


    void updateTaskTitle(int id, int routineId, String newTitle);
    void updateRoutineTitle( int routineId, String newTitle);


    void updateTime(int routineId, int routineElapsedTime, int taskElapsedTime);
    void updateGoalTime(int routineId, int newTime);


    void updateInProgressRoutine(int routineId, boolean newInProgress);
    void updateInEditRoutine(int routineId, boolean newInEdit);
    void updateIsDone(int routineId, boolean newIsDone);
    void initializeRoutineState(int routineId);
    boolean checkRoutineDone(int routineId);

}
