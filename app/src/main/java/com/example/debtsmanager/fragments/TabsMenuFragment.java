package com.example.debtsmanager.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.debtsmanager.R;
import com.example.debtsmanager.controllers.Repository;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabsMenuFragment extends Fragment
{


    public TabsMenuFragment() {
    // Required empty public constructor
}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tabs_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        Repository repository = Repository.getInstance();

        Button myDebts = view.findViewById(R.id.tebsMenuMyDebts);

        myDebts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.action_tabsMenuFragment_to_debtToOtherFragment);

            }
        });

        Button otherDebts = view.findViewById(R.id.tebsMenuOtherDebts);

        otherDebts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.action_tabsMenuFragment_to_debtToMeFragment);

            }
        });

        Button addDebts = view.findViewById(R.id.tebsMenuAddDebt);

        addDebts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.action_tabsMenuFragment_to_debtPayFragment);

            }
        });

        Button replaceToManager = view.findViewById(R.id.tebsMenuReplaceManager);

        replaceToManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.action_tabsMenuFragment_to_tabMenueManagerFragment);

            }
        });
    }
}
