package com.f1infotech.support;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TicketDetails extends AppCompatActivity {

    private TextView ticketDetailsTextView;
    private ImageView orderPlacedDot, dispatchedDot, deliveredDot;
    private View orderPlacedLine, dispatchedLine;
    private TextView orderPlacedText, dispatchedText, deliveredText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ticket_details);

        // Initialize views
        ticketDetailsTextView = findViewById(R.id.ticketDetailsTextView);
        orderPlacedDot = findViewById(R.id.orderPlacedDot);
        dispatchedDot = findViewById(R.id.dispatchedDot);
        deliveredDot = findViewById(R.id.deliveredDot);
        orderPlacedLine = findViewById(R.id.orderPlacedLine);
        dispatchedLine = findViewById(R.id.dispatchedLine);
        orderPlacedText = findViewById(R.id.orderPlacedText);
        dispatchedText = findViewById(R.id.dispatchedText);
        deliveredText = findViewById(R.id.deliveredText);

        // Retrieve the ticket details passed from TrackStatus activity
        Intent intent = getIntent();
        String ticketDetails = intent.getStringExtra("ticketDetails");

        // Display the ticket details
        if (ticketDetails != null) {
            ticketDetailsTextView.setText(ticketDetails);
        }

        // Set ticket status progress
        setTicketStatus(ticketDetails);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setTicketStatus(String ticketDetails) {
        // Update the status of the progress tracker
        if (ticketDetails != null) {
            if (ticketDetails.contains("Status: Order Placed")) {
                orderPlacedDot.setImageResource(R.drawable.blue_circle);
                orderPlacedLine.setBackgroundColor(getResources().getColor(R.color.blue));
                orderPlacedText.setTextColor(getResources().getColor(R.color.blue));
            }

            if (ticketDetails.contains("Status: Dispatched")) {
                dispatchedDot.setImageResource(R.drawable.blue_circle);
                dispatchedLine.setBackgroundColor(getResources().getColor(R.color.blue));
                dispatchedText.setTextColor(getResources().getColor(R.color.blue));
            }

            if (ticketDetails.contains("Status: Delivered")) {
                deliveredDot.setImageResource(R.drawable.blue_circle);
                deliveredText.setTextColor(getResources().getColor(R.color.blue));
            }
        }
    }

    public void backClicked(View view) {
        finish();
    }
}
