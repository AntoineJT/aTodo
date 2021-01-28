package com.github.antoinejt.atodo.models;

import com.github.antoinejt.atodo.utils.DateFormatter;

import java.io.Serializable;
import java.util.Date;

public class TaskItem implements Serializable {
    private final long id;
    private final String name;
    private final String description;
    private final Date createdAt;
    private final Date deadline;
    private final boolean isFinished;

    public TaskItem(long id, String title, String description, Date createdAt, Date deadline, boolean isFinished) {
        this.id = id;
        this.name = title;
        this.description = description;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.isFinished = isFinished;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getFormattedDeadline() {
        return DateFormatter.format(deadline);
    }

    public boolean isFinished() {
        return isFinished;
    }
}
