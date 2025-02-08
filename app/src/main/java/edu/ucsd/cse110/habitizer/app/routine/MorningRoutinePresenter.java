package edu.ucsd.cse110.habitizer.app.routine;

import java.util.List;

import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineTask;

public class MorningRoutinePresenter implements RoutineContract.Presenter {

    private final RoutineContract.View view;
    private final RoutineRepository routineRepository;

    public MorningRoutinePresenter(RoutineContract.View view, RoutineRepository routineRepository) {
        this.view = view;
        this.routineRepository = routineRepository;
    }

    @Override
    public void start() {
        loadTasks();
    }

    @Override
    public void loadTasks() {
        Routine routine = routineRepository.routine("Test Routine");
        if (routine != null) {
            List<RoutineTask> tasksList = routine.tasks();
            view.displayTasks(tasksList);
        }
    }
}

