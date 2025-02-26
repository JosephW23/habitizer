package edu.ucsd.cse110.habitizer.app;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    private MutableSubject<List<RoutineTask>> taskList;
    private MutableSubject<List<Routine>> routineList;
    private MutableSubject<Integer> routineId;

    private MutableSubject<String> routineElapsedTime;
    private MutableSubject<String> taskElapsedTime;

    private Routine currentRoutine;
    private final ElapsedTimer routineTimer;
    private final ElapsedTimer taskTimer;



    // Subject to store and update goal time
    private final Handler timerHandler = new Handler(Looper.getMainLooper()); // Handler for periodic timer updates (Lab 4 )
  
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

        routineId = new SimpleSubject<>();
        taskList = new SimpleSubject<>();
        routineList = new SimpleSubject<>();
        routineElapsedTime = new SimpleSubject<>();
        taskElapsedTime = new SimpleSubject<>();

        this.routineTimer = MockElapsedTimer.immediateTimer(); // Initialize MockElapsedTimer for testing
        this.taskTimer = MockElapsedTimer.immediateTimer(); // Initialize MockElapsedTimer for testing

        routineRepository.getRoutineList().observe(routines -> {
            if (routines == null) return;
            routineList.setValue(routines);
        });

        // when one of tasks is modified, update taskList.
        routineRepository.getTaskList(routineId.getValue()).observe(tasks -> {
            if (tasks == null) return;
            taskList.setValue(tasks);
        });

        // when the inProgressRoutine changes, update routineId and time
        routineRepository.getInProgressRoutine().observe(routine -> {
            if (routine == null) return;
            currentRoutine = routine;
            routineId.setValue(routine.id());
            routineElapsedTime.setValue(routine.routineElapsedTime());
            taskElapsedTime.setValue(routine.taskElapsedTime());
        });

        // when routineId changes, update taskList.
        routineId.observe(id -> {
            if (id == null) return;
            taskList.setValue(routineRepository.getTaskList(id).getValue());
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
        return taskList;
    }
    public Subject<List<Routine>> loadRoutineList() {
        return routineList;
    }

    // New Method: Add Task to Routine**
    public void addTaskToRoutine(String taskName) {
        var task = new RoutineTask(null, currentRoutine.id(), taskName, false, -1);
        routineRepository.addTaskToRoutine(task);
    }

    // check off a task with id
    public void checkOffTask(int id) {
        routineRepository.checkOffTask(id, currentRoutine.id());

        taskTimer.resetTimer();
        taskTimer.startTimer();
        startTimerUpdates();
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

    // Stop timer
    public void stopRoutineTimer() {
        routineTimer.stopTimer();
        updateTime();
    }
    public void stopTaskTimer() {
        taskTimer.stopTimer();
        updateTime();
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

    private void updateTime() {
        routineRepository.updateTime(currentRoutine.id(),
                routineTimer.getRoundedDownTime(), taskTimer.getRoundedDownTime());
    }

    public void updateGoalTime(String time) {
        System.out.println("Time received: " + time);
        goalTime.setValue(time);
    }

    public Subject<String> getGoalTime() {
        return goalTime;
    }

    // Updates task name when in edit task dialog
    public void updateTaskName(String newTitle, int id) {
        routineRepository.updateTaskTitle(id, currentRoutine.id(), newTitle);
    }

    // initialize all tasks
    public void initializeTasks(String routineName) {
        var taskList = routineRepository.getTaskList(routineName);
        for (var task : taskList) {
            task.initialize();
        }
        this.taskList.setValue(taskList);
        isRoutineDone.setValue(false);
        currentTaskId = 0;
    }
}
