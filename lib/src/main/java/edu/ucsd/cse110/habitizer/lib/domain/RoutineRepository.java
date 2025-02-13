package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.List;

import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;

public class RoutineRepository {
    private final InMemoryDataSource dataSource;

    public RoutineRepository(InMemoryDataSource dataSource) { this.dataSource = dataSource; }

    public Routine routine(String title) { return dataSource.routine(title); }

    // return a List of RoutineTask
    public List<RoutineTask> getTaskList(String name) {
        return dataSource.getTaskList(name);
    }

    public RoutineTask getTaskWithIdandName(String name, int id) {
        for (var task : this.getTaskList(name)) {
            if (task.id() == id) {
                return task;
            }
        }
        return null;
    }
}
