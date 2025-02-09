package edu.ucsd.cse110.habitizer.app;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineTask;
import edu.ucsd.cse110.habitizer.lib.util.Subject;

public class MainViewModel extends ViewModel {
    private final RoutineRepository routineRepository;

    private final Subject<List<RoutineTask>> taskList;

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
        this.taskList = new Subject<>();

        // set initial task list here
        taskList.setValue(routineRepository.getTaskList());
    }

    public Subject<List<RoutineTask>> loadTaskList() {
        return taskList;
    }

    public void checkOffTask(int id) {
        Log.d("Task", "checkoff");
//        var task = routineRepository.getTaskWithId(id);
//        task.checkOff();
    }
}
