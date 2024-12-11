package com.example.project3regex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    private Button navigateToPriorityButton;
    // Define a list of tasks
    public static ArrayList<Task> taskList = new ArrayList<>();  // This will hold tasks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        navigateToPriorityButton = findViewById(R.id.navigateToPriority);

        // You can add some dummy tasks to taskList (or fetch from a database)
        taskList.add(new Task("Task 1", "Description 1", "High"));
        taskList.add(new Task("Task 2", "Description 2", "Medium"));
        taskList.add(new Task("Task 3", "Description 3", "Low"));

        // Navigate to Priority Selection when button is clicked
        navigateToPriorityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskListActivity.this, SelectPriority.class);
                intent.putParcelableArrayListExtra("taskList", taskList);  // Pass taskList to SelectPriority
                startActivity(intent);
            }
        });
    }
}
