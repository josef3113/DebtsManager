package com.example.debtsmanager.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
    Repository repository;
    Button replaceToManager;
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

        repository = Repository.getInstance();

        getActivity().setTitle(repository.getCurrentUser().getName());

        Button myDebts = view.findViewById(R.id.tebsMenuMyDebts);
        replaceToManager = view.findViewById(R.id.tebsMenuReplaceManager);
        Button addDebts = view.findViewById(R.id.tebsMenuAddDebt);
        Button otherDebts = view.findViewById(R.id.tebsMenuOtherDebts);

        if(!repository.getCurrentUser().isIsmanager())
        {
            replaceToManager.setVisibility(View.GONE);
        }else
        {
            replaceToManager.setVisibility(View.VISIBLE);
        }

        myDebts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.action_tabsMenuFragment_to_debtToOtherFragment);

            }
        });


        otherDebts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.action_tabsMenuFragment_to_debtToMeFragment);

            }
        });


        addDebts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.action_tabsMenuFragment_to_debtPayFragment);

            }
        });


        replaceToManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Navigation.findNavController(view).navigate(R.id.action_tabsMenuFragment_to_tabMenueManagerFragment);

            }
        });
    }

}
