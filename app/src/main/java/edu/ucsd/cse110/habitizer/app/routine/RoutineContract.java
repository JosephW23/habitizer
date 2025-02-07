package edu.ucsd.cse110.habitizer.app.routine;

public interface RoutineContract {
    interface View {
        void displayTasks(String[] tasks );
    }
    interface Presenter{
        void start();
        void loadTasks();
    }
}
