package edu.ucsd.cse110.habitizer.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.habitizer.lib.util.Subject;

public class SimpleRoutineRepository implements RoutineRepository {
    private final InMemoryDataSource dataSource;

    public SimpleRoutineRepository(InMemoryDataSource dataSource) {
        this.dataSource = dataSource;
    }

    // return a List of Routine
    @Override
    public Subject<List<Routine>> getRoutineList() {
        return dataSource.getRoutineListSubjects();
    }

    // return a List of RoutineTask
    @Override
    public Subject<Routine> getRoutineWithId(int routineId) {
        return dataSource.getRoutineWithIdSubject(routineId);
    }

    @Override
    public Subject<Routine> getInProgressRoutine() {
        return dataSource.getInProgressRoutineSubject();
    }

    @Override
    public Subject<RoutineTask> getTaskWithId(int id, int routineId) {
        return dataSource.getTaskWithIdSubject(id, routineId);
    }

    @Override
    public Subject<List<RoutineTask>> getTaskList(int routineId) {
        return dataSource.getTaskListSubjects(routineId);
    }

    @Override
    public void updateInProgressRoutine(int newRoutineId, boolean newInProgress) {
        dataSource.updateInProgressRoutine(newRoutineId, newInProgress);
    }

    @Override
    public void addTaskToRoutine(int routineId, RoutineTask task) {
        dataSource.addTaskToRoutine(routineId, task);
    }

    @Override
    public void checkOffTask(int id, int routineId) {
        dataSource.checkOffTask(id, routineId);
    }

    @Override
    public boolean getIsTaskChecked(int id, int routineId) {
        return dataSource.getIsTaskChecked(id, routineId);
    }

    @Override
    public void updateTaskTitle(int id, int routineId, String newTitle) {
        dataSource.updateTaskTitle(id, routineId, newTitle);
    }

    @Override
    public void updateTime(int routineId, String routineElapsedTime, String taskElapsedTIme) {
        dataSource.updateTime(routineId, routineElapsedTime, taskElapsedTIme);
    }

    @Override
    public void updateGoalTime(int routineId, String newTime) {
        dataSource.updateGoalTime(routineId, newTime);
    }

    @Override
    public void updateIsDone(int routineId, boolean newIsDone) {
        dataSource.updateIsDone(routineId, newIsDone);
    }

    @Override
    public void initializeStates(int routineId) {
        dataSource.initializeStates(routineId);
    }
}

