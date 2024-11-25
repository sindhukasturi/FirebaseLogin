package com.example.myapp.HomeModules;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapp.R;

public class CertificateFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_certificate, container, false);

        Button winCertificateButton = view.findViewById(R.id.winCertificateButton);
        winCertificateButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CertificateActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
