package edu.ucsd.cse110.habitizer.app.data.db;

import android.util.Log;

import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.habitizer.app.util.LiveDataSubjectAdapter;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineTask;
import edu.ucsd.cse110.habitizer.lib.util.SimpleSubject;
import edu.ucsd.cse110.habitizer.lib.util.Subject;


public class RoomRoutineRepository implements RoutineRepository {
    private final RoutineDao routineDao;
    private final RoutineTaskDao routineTaskDao;

    public RoomRoutineRepository(RoutineDao routineDao, RoutineTaskDao routineTaskDao) {
        this.routineDao = routineDao;
        this.routineTaskDao = routineTaskDao;
    }

    @Override
    public Subject<List<Routine>> getRoutineList() {
        if (routineDao.getRoutineCount() != 0) {
            var entitiesLiveData = routineDao.getRoutineList();
            var routineLiveData = Transformations.map(entitiesLiveData, entities -> {
                return entities.stream()
                        .map(RoutineEntity::toRoutine)
                        .collect(Collectors.toList());
            });
            return new LiveDataSubjectAdapter<>(routineLiveData);
        } else {
            return new SimpleSubject<List<Routine>>();
        }
    }

    // return a Routine with id
    public Subject<Routine> getRoutineWithId(int routineId) {
        var entityLiveData = routineDao.getRoutineWithId(routineId);
        var routineLiveData = Transformations.map(entityLiveData, RoutineEntity::toRoutine);
        return new LiveDataSubjectAdapter<>(routineLiveData);

    }

    // return the in-progess Routine
    public Subject<Routine> getInProgressRoutine() {
        if (routineDao.getInProgressCount() != 0) {
            var entityLiveData = routineDao.getInProgressRoutine();
            var routineLiveData = Transformations.map(entityLiveData, RoutineEntity::toRoutine);
            return new LiveDataSubjectAdapter<>(routineLiveData);
        } else {
            return new SimpleSubject<Routine>();
        }
    }

    // return a List of RoutineTask
    public Subject<List<RoutineTask>> getTaskList(int routineId) {
        var entitiesLiveData = routineTaskDao.getTaskList(routineId);
        var routineLiveData = Transformations.map(entitiesLiveData, entities -> {
            return entities.stream()
                    .map(RoutineTaskEntity::toRoutineTask)
                    .collect(Collectors.toList());
        });
        return new LiveDataSubjectAdapter<>(routineLiveData);
    }

    public Subject<RoutineTask> getTaskWithId(int id, int routineId) {
        var entityLiveData = routineTaskDao.getTaskWithId(id, routineId);
        var routineLiveData = Transformations.map(entityLiveData, RoutineTaskEntity::toRoutineTask);
        return new LiveDataSubjectAdapter<>(routineLiveData);

    }

    public void updateInProgressRoutine(int routineId, boolean newInProgress) {
        routineDao.updateInProgressRoutine(routineId, newInProgress);
    }

    public void addTask(RoutineTask task) {
        routineTaskDao.insert(RoutineTaskEntity.fromRoutineTask(task));
    }
    public void addTaskList(List<RoutineTask> tasks) {
        var newTasks = new ArrayList<RoutineTaskEntity>();
        for (var task : tasks) {
            newTasks.add(RoutineTaskEntity.fromRoutineTask(task));
        }
        routineTaskDao.insert(List.copyOf(newTasks));
    }
    public void deleteTask(int id, int routineId) {
        routineTaskDao.deleteTask(id, routineId);
    }
    public void clearTaskTable() {
        routineTaskDao.clearTaskTable();
    }

    public void addRoutine(Routine routine) {
        routineDao.insert(RoutineEntity.fromRoutine(routine));
    }
    public void addRoutineList(List<Routine> routines) {
        var newRoutines = new ArrayList<RoutineEntity>();
        for (var routine : routines) {
            newRoutines.add(RoutineEntity.fromRoutine(routine));
        }
        routineDao.insert(List.copyOf(newRoutines));
    }
    public void deleteRoutine(int routineId) {
        routineDao.deleteRoutine(routineId);
    }
    public void clearRoutineTable() {
        routineDao.clearRoutineTable();
    }

    public boolean checkRoutineDone(int routineId) {
        boolean isDone = true;
        for (var isChecked : routineTaskDao.checkIsRoutineDone(routineId)) {
            isDone = isDone && isChecked;
        }
        return isDone;
    }

    public void checkOffTask(int id, int routineId) {
        int taskElapsedTime = routineDao.getTaskElapsedTime(routineId);
        routineTaskDao.checkOffTask(id, routineId, taskElapsedTime);
    }
    public boolean getIsTaskChecked(int id, int routineId) {
        return routineTaskDao.getIsTaskChecked(id, routineId);
    }

    public void updateTaskTitle(int id, int routineId, String newTitle) {
        routineTaskDao.updateTaskTitle(id, routineId, newTitle);
    }

    public void updateRoutineTitle( int routineId, String newTitle) {
        routineDao.updateRoutineTitle(routineId, newTitle);
    }

    public void updateTime(int routineId, int routineElapsedTime, int taskElapsedTime) {
        routineDao.updateTime(routineId, routineElapsedTime, taskElapsedTime);
    }

    public void updateGoalTime(int routineId, int newTime) {
        routineDao.updateGoalTime(routineId, newTime);
    }

    public void updateIsDone(int routineId, boolean newIsDone) {
        routineDao.updateIsDone(routineId, newIsDone);
    }

    public void initializeRoutineState(int routineId) {
        routineDao.initializeRoutine(routineId);
        routineTaskDao.initializeTask(routineId);
    }

}

