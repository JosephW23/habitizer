package edu.ucsd.cse110.habitizer.app.ui.editlist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.AlertDialog;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.habitizer.app.R;
import edu.ucsd.cse110.habitizer.app.databinding.FragmentRoutineEditListBinding;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.habitizer.app.MainViewModel;
import edu.ucsd.cse110.habitizer.app.ui.routinelist.RoutineListFragment;

public class EditListFragment extends Fragment {
    private MainViewModel activityModel;
    private EditListAdapter adapter; // adapter for ListView
    private FragmentRoutineEditListBinding view;

    public EditListFragment() {}

    public static EditListFragment newInstance() {
        EditListFragment fragment = new EditListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstance) {
        super.onCreate(savedInstance);

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);

        this.adapter = new EditListAdapter(requireContext(), List.of(), activityModel);

        activityModel.loadTaskList().observe(tasks -> {
            Log.d("Current Task", String.valueOf(tasks == null));
            if (tasks == null) return;

            adapter.clear();
            adapter.addAll(new ArrayList<>(tasks));
            adapter.notifyDataSetChanged();
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = FragmentRoutineEditListBinding.inflate(inflater, container, false);
        view.routineList.setAdapter(adapter);

        activityModel.getCurrentRoutine().observe(routine -> {
            if (routine == null) return;
            view.routineText.setText(routine.title() + " Edit");
        });
      
        // Set up Add Task Button
        view.addTaskButton.setOnClickListener(v -> showAddTaskDialog());
        // Back Button functionality to navigate back
        view.backButton.setOnClickListener(v -> {
            activityModel.initializeRoutineState();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, RoutineListFragment.newInstance()) // Ensure this is the correct fragment
                    .addToBackStack(null) // Add this fragment to the back stack
                    .commit();
        });

        view.deleteRoutineButton.setOnClickListener(v -> showDeleteRoutineDialog());

        activityModel.loadTaskList().observe(tasks -> {
            if (tasks == null) return;

            Log.d("Current Task", String.valueOf(tasks.size()));

            adapter.clear();
            adapter.addAll(new ArrayList<>(tasks));
            adapter.notifyDataSetChanged();
        });

        return view.getRoot();
    }
  
    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add New Task");

        // Set up input field
        final EditText input = new EditText(requireContext());
        input.setHint("Enter task name");
        builder.setView(input);

        // Set up dialog buttons
        builder.setPositiveButton("Add", (dialog, which) -> {
            String taskName = input.getText().toString().trim();
            if (!taskName.isEmpty()) {
                activityModel.addRoutineTask(taskName);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    public void showDeleteRoutineDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Do you want to delete this routine?");

        builder.setPositiveButton("Delete", (dialog, which) -> {
            activityModel.deleteRoutine();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, RoutineListFragment.newInstance()) // Ensure this is the correct fragment
                    .addToBackStack(null) // Add this fragment to the back stack
                    .commit();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}

