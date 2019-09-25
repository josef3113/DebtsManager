package com.example.debtsmanager.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.debtsmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DebtToMeFragment extends Fragment {


    public DebtToMeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_debt_to_me, container, false);
    }

}
