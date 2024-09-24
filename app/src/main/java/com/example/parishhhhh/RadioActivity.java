package com.example.parishhhhh;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RadioActivity extends AppCompatActivity {

    private CheckBox pizza, burger, pasta, salad;
    private RadioGroup radioGroupDrinks;
    private TextView quantityTextView, priceTextView, rating;
    private RatingBar ratingBar;
    private Switch feedbackSwitch;
    private EditText feedbackEditText;
    private Button increment, decrement, orderButton;

    private int quantity = 0;
    private final int pricePerItem = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio); // Use your actual layout file name

        pizza = findViewById(R.id.pizza);
        burger = findViewById(R.id.burger);
        pasta = findViewById(R.id.pasta);
        salad = findViewById(R.id.salad);
        radioGroupDrinks = findViewById(R.id.radioGroupDrinks);
        quantityTextView = findViewById(R.id.quantityTextView);
        priceTextView = findViewById(R.id.priceTextView);
        ratingBar = findViewById(R.id.ratingBar);
        rating = findViewById(R.id.rating);
        feedbackSwitch = findViewById(R.id.feedbackSwitch);
        feedbackEditText = findViewById(R.id.feedbackEditText);
        increment = findViewById(R.id.increment);
        decrement = findViewById(R.id.decrement);
        orderButton = findViewById(R.id.order_btn);

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                updateQuantityAndPrice();
                Toast.makeText(RadioActivity.this, "Quantity increased", Toast.LENGTH_SHORT).show();
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 0) {
                    quantity--;
                    updateQuantityAndPrice();
                    Toast.makeText(RadioActivity.this, "Quantity decreased", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RadioActivity.this, "Cannot decrease quantity below zero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        feedbackSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                feedbackEditText.setVisibility(View.VISIBLE);
            } else {
                feedbackEditText.setVisibility(View.GONE);
            }
        });

        orderButton.setOnClickListener(v -> {
            // Handle order placement logic
            String selectedFoods = getSelectedFoods();
            String drink = getSelectedDrink();
            String userRating = String.valueOf(ratingBar.getRating());
            String feedback = feedbackEditText.getText().toString();

            // Place order logic goes here (e.g., send to server or show confirmation)
            Toast.makeText(RadioActivity.this, "Order Placed:\n" + selectedFoods + "\nDrink: " + drink + "\nRating: " + userRating, Toast.LENGTH_LONG).show();
        });
    }

    private void updateQuantityAndPrice() {
        quantityTextView.setText(String.valueOf(quantity));
        priceTextView.setText("BDT " + (quantity * pricePerItem));
    }

    private String getSelectedFoods() {
        StringBuilder selectedFoods = new StringBuilder();
        if (pizza.isChecked()) selectedFoods.append("Pizza, ");
        if (burger.isChecked()) selectedFoods.append("Burger, ");
        if (pasta.isChecked()) selectedFoods.append("Pasta, ");
        if (salad.isChecked()) selectedFoods.append("Salad, ");

        // Remove the last comma and space if needed
        if (selectedFoods.length() > 0) {
            selectedFoods.setLength(selectedFoods.length() - 2);
        }
        return selectedFoods.toString();
    }

    private String getSelectedDrink() {
        int selectedId = radioGroupDrinks.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        return selectedRadioButton != null ? selectedRadioButton.getText().toString() : "No Drink Selected";
    }
}
