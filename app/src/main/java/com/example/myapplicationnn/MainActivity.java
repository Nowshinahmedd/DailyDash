package com.daily_dash.assignment1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parishhhhh.R;

public class MainActivity extends AppCompatActivity {

    private Button buttonShowText;
    private Button buttonShowImage;
    private TextView textViewMessage;
    private ImageView imageViewExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        buttonShowText = findViewById(R.id.buttonShowText);
        buttonShowImage = findViewById(R.id.buttonShowImage);
        textViewMessage = findViewById(R.id.textViewMessage);
        imageViewExample = findViewById(R.id.imageViewExample);

        // Hide the image and text initially
        textViewMessage.setVisibility(View.GONE);
        imageViewExample.setVisibility(View.GONE);

        // Set a click listener for the button to show text
        buttonShowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewMessage.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Text Shown", Toast.LENGTH_SHORT).show();
            }
        });

        // Set a click listener for the button to show image
        buttonShowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewExample.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Image Shown", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
