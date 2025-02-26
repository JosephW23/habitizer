package edu.ucsd.cse110.habitizer.lib.domain;

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
    public Subject<List<RoutineTask>> getTaskList(int routineId) {
        return dataSource.getTaskListSubjects(routineId);
    }

    @Override
    public Subject<RoutineTask> getTaskWithId(int id, int routineId) {
        return dataSource.getTaskWithIdSubject(id, routineId);
    }

    // Add a new task to a routine
    @Override
    public void addTaskToRoutine(RoutineTask task) {
        Routine routine = dataSource.getRoutine(task.routineId());
        if (routine != null) {
            // Generate a new task ID (increment from the last task)
            int newTaskId = routine.tasks().isEmpty() ? 0 : routine.tasks().get(routine.tasks().size() - 1).id() + 1;

            // temporarily set sortOrder same as id.
            int sortOrder = newTaskId;
            RoutineTask newTask = new RoutineTask(newTaskId, taskName, false, sortOrder);

            // Create a new updated Routine instance
            routine.addTask(newTask);

            // Save the updated routine
            dataSource.updateRoutine(routine);
        }
    }
}

