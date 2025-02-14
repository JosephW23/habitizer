package edu.ucsd.cse110.habitizer.app.ui.editlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.habitizer.app.databinding.FragmentRoutineEditListBinding;
import edu.ucsd.cse110.habitizer.app.databinding.FragmentTaskListBinding;
import edu.ucsd.cse110.habitizer.app.ui.routinelist.RoutineListAdapter;
import edu.ucsd.cse110.habitizer.app.ui.tasklist.TaskListAdapter;
import edu.ucsd.cse110.habitizer.app.ui.tasklist.TaskListFragment;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.habitizer.app.MainViewModel;
import edu.ucsd.cse110.habitizer.app.databinding.FragmentRoutineListBinding;

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
            // when a change is detected by observer
            // this will clear all contents in the adapter
            // and then get repopulate with new data
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
        return view.getRoot();

    }
}
