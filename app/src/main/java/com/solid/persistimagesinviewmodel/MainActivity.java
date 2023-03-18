package com.solid.persistimagesinviewmodel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Start the FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Add the fragment to the container
        MainFragment myFragment = new MainFragment();
        fragmentTransaction.add(R.id.fragment_container, myFragment);

        // Commit the FragmentTransaction
        fragmentTransaction.commit();
    }
}