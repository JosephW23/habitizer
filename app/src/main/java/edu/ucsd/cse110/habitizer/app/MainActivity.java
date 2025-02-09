package edu.ucsd.cse110.habitizer.app;

import android.os.Bundle;
import android.util.Log;

import edu.ucsd.cse110.habitizer.app.databinding.ActivityMainBinding;
import androidx.appcompat.app.AppCompatActivity;

import edu.ucsd.cse110.habitizer.app.ui.routinelist.RoutineListFragment;
import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;

import edu.ucsd.cse110.habitizer.app.ui.tasklist.TaskListFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding view;
    private boolean isShowingMorning = false;
    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Todo: we have to call this from MainViewModel based on onClick method.
    // This method swap fragments between routine list and task list.
    // This method would be called when the main page showing list of routines gets implemented
    public void swapFragments() {
        if (isShowingMorning) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, TaskListFragment.newInstance())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, RoutineListFragment.newInstance())
                    .commit();
        }
        isShowingMorning = !isShowingMorning;
    }
}