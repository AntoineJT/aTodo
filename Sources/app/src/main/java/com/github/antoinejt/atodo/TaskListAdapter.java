package com.github.antoinejt.atodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.antoinejt.atodo.activities.MainActivity;
import com.github.antoinejt.atodo.activities.EditTaskActivity;
import com.github.antoinejt.atodo.dataclasses.TaskItem;
import com.github.antoinejt.atodo.utils.Goto;
import com.github.antoinejt.exassert.Preconditions;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {
    private final List<TaskItem> taskList;

    public TaskListAdapter(List<TaskItem> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Preconditions.requiresUnsigned(position);
        TaskItem item = taskList.get(position);

        holder.getTitle().setText(item.title);
        holder.getDescription().setText(item.description);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(view ->
                    Goto.changeActivity(MainActivity.getInstance(), EditTaskActivity.class));
        }

        protected TextView getTitle() {
            return itemView.findViewById(R.id.task_title);
        }

        protected TextView getDescription() {
            return itemView.findViewById(R.id.task_desc);
        }
    }
}
