package com.example.project3regex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {

    private EditText titleEditText, descriptionEditText, priorityEditText;
    private Button saveButton, navigateToPriorityButton, updateButton;
    private ArrayList<Task> taskList;  // List to hold tasks
    private Task taskToUpdate;  // Task to update

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        priorityEditText = findViewById(R.id.priorityEditText);
        saveButton = findViewById(R.id.saveButton);
        navigateToPriorityButton = findViewById(R.id.navigateToPriority);
        updateButton = findViewById(R.id.updateButton);

        // Initialize the task list
        taskList = new ArrayList<>();

        // Check if task is being passed for update
        Intent intent = getIntent();
        if (intent.hasExtra("taskToUpdate")) {
            taskToUpdate = intent.getParcelableExtra("taskToUpdate");

            // Pre-fill the fields with the task data
            titleEditText.setText(taskToUpdate.getTitle());
            descriptionEditText.setText(taskToUpdate.getDescription());
            priorityEditText.setText(taskToUpdate.getPriority());

            // Hide save button when updating, show update button
            saveButton.setVisibility(View.GONE);  // Hide save button
            updateButton.setVisibility(View.VISIBLE);  // Show update button
        }

        // Handle save button click (for new task)
        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String priority = priorityEditText.getText().toString().trim();

            // Validate input
            if (title.isEmpty() || description.isEmpty() || priority.isEmpty()) {
                Toast.makeText(TaskActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate priority
            if (!priority.equals("High") && !priority.equals("Medium") && !priority.equals("Low")) {
                Toast.makeText(TaskActivity.this, "Priority must be 'High', 'Medium', or 'Low'", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create new task
            Task newTask = new Task(title, description, priority);

            // Add task to task list
            taskList.add(newTask);

            // Show toast
            Toast.makeText(TaskActivity.this, "Task Saved", Toast.LENGTH_SHORT).show();

            // Return to the SelectPriority activity
            Intent returnIntent = new Intent(TaskActivity.this, SelectPriority.class);
            returnIntent.putParcelableArrayListExtra("taskList", taskList);
            startActivity(returnIntent);
        });

        // Handle update button click (for updating existing task)
        updateButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String priority = priorityEditText.getText().toString().trim();

            // Validate input
            if (title.isEmpty() || description.isEmpty() || priority.isEmpty()) {
                Toast.makeText(TaskActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate priority
            if (!priority.equals("High") && !priority.equals("Medium") && !priority.equals("Low")) {
                Toast.makeText(TaskActivity.this, "Priority must be 'High', 'Medium', or 'Low'", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update the task with new data
            taskToUpdate.setTitle(title);
            taskToUpdate.setDescription(description);
            taskToUpdate.setPriority(priority);

            // Show toast
            Toast.makeText(TaskActivity.this, "Task Updated", Toast.LENGTH_SHORT).show();

            // Make sure the task list is updated
            taskList.remove(taskToUpdate);
            taskList.add(taskToUpdate);  // Add updated task back to the list

            // Return to the SelectPriority activity
            Intent returnIntent = new Intent(TaskActivity.this, SelectPriority.class);
            returnIntent.putParcelableArrayListExtra("taskList", taskList);
            startActivity(returnIntent);
        });

        navigateToPriorityButton.setOnClickListener(v -> {
            Intent intentToPriority = new Intent(TaskActivity.this, SelectPriority.class);
            intentToPriority.putParcelableArrayListExtra("taskList", taskList);
            startActivity(intentToPriority);
        });
    }
}
