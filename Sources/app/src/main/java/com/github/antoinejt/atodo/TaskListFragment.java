package com.github.antoinejt.atodo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.github.antoinejt.atodo.dataclasses.TaskItem;
import com.github.antoinejt.exassert.Preconditions;
import com.github.antoinejt.atodo.DBUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskListFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.task_list);
        DBUtils db = new DBUtils(this.getContext());
        TaskListAdapter adapter = new TaskListAdapter(db.getTasks());
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);

        /*
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
         */
    }

    public List<TaskItem> genDummyTasks(int count) {
        Preconditions.requiresStrictlyPositive(count);

        List<TaskItem> result = new ArrayList<>();
        for (int i = 1; i <= count; ++i) {
            TaskItem item = new TaskItem("Tâche n°" + i, "Grosse description omg");
            result.add(item);
        }
        return result;
    }
}