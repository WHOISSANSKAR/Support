package com.f1infotech.support;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrackStatus extends AppCompatActivity {

    private RecyclerView ticketRecyclerView;
    private TicketAdapter ticketAdapter;
    private ArrayList<String> ticketStatuses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_track_status);

        // Initialize the RecyclerView
        ticketRecyclerView = findViewById(R.id.ticketRecyclerView);
        ticketRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample data for ticket statuses, you can use your actual ticketStatuses list
        ticketStatuses = new ArrayList<>();
        ticketStatuses.add("TICKET-1001 - Opened - Service: Service 1 - Description: Issue - File Attached: None - Status: Open");
        ticketStatuses.add("TICKET-1002 - Opened - Service: Service 2 - Description: Payment Issue - File Attached: File1.pdf - Status: Open");
        ticketStatuses.add("TICKET-1003 - Opened - Service: Service 1 - Description: Issue - File Attached: None - Status: Open");
        ticketStatuses.add("TICKET-1004 - Opened - Service: Service 2 - Description: Payment Issue - File Attached: File1.pdf - Status: Open");
        ticketStatuses.add("TICKET-1005 - Opened - Service: Service 1 - Description: Issue - File Attached: None - Status: Open");
        ticketStatuses.add("TICKET-1006 - Opened - Service: Service 2 - Description: Payment Issue - File Attached: File1.pdf - Status: Open");

        // Set the adapter for the RecyclerView
        ticketAdapter = new TicketAdapter(this, ticketStatuses);
        ticketRecyclerView.setAdapter(ticketAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void backClicked(View view) {
        finish();
    }
}