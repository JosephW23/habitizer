package edu.ucsd.cse110.habitizer.app.routine;

import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;


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
            String[] tasksArray = routine.tasks()
                    .stream()
                    .map(task -> task.title())
                    .toArray(String[]::new);
            view.displayTasks(tasksArray);
        }
    }
}
