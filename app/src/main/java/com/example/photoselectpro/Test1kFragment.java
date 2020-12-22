package com.example.photoselectpro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileWriter;


public class Test1kFragment extends Fragment {


    PhotoSelectFragment fragment1;
    PhotoSelectFragment fragment2;
    PhotoSelectFragment fragment3;

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


        return view;


    }


}