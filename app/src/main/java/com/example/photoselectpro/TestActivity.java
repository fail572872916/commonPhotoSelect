package com.example.photoselectpro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    PhotoSelectFragment fragment1;
    PhotoSelectFragment fragment2;
    PhotoSelectFragment fragment3;
    Button button;

    String tag = "-1";

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
        if (tag.equals(fragment1.getTag())) {

            Log.d("PhotoSelectFragment", "我是tag" + fragment1.getTag());
            fragment1.onActivityResult(Integer.parseInt(fragment1.getTag()), resultCode, data);
            Log.d("TestActivity", "我是1");
        }
        if (tag.equals(fragment2.getTag())) {

            Log.d("TestActivity", "我是2");
            fragment2.onActivityResult(Integer.parseInt(fragment2.getTag()), resultCode, data);
        }
        if (tag.equals(fragment3.getTag())) {

            Log.d("TestActivity", "我是2");
            fragment3.onActivityResult(Integer.parseInt(fragment3.getTag()), resultCode, data);
        }

    }
}