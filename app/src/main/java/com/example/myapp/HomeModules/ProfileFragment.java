package com.example.myapp.HomeModules;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Initialize ProgressDialog
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading data...");
        progressDialog.setCancelable(false);
        // Check if the user is logged in; otherwise, authenticate anonymously
        if (currentUser == null) {
            progressDialog.show();
            mAuth.signInAnonymously().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("ProfileFragment", "Anonymous authentication successful");
                    fetchFirebaseData(view);
                } else {
                    progressDialog.dismiss();
                    Log.e("ProfileFragment", "Anonymous authentication failed", task.getException());
                    Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            progressDialog.show();
            fetchFirebaseData(view);
        }

        return view;
    }

    private void fetchFirebaseData(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://myapp-6389b-default-rtdb.firebaseio.com/");
        databaseReference = database.getReference("users");
        TextView userDataTextView = view.findViewById(R.id.userDataTextView);

        // Fetch data from Firebase
        databaseReference.get().addOnCompleteListener(task -> {
            progressDialog.dismiss();
            if (task.isSuccessful()) {
                StringBuilder userData = new StringBuilder();
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    String name = snapshot.child("name").getValue(String.class);
                    Integer age = snapshot.child("age").getValue(Integer.class);
                    Boolean premium = snapshot.child("premium").getValue(Boolean.class);

                    if (name != null && age != null && premium != null) {
                        userData.append("Name: ").append(name).append("\n")
                                .append("Age: ").append(age).append("\n")
                                .append("Premium: ").append(premium).append("\n\n");
                    } else {
                        Log.e("ProfileFragment", "Missing data for user: " + snapshot.getKey());
                    }
                }
                userDataTextView.setText(userData.toString());
            } else {
                Log.e("ProfileFragment", "Database fetch failed: ", task.getException());
                Toast.makeText(getContext(), "Failed to fetch data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
