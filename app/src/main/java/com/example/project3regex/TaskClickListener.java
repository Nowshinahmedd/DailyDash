// TaskClickListener.java
package com.example.project3regex;

public interface TaskClickListener {
    void onTaskUpdate(Task task, int position);
    void onTaskDelete(Task task, int position);
}
