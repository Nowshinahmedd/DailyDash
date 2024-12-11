package com.example.project3regex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SelectPriority extends AppCompatActivity {

    private ArrayList<Task> taskList;  // List to hold tasks
    private ListView taskListView;
    private Button buttonHighPriority, buttonMediumPriority, buttonLowPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_priority);

        taskListView = findViewById(R.id.taskListView);
        buttonHighPriority = findViewById(R.id.buttonHighPriority);
        buttonMediumPriority = findViewById(R.id.buttonMediumPriority);
        buttonLowPriority = findViewById(R.id.buttonLowPriority);

        // Get the task list passed from TaskActivity
        Intent intent = getIntent();
        if (intent.hasExtra("taskList")) {
            taskList = intent.getParcelableArrayListExtra("taskList");
        } else {
            taskList = new ArrayList<>();
        }

        // Show High Priority tasks
        buttonHighPriority.setOnClickListener(v -> showTasksByPriority("High"));

        // Show Medium Priority tasks
        buttonMediumPriority.setOnClickListener(v -> showTasksByPriority("Medium"));

        // Show Low Priority tasks
        buttonLowPriority.setOnClickListener(v -> showTasksByPriority("Low"));

        // Initially show all tasks (or high priority by default)
        showTasksByPriority("High");  // You can change this to show all tasks initially
    }

    private void showTasksByPriority(String priority) {
        ArrayList<Task> filteredTasks = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getPriority().equalsIgnoreCase(priority)) {
                filteredTasks.add(task);
            }
        }

        if (filteredTasks.isEmpty()) {
            Toast.makeText(this, "No tasks found for " + priority + " priority.", Toast.LENGTH_SHORT).show();
        }

        // Create an adapter and set it to the ListView
        TaskAdapter taskAdapter = new TaskAdapter(this, filteredTasks);
        taskListView.setAdapter(taskAdapter);

        // Set item click listener to update task when clicked
        taskListView.setOnItemClickListener((parent, view, position, id) -> {
            Task taskToUpdate = filteredTasks.get(position);
            Intent updateIntent = new Intent(SelectPriority.this, TaskActivity.class);
            updateIntent.putExtra("taskToUpdate", taskToUpdate);
            startActivity(updateIntent);  // Open TaskActivity for updating
        });
    }
}
