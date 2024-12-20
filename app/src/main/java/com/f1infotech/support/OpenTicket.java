package com.f1infotech.support;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class OpenTicket extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST_CODE = 1001;
    private Spinner productSpinner;
    private Button openTicketButton;
    private Button showStatusButton;
    private EditText descriptionEditText;
    private TextView selectedProductTextView;
    private TextView ticketInfoTextView;
    private TextView selectedFileTextView;
    private ArrayList<String> products;
    private String selectedProduct;

    private static int ticketCounter = 1001;
    private static ArrayList<String> ticketStatuses = new ArrayList<>();
    private String selectedFileName = "No file selected";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_open_ticket);


        // Initialize views
        productSpinner = findViewById(R.id.productSpinner);
        openTicketButton = findViewById(R.id.openTicketButton);
//        showStatusButton = findViewById(R.id.showStatusButton);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        selectedProductTextView = findViewById(R.id.selectedProductTextView);
//        ticketInfoTextView = findViewById(R.id.ticketInfoTextView);
        selectedFileTextView = findViewById(R.id.selectedFileTextView);

        // Initialize the product list and set up the Spinner
        products = new ArrayList<>();
        products.add("Click here to select");
        products.add("Service 1");
        products.add("Service 2");
        products.add("Service 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, products);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productSpinner.setAdapter(adapter);

        // Set the selected product when the spinner item is selected
        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected product based on the spinner's position
                selectedProduct = products.get(position);

                if(!selectedProduct.equals("Click here to select")) {
                    // Update the TextView to display the selected product
                    selectedProductTextView.setText("Selected Service: " + selectedProduct);
                }else {
                    selectedProductTextView.setText("No Service Selected!");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no item is selected (optional)
            }
        });



        // Action when the Open Ticket button is clicked
        openTicketButton.setOnClickListener(v -> {
            String description = descriptionEditText.getText().toString();
            if (selectedProduct != null && !description.isEmpty() && !selectedProduct.equals("Click here to select")) {
                // Generate a ticket number and store the description
                String ticketNumber = "TICKET-" + ticketCounter++;
                ticketStatuses.add(ticketNumber + " - Opened - Service: " + selectedProduct + " - Description: " + description);

                // Display the ticket number
//                ticketInfoTextView.setText("Ticket Opened: " + ticketNumber + "\nStatus: Open");

                new AlertDialog.Builder(OpenTicket.this)
                        .setTitle("Ticket Opened")
                        .setMessage("Your ticket number is: " + ticketNumber +
                                "\nService: " + selectedProduct +
                                "\nDescription: " + description +
                                "\nFile Attached: " + selectedFileName +
                                "\nStatus: Open")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();

                // Clear the fields
                descriptionEditText.setText("");
                selectedProduct = null;
                selectedFileTextView.setText("No file selected");
                productSpinner.setSelection(0);
            } else {
                Toast.makeText(this, "Please select a service and enter a description", Toast.LENGTH_SHORT).show();
            }
        });

        // Action when the Show Status button is clicked
//        showStatusButton.setOnClickListener(v -> {
//            StringBuilder statusBuilder = new StringBuilder();
//            for (String ticketStatus : ticketStatuses) {
//                statusBuilder.append(ticketStatus).append("\n");
//            }
//            if (statusBuilder.length() > 0) {
//                ticketInfoTextView.setText(statusBuilder.toString());
//            } else {
//                ticketInfoTextView.setText("No open tickets.");
//            }
//        });

//        // Action when the Show Status button is clicked
//        showStatusButton.setOnClickListener(v -> {
//            // Open the TicketStatusActivity and pass the ticket statuses
//            Intent intent = new Intent(OpenTicket.this, TrackStatus.class);
//            intent.putStringArrayListExtra("ticketStatuses", ticketStatuses);
//            startActivity(intent);
//        });

        Button selectFileButton = findViewById(R.id.selectFileButton);
        selectFileButton.setOnClickListener(v -> {
            // Open file picker dialog
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*"); // Allows all file types; you can specify MIME types like "image/*" for images
            startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            // Get the URI of the selected file
            if (data != null && data.getData() != null) {
                Uri selectedFileUri = data.getData();
                selectedFileName = getFileName(selectedFileUri);

                // Display the selected file name in the TextView
                selectedFileTextView.setText("Selected File: " + selectedFileName);
            }
        }
    }

    // Helper method to extract file name from URI
    private String getFileName(Uri uri) {
        String fileName = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (columnIndex != -1) {
                    fileName = cursor.getString(columnIndex);
                }
                cursor.close();
            }
        }
        if (fileName == null) {
            fileName = uri.getPath();
            int cut = fileName.lastIndexOf('/');
            if (cut != -1) {
                fileName = fileName.substring(cut + 1);
            }
        }
        return fileName;
    }

    // Helper method to track ticket status
    public static String trackTicketStatus(String ticketNumber) {
        for (String ticketStatus : ticketStatuses) {
            if (ticketStatus.contains(ticketNumber)) {
                return ticketStatus;
            }
        }
        return "Ticket not found!";
    }

    public void backClicked(View view) {
        finish();
    }
}