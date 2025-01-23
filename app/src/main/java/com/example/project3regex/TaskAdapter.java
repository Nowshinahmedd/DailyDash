package com.example.project3regex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    private final Context context;
    private final List<Task> taskList;

    // Constructor with Context first, then List<Task>
    public TaskAdapter(Context context, List<Task> taskList) {
        super(context, R.layout.task_item, taskList);
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.task_item, parent, false);
        }

        Task task = taskList.get(position);

        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView priorityTextView = convertView.findViewById(R.id.priorityTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateTextView);

        titleTextView.setText(task.getTitle());
        priorityTextView.setText(task.getPriority());
        dateTextView.setText(task.getDate());

        return convertView;
    }
}
