package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.List;

import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.habitizer.lib.util.Subject;

public class SimpleRoutineRepository implements RoutineRepository {
    private final InMemoryDataSource dataSource;

    public SimpleRoutineRepository(InMemoryDataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Subject<List<Routine>> findRoutineList() {
        return null;
    }

    @Override
    public List<RoutineTask> findTaskList(int routineId) {
        return null;
    }

    @Override
    public void saveRoutine(Routine routine) {

    }

    @Override
    public void saveRoutineTask(RoutineTask task) {

    }

    @Override
    public void saveRoutineList(List<Routine> routine) {

    }

    @Override
    public void saveRoutineTaskList(List<RoutineTask> task) {

    }
}

