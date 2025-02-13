package edu.ucsd.cse110.habitizer.app.ui.tasklist;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.ucsd.cse110.habitizer.app.MainViewModel;
import edu.ucsd.cse110.habitizer.app.databinding.ListItemTaskBinding;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineTask;

public class TaskListAdapter extends ArrayAdapter<RoutineTask> {
    Consumer<Integer> onCheckOffClick; // this variable tracks when a task is clicked
    private MainViewModel activityModel;
    public TaskListAdapter(Context context, List<RoutineTask> tasks, Consumer<Integer> onCheckOffClick, MainViewModel activityModel) {
        super(context, 0, new ArrayList<>(tasks));
        this.onCheckOffClick = onCheckOffClick;
        this.activityModel = activityModel;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        var task = getItem(position);
        assert task != null;

        ListItemTaskBinding binding;
        if (convertView != null) {
            binding = ListItemTaskBinding.bind(convertView);
        } else {
            var layoutInflater = LayoutInflater.from(getContext());
            binding = ListItemTaskBinding.inflate(layoutInflater, parent, false);
        }

        binding.taskFrontText.setText(task.title());

        // This if-else block updates opacity of checkmark in UI
        // so that checkmark appears when clicked
        if (task.isChecked()) {
            binding.taskCheck.setAlpha(255);
        } else {
            binding.taskCheck.setAlpha(0);
        }

        // When task is clicked, the hidden button is clicked
        // and the events for checking off task are called
        binding.taskButton.setOnClickListener(v -> {
            var id = task.id();
            activityModel.checkOffTask(id);
        });

        activityModel.getTaskElapsedTime().observe(time -> {
            if (task.id() == activityModel.getCurrentTaskId()) {
                binding.taskTime.setText(time); // Updates UI dynamically
            } else {
                binding.taskTime.setText(task.getElapsedTime());
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

        var id = task.id();
        assert id != null;

        return id;
    }
}