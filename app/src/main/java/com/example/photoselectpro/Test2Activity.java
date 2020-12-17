package com.example.photoselectpro;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Test2Activity extends AppCompatActivity {
    Test1kFragment fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        fragment1 = (Test1kFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        fragment1.onActivityResult(requestCode, resultCode, data);

    }
    }