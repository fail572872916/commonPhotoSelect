package com.example.photoselectpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ExampleActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);

        findViewById(R.id.button2).setOnClickListener(v -> startActivity(new Intent(ExampleActivity2.this,TestActivity.class)));
        findViewById(R.id.button3).setOnClickListener(v -> startActivity(new Intent(ExampleActivity2.this,Test2Activity.class)));
    }
}