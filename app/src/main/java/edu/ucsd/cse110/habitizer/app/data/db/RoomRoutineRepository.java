package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.habitizer.app.util.LiveDataSubjectAdapter;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineTask;
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
        var entitiesLiveData = routineDao.findRoutineList();
        var routineLiveData = Transformations.map(entitiesLiveData, entities -> {
            return entities.stream()
                    .map(RoutineEntity::toRoutine)
                    .collect(Collectors.toList());
        });
        return new LiveDataSubjectAdapter<>(routineLiveData);
    }

    // return a Routine with id
    public Subject<Routine> getRoutineWithId(int routineId) {
        var entityLiveData = routineDao.findRoutineWithId(routineId);
        var routineLiveData = Transformations.map(entityLiveData, RoutineEntity::toRoutine);
        return new LiveDataSubjectAdapter<>(routineLiveData);

    }

    // return the in-progess Routine
    public Subject<Routine> getInProgressRoutine() {
        var entityLiveData = routineDao.findInProgressRoutine();
        var routineLiveData = Transformations.map(entityLiveData, RoutineEntity::toRoutine);
        return new LiveDataSubjectAdapter<>(routineLiveData);
    }

    // return a List of RoutineTask
    public Subject<List<RoutineTask>> getTaskList(int routineId) {
        var entitiesLiveData = routineTaskDao.findTaskList(routineId);
        var routineLiveData = Transformations.map(entitiesLiveData, entities -> {
            return entities.stream()
                    .map(RoutineTaskEntity::toRoutineTask)
                    .collect(Collectors.toList());
        });
        return new LiveDataSubjectAdapter<>(routineLiveData);
    }

    public Subject<RoutineTask> getTaskWithId(int id, int routineId) {
        var entityLiveData = routineTaskDao.findTaskWithId(id, routineId);
        var routineLiveData = Transformations.map(entityLiveData, RoutineTaskEntity::toRoutineTask);
        return new LiveDataSubjectAdapter<>(routineLiveData);

    }

    public void updateInProgressRoutine(int routineId, boolean newInProgress) {
        routineDao.updateInProgressRoutine(routineId, newInProgress);
    }

    public void addTaskToRoutine(int routineId, RoutineTask task) {
        routineTaskDao.addTaskToRoutine(RoutineTaskEntity.fromRoutineTask(task));
    }

    public void checkRoutineDone(int routineId) {
        Routine routine = getRoutineWithId(routineId).getValue();
        boolean isDone = true;
        for (var task :routine.tasks()) {
            isDone = isDone && task.isChecked();
        }
        if (isDone) {
            updateIsDone(routineId, true);
        }
    }

    public void checkOffTask(int id, int routineId) {
        String taskElapsedTime = routineDao.getTaskElapsedTime(routineId);
        RoutineTask task = getTaskWithId(id, routineId).getValue();
        task.checkOff(taskElapsedTime);

        addTaskToRoutine(routineId, task);
        checkRoutineDone(routineId);
    }
    public boolean getIsTaskChecked(int id, int routineId) {
        return routineTaskDao.getIsTaskChecked(id, routineId);
    }

    public void updateTaskTitle(int id, int routineId, String newTitle) {
        routineTaskDao.updateInTaskTitle(id, routineId, newTitle);
    }

    public void updateTime(int routineId, String routineElapsedTime, String taskElapsedTime) {
        routineDao.updateTime(routineId, routineElapsedTime, taskElapsedTime);
    }

    public void updateGoalTime(int routineId, String newTime) {
        routineDao.updateGoalTime(routineId, newTime);
    }

    public void updateIsDone(int routineId, boolean newIsDone) {
        routineDao.updateIsDone(routineId, newIsDone);
    }

    public void initializeRoutineState(int routineId) {
        Routine routine = getRoutineWithId(routineId).getValue();
        var tasks = getTaskList(routineId).getValue();
        for (var task : tasks) {
            task.initialize();
        }
        routine.initialize();
        routine.setTasks(tasks);
        routineDao.addRoutine(RoutineEntity.fromRoutine(routine));
    }

}

