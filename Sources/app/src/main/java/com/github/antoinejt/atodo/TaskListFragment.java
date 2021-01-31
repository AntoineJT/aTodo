package com.github.antoinejt.atodo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.github.antoinejt.atodo.models.TaskItem;
import com.github.antoinejt.exassert.Preconditions;
import com.github.antoinejt.atodo.utils.DatabaseHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskListFragment extends Fragment {
    private static boolean hideUnfinishedTasks = false;

    public static boolean isHidingUnfinishedTasks() {
        return hideUnfinishedTasks;
    }

    public static void setHideUnfinishedTasks(boolean hideUnfinishedTasks) {
        TaskListFragment.hideUnfinishedTasks = hideUnfinishedTasks;
    }

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
        try (DatabaseHandler db = DatabaseHandler.get(this.getContext())) {
            final List<TaskItem> items = hideUnfinishedTasks
                    ? db.getUnfinishedTasks() : db.getTasks();
            final TaskListAdapter adapter = new TaskListAdapter(items);
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