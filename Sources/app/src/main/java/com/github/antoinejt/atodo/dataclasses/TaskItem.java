package com.github.antoinejt.atodo.dataclasses;

import java.util.Date;

public class TaskItem {
    public final long id;
    public final String title;
    public final String description;
    public final Date createdAt;
    public final Date deadline;
    public final boolean isFinished;

    public TaskItem(long id, String title, String description, Date createdAt, Date deadline, boolean isFinished) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.isFinished = isFinished;
    }
}
