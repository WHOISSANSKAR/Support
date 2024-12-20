package com.f1infotech.support;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private Context context;
    private ArrayList<String> ticketStatuses;

    public TicketAdapter(Context context, ArrayList<String> ticketStatuses) {
        this.context = context;
        this.ticketStatuses = ticketStatuses;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each ticket
        View view = LayoutInflater.from(context).inflate(R.layout.ticket_item, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        // Set the ticket status data for the current position
        String ticketStatus = ticketStatuses.get(position);
        holder.ticketStatusTextView.setText(ticketStatus);

        // Set click listener to open TicketDetails activity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TicketDetails.class);
            intent.putExtra("ticketDetails", ticketStatus); // Pass the ticket details
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ticketStatuses.size(); // Return the number of tickets
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {

        // Declare the TextView to display ticket status
        TextView ticketStatusTextView;

        public TicketViewHolder(View itemView) {
            super(itemView);
            // Initialize the TextView
            ticketStatusTextView = itemView.findViewById(R.id.ticketStatusTextView);
        }
    }
}
