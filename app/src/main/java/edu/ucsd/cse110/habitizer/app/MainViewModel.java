package edu.ucsd.cse110.habitizer.app;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.habitizer.lib.domain.ElapsedTimer;
import edu.ucsd.cse110.habitizer.lib.domain.MockElapsedTimer;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineTask;
import edu.ucsd.cse110.habitizer.lib.util.Subject;

public class MainViewModel extends ViewModel {
    private final RoutineRepository routineRepository;
    private final Subject<List<RoutineTask>> taskList;

    // ElapsedTimer instance to track routine elapsed time
    private final ElapsedTimer timer;

    // Subject to store and update elapsed time dynamically (Lab 5)
    private final Subject<String> elapsedTime;
    // Subject to store and update goal time
    private final Subject<String> goalTime;

    // Handler for periodic timer updates (Lab 4 )
    private final Handler timerHandler = new Handler(Looper.getMainLooper());
    private static final long TIMER_INTERVAL_MS = 1000; // Updates every second

    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (HabitizerApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getRoutineRepository());
                    });

    // Initialize elapsed time tracking and timer in constructor
    public MainViewModel(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
        this.taskList = new Subject<>();
        this.timer = MockElapsedTimer.immediateTimer(); // Initialize MockElapsedTimer for testing
        this.elapsedTime = new Subject<>();  // Initialize elapsed time tracking
        this.goalTime = new Subject<>();

        // Set initial values
        goalTime.setValue("60");
        taskList.setValue(routineRepository.getTaskList());
        elapsedTime.setValue("00:00"); // Default to 0 time

        // Start updating elapsed time periodically
        startTimerUpdates();
    }

    public Subject<List<RoutineTask>> loadTaskList() {
        return taskList;
    }

    public void checkOffTask(int id) {
        // Given id, find corresponding task and check it off
        var task = routineRepository.getTaskWithId(id);
        task.checkOff();
        taskList.setValue(routineRepository.getTaskList());
    }

    public ElapsedTimer getTimer() {
        return this.timer;
    }

    // Expose elapsed time for UI updates
    public Subject<String> getElapsedTime() {
        return elapsedTime;
    }

    // Start routine timer and begin elapsed time tracking
    public void startRoutineTimer() {
        timer.startTimer();
        updateElapsedTime();
    }

    // Stop routine timer and reset elapsed time
    public void stopRoutineTimer() {
        timer.stopTimer();
        updateElapsedTime();
    }

    // Pause routine timer and freeze elapsed time updates
    public void pauseRoutineTimer() {
        timer.pauseTimer();
        updateElapsedTime();
    }

    // Resume routine timer and restart elapsed time updates
    public void resumeRoutineTimer() {
        timer.resumeTimer();
        updateElapsedTime();
    }

    // Manually advance routine timer by 30 seconds (For US3c)
    public void advanceRoutineTimer() {
        timer.advanceTimer();
        updateElapsedTime();
    }

    // Periodically update elapsed time every second
    private void startTimerUpdates() {
        timerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                elapsedTime.setValue(timer.getTime());
                timerHandler.postDelayed(this, TIMER_INTERVAL_MS);
            }
        }, TIMER_INTERVAL_MS);
    }

    // Helper function to update elapsed time for UI
    private void updateElapsedTime() {
        elapsedTime.setValue(timer.getTime());
    }

    public void updateGoalTime(String time) {
        Log.d("DEBUG","Update Goal Time");
        System.out.println("Time received: " + time);
        goalTime.setValue(time);
    }

    public Subject<String> getGoalTime() {
        Log.d("DEBUG","Getting Time");
        return goalTime;
    }

}
