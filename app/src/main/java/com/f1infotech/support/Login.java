package com.f1infotech.support;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    // Declare the UI elements
    private EditText usernameField, passwordField;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);  // Enable edge-to-edge support
        setContentView(R.layout.activity_login); // Ensure the layout file matches

        // Initialize the UI elements
        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);
        submitButton = findViewById(R.id.submitButton);

        // Set up the submit button's click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });

        // Set window insets listener for edge-to-edge behavior
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(uiOptions); // Hide the status and navigation bars
        }
    }

    // Handle the login logic
    private void handleLogin() {
        // Get the text from the username and password fields
        String username = usernameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        // Validate the inputs
        if (username.isEmpty()) {
            usernameField.setError("Username is required");
            return;
        }

        if (password.isEmpty()) {
            passwordField.setError("Password is required");
            return;
        }

        // Perform login logic (this is just an example, you can replace it with actual login code)
        if (username.equals("admin") && password.equals("password")) {
            // If the credentials are correct, show a success message (or proceed to the next screen)
            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
            // You can start another activity here, for example:
            // startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            // finish(); // Close the login activity
        } else {
            // If the credentials are incorrect, show an error message
            Toast.makeText(Login.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
        }
    }
}