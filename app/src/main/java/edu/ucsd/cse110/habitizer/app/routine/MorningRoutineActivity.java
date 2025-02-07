package edu.ucsd.cse110.habitizer.app.routine;
import edu.ucsd.cse110.habitizer.app.R;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;



public class MorningRoutineActivity extends AppCompatActivity implements RoutineContract.View {

    private RoutineContract.Presenter presenter;
    private TextView tasksDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morning_routine);

        tasksDisplay = findViewById(R.id.tasks_display);
        List<String> initialTasks = new ArrayList<>();
        initialTasks.add("Exercise");
        initialTasks.add("Meditation");
        initialTasks.add("Reading");

        presenter = new MorningRoutinePresenter(this, initialTasks);
        presenter.start();
    }

    @Override
    public void displayTasks(String[] tasks) {
        StringBuilder tasksText = new StringBuilder();
        for (String task : tasks) {
            tasksText.append(task).append("\n");
        }
        tasksDisplay.setText(tasksText.toString());
    }
}
