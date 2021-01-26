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
import com.github.antoinejt.atodo.utils.DBUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.task_list);
        try (DBUtils db = new DBUtils(this.getContext())) {
            TaskListAdapter adapter = new TaskListAdapter(db.getTasks());
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TaskItem> genDummyTasks(int count) {
        Preconditions.requiresStrictlyPositive(count);

        List<TaskItem> result = new ArrayList<>();
        for (int i = 1; i <= count; ++i) {
            Date now = Date.from(Instant.now());
            TaskItem item = new TaskItem(i, "Tâche n°" + i, "Grosse description omg", now, now, true);
            result.add(item);
        }
        return result;
    }
}