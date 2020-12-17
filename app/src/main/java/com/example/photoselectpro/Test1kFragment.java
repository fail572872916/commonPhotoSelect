package com.example.photoselectpro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Test1kFragment extends Fragment {


    PhotoSelectFragment fragment1;
    PhotoSelectFragment fragment2;
    PhotoSelectFragment fragment3;
    String tag="-1";
    View view;
    public Test1kFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_test1k, container, false);
        fragment1 = (PhotoSelectFragment) getChildFragmentManager().findFragmentById(R.id.fragmentContainerView2);
        fragment2 = (PhotoSelectFragment) getChildFragmentManager().findFragmentById(R.id.fragmentContainerView3);
        fragment3 = (PhotoSelectFragment) getChildFragmentManager().findFragmentById(R.id.fragmentContainerView4);

        fragment1.setOnClickListener(() ->tag= fragment1.getTag());
        fragment2.setOnClickListener(() ->tag= fragment2.getTag());
        fragment3.setOnClickListener(() ->tag= fragment3.getTag());

        return view;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(tag.equals(fragment1.getTag())) {

            Log.d("PhotoSelectFragment", "我是tag"+fragment1.getTag());
            fragment1.onActivityResult(Integer.parseInt(fragment1.getTag()), resultCode, data);
            Log.d("TestActivity", "我是1");
        }
        if(tag.equals(fragment2.getTag())) {

            Log.d("TestActivity", "我是2");
            fragment2.onActivityResult(Integer.parseInt(fragment2.getTag()), resultCode, data);
        }
        if(tag.equals(fragment3.getTag())) {

            Log.d("TestActivity", "我是2");
            fragment3.onActivityResult(Integer.parseInt(fragment3.getTag()), resultCode, data);
        }

    }
}