package com.github.antoinejt.atodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.antoinejt.atodo.activities.MainActivity;
import com.github.antoinejt.atodo.activities.TaskActivity;
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
        holder.getShortDescription().setText(item.shortDescription);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener((View view) -> {
                Goto.changeActivity(MainActivity.getInstance(), TaskActivity.class);
            });
            // TextView title = getTitle();
            // TextView shortDescription = getShortDescription();
        }

        protected TextView getTitle() {
            return itemView.findViewById(R.id.task_title);
        }

        protected TextView getShortDescription() {
            return itemView.findViewById(R.id.task_short_desc);
        }
    }
}
