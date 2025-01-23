package com.example.project3regex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    private ListView taskListView;
    private Button buttonHighPriority, buttonMediumPriority, buttonLowPriority;
    private ArrayList<Task> taskList; // Holds all tasks
    private ArrayList<Task> filteredTaskList; // Holds filtered tasks based on priority
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        // Initialize UI components
        taskListView = findViewById(R.id.taskListView);
        buttonHighPriority = findViewById(R.id.buttonHighPriority);
        buttonMediumPriority = findViewById(R.id.buttonMediumPriority);
        buttonLowPriority = findViewById(R.id.buttonLowPriority);

        // Retrieve all tasks from the previous activity
        taskList = getIntent().getParcelableArrayListExtra("taskList");
        filteredTaskList = new ArrayList<>(taskList); // Initially, show all tasks

        // Initialize TaskAdapter with all tasks
        taskAdapter = new TaskAdapter(this, filteredTaskList);
        taskListView.setAdapter(taskAdapter);

        // Set click listeners for priority buttons
        buttonHighPriority.setOnClickListener(v -> showTasksByPriority("High"));
        buttonMediumPriority.setOnClickListener(v -> showTasksByPriority("Medium"));
        buttonLowPriority.setOnClickListener(v -> showTasksByPriority("Low"));

        // Handle task selection for updates
        taskListView.setOnItemClickListener((parent, view, position, id) -> {
            Task selectedTask = taskAdapter.getItem(position);
            showUpdatePrompt(selectedTask);
        });
    }

    // Filter tasks based on selected priority
    private void showTasksByPriority(String priority) {
        filteredTaskList.clear(); // Clear current filtered tasks
        for (Task task : taskList) {
            if (task.getPriority().equals(priority)) {
                filteredTaskList.add(task); // Add tasks that match the priority
            }
        }
        taskAdapter.notifyDataSetChanged(); // Refresh ListView with filtered tasks
    }

    // Show update prompt for task selection
    private void showUpdatePrompt(Task task) {
        new android.app.AlertDialog.Builder(this)
                .setMessage("Do you want to update this task?")
                .setPositiveButton("Yes", (dialog, which) -> navigateToUpdateTaskActivity(task))
                .setNegativeButton("No", null)
                .show();
    }

    // Navigate to UpdateTaskActivity for task update
    private void navigateToUpdateTaskActivity(Task task) {
        Intent intent = new Intent(TaskListActivity.this, UpdateTaskActivity.class);
        intent.putExtra("task", task);
        startActivityForResult(intent, 1);
    }

    // Handle updated task result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Task updatedTask = data.getParcelableExtra("updatedTask");
            updateTaskInList(updatedTask); // Update the task in the task list
        }
    }

    // Update task in the task list
    private void updateTaskInList(Task updatedTask) {
        // Replace the updated task in both filteredTaskList and taskList
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getId() == updatedTask.getId()) {
                taskList.set(i, updatedTask);
                break;
            }
        }

        // Refresh the ListView after task update
        taskAdapter.notifyDataSetChanged();
    }
}
