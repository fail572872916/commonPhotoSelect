package com.example.photoselectpro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    PhotoSelectFragment fragment1;
    PhotoSelectFragment fragment2;
    PhotoSelectFragment fragment3;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        button=findViewById(R.id.button);
        fragment1 = (PhotoSelectFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);
        fragment2 = (PhotoSelectFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView3);
        fragment3 = (PhotoSelectFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView4);


        button.setOnClickListener(v -> {
            //拿到要提交的图片
            Log.d("TestActivity", "fragment1.getSelectList():" + fragment1.getSelectList());
            Log.d("TestActivity", "fragment2.getSelectList():" + fragment2.getSelectList());
            Log.d("TestActivity", "fragment3.getSelectList():" + fragment3.getSelectList());
            Toast.makeText(TestActivity.this, "提交数据", Toast.LENGTH_SHORT).show();
        });

    }


}