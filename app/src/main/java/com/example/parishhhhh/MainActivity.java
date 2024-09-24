package com.example.parishhhhh;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonShowText;
    private Button buttonShowImage;
    private Button buttonGoToRadioActivity;
    private TextView textViewMessage;
    private ImageView imageViewExample;
    private SeekBar seekBar;
    private CheckBox checkBox; // Declare CheckBox

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        buttonShowText = findViewById(R.id.buttonShowText);
        buttonShowImage = findViewById(R.id.buttonShowImage);
        buttonGoToRadioActivity = findViewById(R.id.buttonGoToRadioActivity);
        textViewMessage = findViewById(R.id.textViewMessage);
        imageViewExample = findViewById(R.id.imageViewExample);
        seekBar = findViewById(R.id.seekBar);
        checkBox = findViewById(R.id.checkBox);


        textViewMessage.setVisibility(View.GONE);
        imageViewExample.setVisibility(View.GONE);


        buttonShowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewMessage.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Text Shown", Toast.LENGTH_SHORT).show();
            }
        });


        buttonShowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewExample.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Image Shown", Toast.LENGTH_SHORT).show();
            }
        });


        buttonGoToRadioActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    Intent intent = new Intent(MainActivity.this, RadioActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Please agree to the terms", Toast.LENGTH_SHORT).show();
                }
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                showSeekBarDialog(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Optional: Do something when tracking starts
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Optional: Do something when tracking stops
            }
        });
    }

    private void showSeekBarDialog(int progress) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SeekBar Value");
        builder.setMessage("Current Value: " + progress);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
