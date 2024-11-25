package com.example.myapp.HomeModules;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.HomeActivity;
import com.example.myapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CertificateActivity extends AppCompatActivity {
    private EditText answerEditText;
    private Button submitButton;
    private TextView timerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_certificate_activity);

        answerEditText = findViewById(R.id.answerEditText);
        submitButton = findViewById(R.id.submitButton);
        timerTextView = findViewById(R.id.timerTextView);

        // Hide the submit button initially
        submitButton.setVisibility(View.GONE);

        // Start the countdown timer
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time Remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                answerEditText.setEnabled(false);
                submitButton.setVisibility(View.VISIBLE);
            }
        }.start();

        // Set up the submit button action
        submitButton.setOnClickListener(v -> {
            String answer = answerEditText.getText().toString().trim();

            if (answer.isEmpty()) {
                Toast.makeText(this, "Please enter an answer before submitting.", Toast.LENGTH_SHORT).show();
            } else {
                saveAnswerToFirebase(answer);
            }
        });
    }

    private void saveAnswerToFirebase(String answer) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://myapp-6389b-default-rtdb.firebaseio.com/");
        DatabaseReference answersRef = database.getReference("answers");

        String answerId = answersRef.push().getKey(); // Generate a unique key

        if (answerId == null) {
            Toast.makeText(this, "Error generating unique ID for answer.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the answer under the unique ID
        answersRef.child(answerId).setValue(answer)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Submission successful!", Toast.LENGTH_SHORT).show();
                        // Navigate back to home page
                        Intent intent = new Intent(CertificateActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Submission failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to save answer: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
