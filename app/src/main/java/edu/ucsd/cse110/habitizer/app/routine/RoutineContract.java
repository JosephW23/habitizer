package edu.ucsd.cse110.habitizer.app.routine;

import java.util.List;

import edu.ucsd.cse110.habitizer.lib.domain.RoutineTask;

public interface RoutineContract {
    interface View {
        void displayTasks(List<RoutineTask> tasks);
    }
    interface Presenter {
        void start();
        void loadTasks();
    }
}
