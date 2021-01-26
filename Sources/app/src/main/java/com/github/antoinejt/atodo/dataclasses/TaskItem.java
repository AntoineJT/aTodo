package com.github.antoinejt.atodo.dataclasses;

public class TaskItem {
    public final String title;
    public final String shortDescription;

    public TaskItem(String title, String shortDescription) {
        this.title = title;
        this.shortDescription = shortDescription;
    }
}
