package com.app.tasvirassignment1;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.app.androidwearstopwatchapp.R;
import com.app.androidwearstopwatchapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;
    private boolean isRunning = false;
    private long startTime = 0;
    private long elapsedTime = 0;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initTask();
    }

    private void initTask() {
        bindView();
    }

    // A runnable to update the elapsed time and the displayed time
    private final Runnable timerRunnable = new Runnable() {
        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            updateTime(); // Update the elapsed time on the UI
            handler.postDelayed(this, 1000); // Schedule this Runnable to run again after 1 second
        }
    };

    private void bindView() {
        // Set click listeners for the buttons
        binding.startButton.setOnClickListener(this);
        binding.stopButton.setOnClickListener(this);
        binding.resetButton.setOnClickListener(this);
    }

    private void startTimer() {
        //if stopWatch is not Running then this condition will be true
        if (!isRunning) {
            // Start the timer and disable the appropriate buttons
            startTime = System.currentTimeMillis() - elapsedTime;
            handler.postDelayed(timerRunnable, 0);
            isRunning = true;
            binding.startButton.setEnabled(false);
            binding.stopButton.setEnabled(true);
            binding.resetButton.setEnabled(true);
        }
    }

    private void stopTimer() {
        //if stopWatch is Running then this condition will be true
        if (isRunning) {
            // Stop the timer and enable the appropriate buttons
            handler.removeCallbacks(timerRunnable);
            isRunning = false;
            binding.stopButton.setEnabled(false);
            binding.startButton.setEnabled(true);
            binding.resetButton.setEnabled(true);
        }
    }

    private void resetTimer() {
        //if stopWatch is Paused or not Running then this condition will be true
        if (!isRunning) {
            // Reset the timer and disable the reset button
            elapsedTime = 0;
            updateTime();
            binding.resetButton.setEnabled(false);
        }
    }

    @SuppressLint("DefaultLocale")
    private void updateTime() {
        // Check if the stopwatch is running
        if (isRunning) {
            elapsedTime = System.currentTimeMillis() - startTime;
            // The elapsedTime variable now holds the time that has passed since the stopwatch started.
        }

        // Calculate the hours, minutes, and seconds from elapsed time
        long seconds = elapsedTime / 1000;
        long minutes = seconds / 60;
        seconds %= 60;
        long hours = minutes / 60;
        minutes %= 60;

        // Display the time
        binding.timeTextView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    @Override
    public void onClick(View view) {

        // Handle button clicks
        if (view.getId() == R.id.startButton) {
            startTimer();
        } else if (view.getId() == R.id.stopButton) {
            stopTimer();
        } else if (view.getId() == R.id.resetButton) {
            resetTimer();
        }
    }
}