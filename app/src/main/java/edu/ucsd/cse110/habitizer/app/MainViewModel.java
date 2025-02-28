package edu.ucsd.cse110.habitizer.app;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.List;

import edu.ucsd.cse110.habitizer.app.ui.routinelist.RoutineListFragment;
import edu.ucsd.cse110.habitizer.lib.domain.ElapsedTimer;
import edu.ucsd.cse110.habitizer.lib.domain.MockElapsedTimer;
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

    private int routineId;
    private final ElapsedTimer routineTimer;
    private final ElapsedTimer taskTimer;


    // Subject to store and update goal time
    private final Handler timerHandler = new Handler(Looper.getMainLooper()); // Handler for periodic timer updates (Lab 4 )
  
    private static final long TIMER_INTERVAL_MS = 1000; // Updates every 5 seconds

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

        currentRoutine = new SimpleSubject<>();
        routineElapsedTime = new SimpleSubject<>();
        taskElapsedTime = new SimpleSubject<>();
        goalTime = new SimpleSubject<>();
        isRoutineDone = new SimpleSubject<>();

        routineId = -1;

        this.routineTimer = MockElapsedTimer.immediateTimer(); // Initialize MockElapsedTimer for testing
        this.taskTimer = MockElapsedTimer.immediateTimer(); // Initialize MockElapsedTimer for testing

        routineRepository.getRoutineList().observe(routines -> {
            if (routines == null) return;
            for (var routine : routines){
                if (routine.isInProgress()) {
                    currentRoutine.setValue(routine);
                }
            }
        });

        // when routineId changes, update taskList.
        currentRoutine.observe(routine -> {
            if (routine == null) return;
            routineId = routine.id();
            routineElapsedTime.setValue(routine.routineElapsedTime());
            taskElapsedTime.setValue(routine.taskElapsedTime());
            goalTime.setValue(routine.goalTime());
            isRoutineDone.setValue(routine.isDone());
        });
    }

    public void startRoutine() {
        // Start updating elapsed time periodically
        routineTimer.resetTimer();
        routineTimer.startTimer();

        // Start updating task elapsed time periodically
        taskTimer.resetTimer();
        taskTimer.startTimer();

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
            routineRepository.checkOffTask(id, routineId);
            if (routineRepository.checkRoutineDone(routineId)) {
                routineRepository.updateIsDone(routineId, true);
                isRoutineDone.setValue(true);
            }

            taskTimer.resetTimer();
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
        routineRepository.updateTime(routineId,
                routineTimer.getRoundedDownTime(), taskTimer.getRoundedDownTime());
    }

    public void updateGoalTime(String newTime) {
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
    }

    public void updateIsDone(boolean newIsDone) {
        routineRepository.updateIsDone(routineId, newIsDone);
        isRoutineDone.setValue(true);
    }

    // Stop timer
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
    }

    // Periodically update elapsed time every second
    private void startTimerUpdates() {
        timerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateTime();
                timerHandler.postDelayed(this, TIMER_INTERVAL_MS);
            }
        }, 0);
    }
}
