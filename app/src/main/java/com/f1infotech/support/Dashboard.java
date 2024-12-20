package com.f1infotech.support;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        View raiseTicketLayout = findViewById(R.id.raiseTicketLayout);
        View trackStatusLayout = findViewById(R.id.trackStatusLayout);

        // Set click listener for 'raiseTicketLayout'
        raiseTicketLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform expand animation and navigate to OpenTicket activity
                performExpandAnimation(v, OpenTicket.class, "raise_ticket_transition");
            }
        });

        // Set click listener for 'trackStatusLayout'
        trackStatusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform expand animation and navigate to TrackStatus activity
                performExpandAnimation(v, TrackStatus.class, "track_status_transition");
            }
        });
    }

    private void performExpandAnimation(View view, Class<?> targetActivity, String transitionName) {
        // Load the expand animation
        Animation expand = AnimationUtils.loadAnimation(this, R.anim.expand_animation);

        // Set an AnimationListener to trigger the activity transition after the animation ends
        expand.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Nothing to do when the animation starts
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Start the target activity after the animation completes
                Intent intent = new Intent(Dashboard.this, targetActivity);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(Dashboard.this, view, transitionName);
                startActivity(intent, options.toBundle());
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Nothing to do when the animation repeats
            }
        });

        // Start the animation on the view
        view.startAnimation(expand);
    }
}
