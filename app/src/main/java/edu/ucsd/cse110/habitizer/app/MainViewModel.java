package edu.ucsd.cse110.habitizer.app;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.List;

import edu.ucsd.cse110.habitizer.lib.domain.ElapsedTimer;
import edu.ucsd.cse110.habitizer.lib.domain.MockElapsedTimer;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineTask;
import edu.ucsd.cse110.habitizer.lib.util.Subject;

public class MainViewModel extends ViewModel {
    private final RoutineRepository routineRepository;
    private final Subject<List<RoutineTask>> taskList;
    private final Subject<Integer> currentTaskId;
    private final Subject<Boolean> isRoutineDone;
    private final Subject<String> taskElapsedTime;
    private Runnable currentRunner;

    // ElapsedTimer instance to track routine elapsed time
    private final ElapsedTimer timer;

    // Subject to store and update elapsed time dynamically (Lab 5)
    private final Subject<String> elapsedTime;

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
        this.currentTaskId = new Subject<>();
        this.isRoutineDone = new Subject<>();
        this.taskElapsedTime = new Subject<>();
        this.timer = MockElapsedTimer.immediateTimer(); // Initialize MockElapsedTimer for testing
        this.elapsedTime = new Subject<>();  // Initialize elapsed time tracking

        // Set initial values
        taskList.setValue(routineRepository.getTaskList());
        currentTaskId.setValue(0);
        isRoutineDone.setValue(false);

        taskElapsedTime.setValue("00:00");
        elapsedTime.setValue("00:00"); // Default to 0 time

        // Start updating elapsed time periodically
        startTimerUpdates();

        var task = routineRepository.getTaskWithId(0);
        task.start();
        currentRunner = startTaskTimerUpdates(0);
    }

    public Subject<List<RoutineTask>> loadTaskList() {
        return taskList;
    }

    public void checkOffTask(int id) {
        // Given id, find corresponding task and check it off
        timerHandler.removeCallbacks(currentRunner);
        var task = routineRepository.getTaskWithId(id);
        task.checkOff();
        // Increment current task id by 1.
        int nextTaskId = currentTaskId.getValue() + 1;

        var nextTask = routineRepository.getTaskWithId(nextTaskId);
        if (nextTask == null) {
            isRoutineDone.setValue(true);
        } else {
            nextTask.start();
            currentRunner = startTaskTimerUpdates(nextTaskId);
            Log.d("Success", getTaskElapsedTime().getValue());
        }

        currentTaskId.setValue(nextTaskId);
        taskList.setValue(routineRepository.getTaskList());
    }

    public ElapsedTimer getTimer() {
        return this.timer;
    }

    // Expose elapsed time for UI updates
    public Subject<String> getElapsedTime() {
        return elapsedTime;
    }

    public Subject<String> getTaskElapsedTime() {
        return taskElapsedTime;
    }

    public Subject<Integer> getCurrentTaskId() {
        return currentTaskId;
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

    public Runnable startTaskTimerUpdates(int id) {
        var task = routineRepository.getTaskWithId(id);
        var runner = new Runnable() {
            @Override
            public void run() {
                taskElapsedTime.setValue(task.getTime());
                timerHandler.postDelayed(this, TIMER_INTERVAL_MS);
            }
        };
        boolean success = timerHandler.postDelayed(runner, 0);
        return runner;
    }

    // Helper function to update elapsed time for UI
    private void updateElapsedTime() {
        elapsedTime.setValue(timer.getTime());
    }
}
