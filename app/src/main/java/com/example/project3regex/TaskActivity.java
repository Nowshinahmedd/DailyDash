package com.example.project3regex;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

import java.util.Calendar;
import java.util.HashMap;

public class TaskActivity extends AppCompatActivity {

    private EditText titleEditText, descriptionEditText, dateEditText;
    private Spinner prioritySpinner;
    private Button dateButton, saveButton, updateButton, navigateToPriorityButton;
    private String selectedDate;
    private Switch notificationSwitch;

    private DatabaseReference databaseReference;
    private String taskId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task); // Set the layout file

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Tasks");

        // Initialize the views
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        prioritySpinner = findViewById(R.id.prioritySpinner);
        dateEditText = findViewById(R.id.dateEditText);

        dateButton = findViewById(R.id.dateButton);
        saveButton = findViewById(R.id.saveButton);
        updateButton = findViewById(R.id.updateButton);
        navigateToPriorityButton = findViewById(R.id.navigateToPriority);

        notificationSwitch = findViewById(R.id.notificationSwitch);

        // Populate the spinner with priority options
        String[] priorities = {"High", "Medium", "Low"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, priorities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);

        // Set up the Date Button
        dateButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(TaskActivity.this,
                    (view, year1, month1, dayOfMonth) -> {
                        selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        dateEditText.setText(selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });

        // Save Task Button
        saveButton.setOnClickListener(v -> saveTask());

        // Update Task Button
        updateButton.setOnClickListener(v -> updateTask());

        // Navigate to Priority Page Button
        navigateToPriorityButton.setOnClickListener(v -> navigateToPriorityPage());

        // Notification Switch
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String message = isChecked ? "Notification Enabled" : "Notification Disabled";
            Toast.makeText(TaskActivity.this, message, Toast.LENGTH_SHORT).show();
        });

        // Listen for real-time updates in the Firebase database
        listenForRealTimeUpdates();
    }

    // Save Task Method
    private void saveTask() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String priority = prioritySpinner.getSelectedItem().toString();

        if (selectedDate == null || selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select a date.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!priority.equalsIgnoreCase("High") && !priority.equalsIgnoreCase("Medium") && !priority.equalsIgnoreCase("Low")) {
            Toast.makeText(this, "Please select a valid priority (High, Medium, or Low).", Toast.LENGTH_LONG).show();
            return;
        }

        String taskId = databaseReference.push().getKey(); // Generate a unique key for the task
        Task task = new Task(taskId, title, description, priority, selectedDate);

        if (taskId != null) {
            databaseReference.child(taskId).setValue(task)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Task saved successfully!", Toast.LENGTH_SHORT).show();
                        clearInputs(); // Clear the inputs after saving
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to save task: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    // Update Task Method
    private void updateTask() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String priority = prioritySpinner.getSelectedItem().toString();

        if (selectedDate == null || selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select a date.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!priority.equalsIgnoreCase("High") && !priority.equalsIgnoreCase("Medium") && !priority.equalsIgnoreCase("Low")) {
            Toast.makeText(this, "Please select a valid priority (High, Medium, or Low).", Toast.LENGTH_LONG).show();
            return;
        }

        if (taskId != null) {
            HashMap<String, Object> updates = new HashMap<>();
            updates.put("title", title);
            updates.put("description", description);
            updates.put("priority", priority);
            updates.put("date", selectedDate);

            databaseReference.child(taskId).updateChildren(updates)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Task updated successfully!", Toast.LENGTH_SHORT).show();
                        clearInputs(); // Clear the inputs after updating
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to update task: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "No task selected for update.", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to clear input fields after save/update
    private void clearInputs() {
        titleEditText.setText("");
        descriptionEditText.setText("");
        dateEditText.setText("");
        prioritySpinner.setSelection(0); // Reset to the first priority
    }

    // Navigate to Priority Page Method
    private void navigateToPriorityPage() {
        Intent intent = new Intent(TaskActivity.this, SelectPriority.class); // Replace SelectPriority with the correct class
        startActivity(intent);
    }

    // Listen for real-time updates from Firebase
    private void listenForRealTimeUpdates() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Task task = dataSnapshot.getValue(Task.class);
                if (task != null) {
                    Toast.makeText(TaskActivity.this, "New Task Added: " + task.getTitle(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Task task = dataSnapshot.getValue(Task.class);
                if (task != null) {
                    Toast.makeText(TaskActivity.this, "Task Updated: " + task.getTitle(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Task task = dataSnapshot.getValue(Task.class);
                if (task != null) {
                    Toast.makeText(TaskActivity.this, "Task Removed: " + task.getTitle(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // Handle reordering tasks if applicable
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TaskActivity.this, "Error fetching tasks: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
