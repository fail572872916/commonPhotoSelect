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

    String tag = "-1";
    List<Fragment> tagList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        button=findViewById(R.id.button);
        fragment1 = (PhotoSelectFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);
        fragment2 = (PhotoSelectFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView3);
        fragment3 = (PhotoSelectFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView4);

        fragment1.setOnClickListener(() -> tag = fragment1.getTag());
        fragment2.setOnClickListener(() -> tag = fragment2.getTag());
        fragment3.setOnClickListener(() -> tag = fragment3.getTag());

        tagList.add(fragment1);
        tagList.add(fragment2);
        tagList.add(fragment3);
        button.setOnClickListener(v -> {
            //拿到要提交的图片
            Log.d("TestActivity", "fragment1.getSelectList():" + fragment1.getSelectList());
            Log.d("TestActivity", "fragment2.getSelectList():" + fragment2.getSelectList());
            Log.d("TestActivity", "fragment3.getSelectList():" + fragment3.getSelectList());
            Toast.makeText(TestActivity.this, "提交数据", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        PhotoSelectFragment.forwardData(tag, resultCode, data, tagList);


    }
}