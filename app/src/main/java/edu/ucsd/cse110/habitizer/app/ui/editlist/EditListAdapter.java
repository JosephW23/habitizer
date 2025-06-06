package edu.ucsd.cse110.habitizer.app.ui.editlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.ucsd.cse110.habitizer.app.MainViewModel;
import edu.ucsd.cse110.habitizer.app.databinding.ListItemEditTaskBinding;
import edu.ucsd.cse110.habitizer.app.ui.editlist.dialog.EditTaskNameDialogFragment;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineTask;

public class EditListAdapter extends ArrayAdapter<RoutineTask> {
    private final MainViewModel activityModel;
    public EditListAdapter(Context context, List<RoutineTask> tasks, MainViewModel activityModel) {
        super(context, 0, new ArrayList<>(tasks));
        this.activityModel = activityModel;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        var task = getItem(position);
        assert task != null;

        ListItemEditTaskBinding binding;
        if (convertView != null) {
            binding = ListItemEditTaskBinding.bind(convertView);
        } else {
            var layoutInflater = LayoutInflater.from(getContext());
            binding = ListItemEditTaskBinding.inflate(layoutInflater, parent, false);
        }

        // Set a tile of a task.
        binding.taskFrontText.setText(task.title());

        binding.renameTaskButton.setOnClickListener(v -> {
            EditTaskNameDialogFragment dialog = EditTaskNameDialogFragment.newInstance(task.id(), task.title());
            dialog.show(((FragmentActivity) getContext()).getSupportFragmentManager(), "EditTaskNameDialogFragment");
        });

        binding.deleteTaskButton.setOnClickListener(v -> {
            activityModel.removeTask(task);
        });

        binding.buttonMoveUp.setOnClickListener(v -> {
            var currentRoutine = activityModel.getCurrentRoutine().getValue();
            if (currentRoutine != null && currentRoutine.isInEdit()) {
                int currentPosition = this.getPosition(task);
                if (currentPosition > 0) {
                    List<RoutineTask> tasksList = new ArrayList<>(activityModel.loadTaskList().getValue());
                    Collections.swap(tasksList, currentPosition, currentPosition - 1);
                    activityModel.updateTaskOrder(tasksList);
                }
            }
        });

        binding.buttonMoveDown.setOnClickListener(v -> {
            var currentRoutine = activityModel.getCurrentRoutine().getValue();
            if (currentRoutine != null && currentRoutine.isInEdit()) {
                int currentPosition = this.getPosition(task);
                List<RoutineTask> tasksList = new ArrayList<>(activityModel.loadTaskList().getValue());
                if (currentPosition < tasksList.size() - 1) {
                    Collections.swap(tasksList, currentPosition, currentPosition + 1);
                    activityModel.updateTaskOrder(tasksList);
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public long getItemId(int position) {
        var task = getItem(position);
        assert task != null;
        return task.id();
    }
}