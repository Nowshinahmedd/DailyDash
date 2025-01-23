package com.example.project3regex;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {

    private String taskId;
    private String title;
    private String description;
    private String priority;
    private String date;

    // Default constructor for Firebase
    public Task() {
    }

    public Task(String taskId, String title, String description, String priority, String date) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.date = date;
    }

    protected Task(Parcel in) {
        taskId = in.readString();
        title = in.readString();
        description = in.readString();
        priority = in.readString();
        date = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public String getDate() {
        return date;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taskId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(priority);
        dest.writeString(date);
    }

    public Object getId() {
        return null;
    }
}
