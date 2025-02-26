package edu.ucsd.cse110.habitizer.lib.data;

import java.util.List;

import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineTask;
import edu.ucsd.cse110.habitizer.lib.util.MutableSubject;
import edu.ucsd.cse110.habitizer.lib.util.SimpleSubject;
import edu.ucsd.cse110.habitizer.lib.util.Subject;

public class InMemoryDataSource {
    private List<Routine> routines;
    private MutableSubject<List<Routine>> routineSubjects;

    public InMemoryDataSource() {
    }

    public void initializeDefaultRoutine() {
        Routine DEFAULT_MORNING_ROUTINE = new Routine(0, "Morning", 0,
                true, false, "-", "-", "60");
        DEFAULT_MORNING_ROUTINE.setTasks(List.of(
                new RoutineTask(0, 0, "Wake Up", false, 0),
                new RoutineTask(1, 0, "Eat Breakfast", false, 1),
                new RoutineTask(2, 0, "Brush Teeth", false, 2)
        ));

        Routine DEFAULT_EVENING_ROUTINE = new Routine(1, "Evening", 0,
                false, false, "-", "-", "60");
        DEFAULT_EVENING_ROUTINE.setTasks(List.of(
                new RoutineTask(0, 1, "Eat Dinner", false, 0),
                new RoutineTask(1, 1, "Brush Teeth", false, 1),
                new RoutineTask(2, 1, "Go To Bed", false, 2)
        ));
        // Use ArrayList instead of `List.of()`, which is immutable.
       routines = List.of(DEFAULT_MORNING_ROUTINE, DEFAULT_EVENING_ROUTINE);
       routineSubjects.setValue(routines);
    }
    public static InMemoryDataSource fromDefault() {
        var data = new InMemoryDataSource();
        data.initializeDefaultRoutine();
        return data;
    }

    public List<Routine> getRoutineList() {
        return routines;
    }

    public Subject<List<Routine>> getRoutineListSubjects() {
        var subject = new SimpleSubject<List<Routine>>();
        subject.setValue(getRoutineList());
        return subject;
    }

    public Routine getRoutineWithId(int routineId) {
        for (var routine : routines) {
            if (routine.id() == routineId) {
                return routine;
            }
        }
        return null;
    }

    public Subject<Routine> getRoutineWithIdSubject(int routineId) {
        var subject = new SimpleSubject<Routine>();
        subject.setValue(getRoutineWithId(routineId));
        return subject;
    }

    public List<RoutineTask> getTaskList(int routineId) {
        return getRoutineWithId(routineId).tasks();
    }

    public Subject<List<RoutineTask>> getTaskListSubjects(int routineId) {
        var subject = new SimpleSubject<List<RoutineTask>>();
        subject.setValue(getTaskList(routineId));
        return subject;
    }

    public RoutineTask getTaskWithId(int id, int routineId) {
        for (var task: getRoutineWithId(routineId).tasks()) {
            if (task.id() == id) {
                return task;
            }
        }
        return null;
    }

    public Subject<RoutineTask> getTaskWithIdSubject(int id, int routineId) {
        var subject = new SimpleSubject<RoutineTask>();
        subject.setValue(getTaskWithId(id, routineId));
        return subject;
    }

    public Routine getInProgressRoutine() {
        for (var routine: getRoutineList()) {
            if (routine.isInProgress()) {
                return routine;
            }
        }
        return null;
    }

    public Subject<Routine> getInProgressRoutineSubject() {
        var subject = new SimpleSubject<Routine>();
        subject.setValue(getInProgressRoutine());
        return subject;
    }

    public void putRoutine(Routine newRoutine) {
        List<Routine> newRoutines = List.of();
        for (var routine: routines) {
            if (routine.id() == newRoutine.id()) {
                newRoutines.add(newRoutine);
            } else {
                newRoutines.add(routine);
            }
        }
        routines = List.copyOf(newRoutines);
        routineSubjects.setValue(routines);
    }

    public void putTask(Routine newRoutine, RoutineTask newTask) {
        List<RoutineTask> newTasks = List.of();
        for (var task: newRoutine.tasks()) {
            if (task.id() == newTask.id()) {
                newTasks.add(newTask);
            } else {
                newTasks.add(task);
            }
        }
        newRoutine.setTasks(newTasks);
        putRoutine(newRoutine);
    }

    // Made updateRoutine()
    public void addTaskToRoutine(int routineId, RoutineTask task) {
        Routine newRoutine = getRoutineWithId(routineId);
        if (newRoutine != null) {
            // Generate a new task ID (increment from the last task)
            int newTaskId = newRoutine.tasks().isEmpty() ? 0 : newRoutine.tasks().size();
            task.setId(newTaskId);

            // temporarily set sortOrder same as id.
            int sortOrder = newTaskId;
            task.setSortOrder(sortOrder);

            newRoutine.addTask(task);
            putRoutine(newRoutine);
        }
    }

    public void checkOffTask(int id, int routineId) {
        Routine routine = getRoutineWithId(routineId);
        RoutineTask task = getTaskWithId(id, routineId);
        task.checkOff(routine.taskElapsedTime());
        putTask(routine, task);
    }

    public void updateTaskTitle(int id, int routineId, String newTitle) {
        Routine routine = getRoutineWithId(routineId);
        RoutineTask task = getTaskWithId(id, routineId);
        if (task != null){
            task.setTitle(newTitle);
            putTask(routine, task);
        }
    }

    public void updateTime(int routineId, String routineElapsedTime, String taskElapsedTime) {
        Routine routine = getRoutineWithId(routineId);
        routine.setElapsedTime(routineElapsedTime, taskElapsedTime);
        putRoutine(routine);
    }

    public void updateGoalTime(int routineId, String newTime) {
        Routine routine = getRoutineWithId(routineId);
        routine.setGoalTime(newTime);
        putRoutine(routine);
    }

    public void initializeTasks(int routineId) {
        Routine routine = getRoutineWithId(routineId);
        var tasks = getTaskList(routineId);
        for (var task : tasks) {
            task.initialize();
        }
        routine.setTasks(tasks);
        putRoutine(routine);
    }
}


