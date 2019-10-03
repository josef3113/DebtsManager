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
import android.widget.TextView;

import com.example.debtsmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabMenueManagerFragment extends Fragment {


    public TabMenueManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tab_menue_manager,container,false) ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {

        Button managerAddDebt = view.findViewById(R.id.tebsMenuManagerAddDebt);

        managerAddDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.action_tabMenueManagerFragment_to_managerAddDebtFragment);

            }
        });

        Button managerAllDebts = view.findViewById(R.id.tebsMenuMAnagerAllDebts);

        managerAllDebts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.action_tabMenueManagerFragment_to_managerAllDebtsFragment);

            }
        });

    }
}
