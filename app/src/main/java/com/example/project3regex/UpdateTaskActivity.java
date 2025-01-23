package com.example.project3regex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

public class UpdateTaskActivity extends Activity {

    private EditText titleEditText, descriptionEditText, dateEditText;
    private Spinner prioritySpinner;
    private Button saveButton;
    private Task taskToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        taskToUpdate = getIntent().getParcelableExtra("taskToUpdate");

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dateEditText = findViewById(R.id.dateEditText);
        prioritySpinner = findViewById(R.id.prioritySpinner);
        saveButton = findViewById(R.id.saveButton);

        titleEditText.setText(taskToUpdate.getTitle());
        descriptionEditText.setText(taskToUpdate.getDescription());
        dateEditText.setText(taskToUpdate.getDate());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[] {"Low", "Medium", "High"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);
        prioritySpinner.setSelection(adapter.getPosition(taskToUpdate.getPriority()));

        saveButton.setOnClickListener(v -> saveUpdatedTask());
    }

    private void saveUpdatedTask() {
        taskToUpdate.setTitle(titleEditText.getText().toString());
        taskToUpdate.setDescription(descriptionEditText.getText().toString());
        taskToUpdate.setDate(dateEditText.getText().toString());
        taskToUpdate.setPriority(prioritySpinner.getSelectedItem().toString());

        Intent resultIntent = new Intent();
        resultIntent.putExtra("updatedTask", String.valueOf(taskToUpdate));
        setResult(RESULT_OK, resultIntent);
        finish();
    }

}
