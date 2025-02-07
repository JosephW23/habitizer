package edu.ucsd.cse110.habitizer.app.routine;

import java.util.List;

public class MorningRoutinePresenter implements RoutineContract.Presenter {

    private final RoutineContract.View view;
    private final List<String> tasks;

    public MorningRoutinePresenter(RoutineContract.View view, List<String> initialTasks) {
        this.view = view;
        this.tasks = initialTasks;
    }

    @Override
    public void start() {
        loadTasks();
    }

    @Override
    public void loadTasks() {
        String[] tasksArray = tasks.toArray(new String[0]);
        view.displayTasks(tasksArray);
    }
}
