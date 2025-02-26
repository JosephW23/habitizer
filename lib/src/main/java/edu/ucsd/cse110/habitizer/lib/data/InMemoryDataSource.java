package edu.ucsd.cse110.habitizer.lib.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.ucsd.cse110.habitizer.lib.domain.RegularTimer;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineTask;
import edu.ucsd.cse110.habitizer.lib.util.MutableSubject;
import edu.ucsd.cse110.habitizer.lib.util.SimpleSubject;
import edu.ucsd.cse110.habitizer.lib.util.Subject;

public class InMemoryDataSource {
    private List<Routine> routines;

    public InMemoryDataSource() {
    }

    public void initializeDefaultRoutine() {
        Routine DEFAULT_MORNING_ROUTINE = new Routine(0, "Morning", 0,
                true, false, "-", "-");
        DEFAULT_MORNING_ROUTINE.setTasks(List.of(
                new RoutineTask(0, 0, "Wake Up", false, 0),
                new RoutineTask(1, 0, "Eat Breakfast", false, 1),
                new RoutineTask(2, 0, "Brush Teeth", false, 2)
        ));

        Routine DEFAULT_EVENING_ROUTINE = new Routine(1, "Evening", 0,
                false, false, "-", "-");
        DEFAULT_EVENING_ROUTINE.setTasks(List.of(
                new RoutineTask(0, 1, "Eat Dinner", false, 0),
                new RoutineTask(1, 1, "Brush Teeth", false, 1),
                new RoutineTask(2, 1, "Go To Bed", false, 2)
        ));
        // Use ArrayList instead of `List.of()`, which is immutable.
       routines = List.of(DEFAULT_MORNING_ROUTINE, DEFAULT_EVENING_ROUTINE);
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

    public Routine getRoutine(int routineId) {
        for (var routine : routines) {
            if (routine.id() == routineId) {
                return routine;
            }
        }
        return null;
    }

    public Subject<Routine>  getRoutineSubjects(int routineId) {
        var subject = new SimpleSubject<Routine>();
        subject.setValue(getRoutine(routineId));
        return subject;
    }

    public List<RoutineTask> getTaskList(int routineId) {
        return getRoutine(routineId).tasks();
    }

    public Subject<List<RoutineTask>> getTaskListSubjects(int routineId) {
        var subject = new SimpleSubject<List<RoutineTask>>();
        subject.setValue(getTaskList(routineId));
        return subject;
    }

    public RoutineTask getTaskWithId(int id, int routineId) {
        for (var task: getRoutine(routineId).tasks()) {
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

    // Made updateRoutine()
    public void addTaskToRoutine(RoutineTask task) {
        Routine routine = getRoutine(task.routineId());
        if (routine != null) {
            // Generate a new task ID (increment from the last task)
            int newTaskId = routine.tasks().isEmpty() ? 0 : routine.tasks().size();
            task.setId(newTaskId);

            // temporarily set sortOrder same as id.
            int sortOrder = newTaskId;
            task.setSortOrder(sortOrder);

            routine.addTask(task);
        }
    }
}


