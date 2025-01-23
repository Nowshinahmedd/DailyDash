package com.example.project3regex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class SelectPriority extends AppCompatActivity {

    private ListView taskListView;
    private TaskAdapter taskAdapter;
    private DatabaseReference databaseReference;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_priority);

        taskListView = findViewById(R.id.taskListView);

        databaseReference = FirebaseDatabase.getInstance().getReference("Tasks");
        taskList = new ArrayList<>();

        taskAdapter = new TaskAdapter(this, taskList);
        taskListView.setAdapter(taskAdapter);


        listenForTaskUpdates();
    }

    private void listenForTaskUpdates() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Task task = dataSnapshot.getValue(Task.class);
                if (task != null) {
                    taskList.add(task);
                    taskAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Task task = dataSnapshot.getValue(Task.class);
                if (task != null) {
                    for (int i = 0; i < taskList.size(); i++) {
                        if (taskList.get(i).getTaskId().equals(task.getTaskId())) {
                            taskList.set(i, task);
                            taskAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Task task = dataSnapshot.getValue(Task.class);
                if (task != null) {
                    taskList.remove(task);
                    taskAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // Handle reordering tasks if applicable
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
