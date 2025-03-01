package edu.ucsd.cse110.habitizer.app;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.List;
import java.util.Locale;

import edu.ucsd.cse110.habitizer.lib.domain.ElapsedTimer;
import edu.ucsd.cse110.habitizer.lib.domain.RegularTimer;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineTask;
import edu.ucsd.cse110.habitizer.lib.util.MutableSubject;
import edu.ucsd.cse110.habitizer.lib.util.SimpleSubject;
import edu.ucsd.cse110.habitizer.lib.util.Subject;

public class MainViewModel extends ViewModel {
    private final RoutineRepository routineRepository;

    private MutableSubject<Routine> currentRoutine;

    private MutableSubject<String> routineElapsedTime;
    private MutableSubject<String> taskElapsedTime;
    private MutableSubject<String> goalTime;
    private MutableSubject<Boolean> isRoutineDone;

    private boolean isFirstRun;
    private int routineId;
    private final ElapsedTimer routineTimer;
    private final ElapsedTimer taskTimer;
    private final Handler timerHandler = new Handler(Looper.getMainLooper());
    private static final long TIMER_INTERVAL_MS = 1000;

    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (HabitizerApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getRoutineRepository());
                    });

    public MainViewModel(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;

        currentRoutine = new SimpleSubject<>();
        routineElapsedTime = new SimpleSubject<>();
        taskElapsedTime = new SimpleSubject<>();
        goalTime = new SimpleSubject<>();
        isRoutineDone = new SimpleSubject<>();

        isFirstRun = true;
        routineId = -1;

        this.routineTimer = new RegularTimer();
        this.taskTimer = new RegularTimer();

        routineRepository.getRoutineList().observe(routines -> {
            if (routines == null) return;
            for (var routine : routines){
                if (routine.isInProgress()) {
                    currentRoutine.setValue(routine);
                }
            }
        });

        currentRoutine.observe(routine -> {
            if (routine == null) return;
            routineId = routine.id();
            isRoutineDone.setValue(routine.isDone());

            goalTime.setValue(String.valueOf(routine.goalTime()));
            routineElapsedTime.setValue(routineTimer.getRoundedDownTime());
            taskElapsedTime.setValue(taskTimer.getRoundedDownTime());
        });
    }

    public String getRoundedDownTime(int seconds) {
        int minutes = (seconds % 3600) / 60;

        if (minutes == 0) {
            return "-";
        }

        return String.format(Locale.getDefault(), "%01d", minutes);
    }

    public void startRoutine() {
        // Start updating elapsed time periodically
        routineTimer.resetTimer();
        // Start updating task elapsed time periodically
        taskTimer.resetTimer();
        startTimerUpdates();
    }

    public Subject<List<RoutineTask>> loadTaskList() {
        return routineRepository.getTaskList(routineId);
    }
    public Subject<List<Routine>> loadRoutineList() {
        return routineRepository.getRoutineList();
    }

    public MutableSubject<Routine> getCurrentRoutine() {
        return currentRoutine;
    }
    public boolean getIsFirstRun() {
        return isFirstRun;
    }

    public void setIsFirstRun() {
        isFirstRun = false;
    }

    public int getRoutineId() {
        return routineId;
    }

    // New Method: Add Task to Routine**
    public void addTaskToRoutine(String taskName) {
        var task = new RoutineTask(null, routineId, taskName, false, -1);
        routineRepository.addTask(task);
    }

    // check off a task with id
    public void checkOffTask(int id) {
        if (!routineRepository.getIsTaskChecked(id, routineId)) {
            Log.d("Task Timer", taskTimer.getRoundedDownTime());
            routineRepository.checkOffTask(id, routineId);
            if (routineRepository.checkRoutineDone(routineId)) {
                routineRepository.updateIsDone(routineId, true);
                isRoutineDone.setValue(true);
            }

            timerHandler.removeMessages(0);
            taskTimer.resetTimer();

            updateTime();

            taskTimer.startTimer();
            startTimerUpdates();
        }
    }

    // Get Timer object
    public ElapsedTimer getRoutineTimer() {
        return routineTimer;
    }
    public ElapsedTimer getTaskTimer() {
        return taskTimer;
    }

    // Expose elapsed time for UI updates
    public Subject<String> getRoutineElapsedTime() {
        return routineElapsedTime;
    }
    public Subject<String> getTaskElapsedTime() {
        return taskElapsedTime;
    }
    public Subject<String> getGoalTime() {
        return goalTime;
    }
    public MutableSubject<Boolean> getIsRoutineDone() {
        return isRoutineDone;
    }

    public void updateInProgressRoutine(int newRoutineId, boolean newInProgress) {
        routineRepository.updateInProgressRoutine(newRoutineId, newInProgress);
    }

    private void updateTime() {
        Log.d("Seconds", String.valueOf(taskTimer.getSeconds()));
        routineRepository.updateTime(routineId,
                routineTimer.getSeconds(), taskTimer.getSeconds());
    }

    public void updateGoalTime(int newTime) {
        routineRepository.updateGoalTime(routineId, newTime);
    }

    // Updates task name when in edit task dialog
    public void updateTaskName(int id, String newTitle) {
        routineRepository.updateTaskTitle(id, routineId, newTitle);
    }

    // initialize all tasks and routine state
    public void initializeRoutineState() {
        routineRepository.initializeRoutineState(routineId);
        routineId = -1;
        endRoutine();
    }

    public void updateIsDone(boolean newIsDone) {
        routineRepository.updateIsDone(routineId, newIsDone);
        isRoutineDone.setValue(true);
    }

    public void stopRoutineTimer() {
        routineTimer.stopTimer();
    }
    public void stopTaskTimer() {
        taskTimer.stopTimer();
    }

    // Manually advance routine timer by 30 seconds (For US3c)
    public void advanceRoutineTimer() {
        routineTimer.advanceTimer();
        updateTime();
    }
    public void advanceTaskTimer() {
        taskTimer.advanceTimer();
        updateTime();
    }

    // stop two timers when finishing routine
    public void endRoutine() {
        stopRoutineTimer();
        stopTaskTimer();
        timerHandler.removeMessages(0);
    }

    // Periodically update elapsed time every second
    public void startTimerUpdates() {
        timerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateTime();
                timerHandler.postDelayed(this, TIMER_INTERVAL_MS);
            }
        }, 0);
    }
}
