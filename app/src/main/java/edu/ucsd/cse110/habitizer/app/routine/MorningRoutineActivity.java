package edu.ucsd.cse110.habitizer.app.routine;



import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import edu.ucsd.cse110.habitizer.app.R;

import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineTask;

public class MorningRoutineActivity extends AppCompatActivity implements RoutineContract.View {

    private RoutineContract.Presenter presenter;
    private TextView tasksDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morning_routine);

        // This matches the TextView in activity_morning_routine.xml
        tasksDisplay = findViewById(R.id.tasks_display);

        // Initialize data + repository US1-1 classes
        InMemoryDataSource dataSource = InMemoryDataSource.fromDefault();
        RoutineRepository routineRepository = new RoutineRepository(dataSource);


        presenter = new MorningRoutinePresenter(this, routineRepository);
        presenter.start();
    }

    @Override
    public void displayTasks(List<RoutineTask> tasks) {
        StringBuilder tasksText = new StringBuilder();

        for (RoutineTask task : tasks) {
            tasksText.append("Title: ").append(task.title())
                    .append(", Priority: ").append(task.priority())
                    .append(", Completed: ").append(task.isChecked() ? "Yes" : "No")
                    .append("\n");
        }

        tasksDisplay.setText(tasksText.toString());
    }
}
