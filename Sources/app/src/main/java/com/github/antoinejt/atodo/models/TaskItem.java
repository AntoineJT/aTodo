package com.github.antoinejt.atodo.models;

import java.io.Serializable;
import java.util.Date;

public class TaskItem implements Serializable {
    public final long id;
    public final String name;
    public final String description;
    public final Date createdAt;
    public final Date deadline;
    public final boolean isFinished;

    public TaskItem(long id, String title, String description, Date createdAt, Date deadline, boolean isFinished) {
        this.id = id;
        this.name = title;
        this.description = description;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.isFinished = isFinished;
    }
}
