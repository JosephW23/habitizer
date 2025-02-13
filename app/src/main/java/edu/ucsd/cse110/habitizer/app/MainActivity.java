package edu.ucsd.cse110.habitizer.app;

import android.os.Bundle;

import edu.ucsd.cse110.habitizer.app.databinding.ActivityMainBinding;
import androidx.appcompat.app.AppCompatActivity;

import edu.ucsd.cse110.habitizer.app.ui.tasklist.TaskListFragment;
import edu.ucsd.cse110.habitizer.app.ui.routinelist.RoutineListFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding view;
    private boolean isShowingRoutine = true;
    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}