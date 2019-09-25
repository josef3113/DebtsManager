package com.example.debtsmanager.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.debtsmanager.R;
import com.example.debtsmanager.adapters.DebtToOtherAdapter;
import com.example.debtsmanager.models.Debt;
import com.example.debtsmanager.models.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DebtToOtherFragment extends Fragment {


    public DebtToOtherFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_debt_to_other, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        RecyclerView recyclerView = view.findViewById(R.id.debtToPayRecycleView);

        ArrayList<Debt> debts = new ArrayList<>();
        debts.add(new Debt(new User("a","a"),new User("b","b"),50));
        debts.add(new Debt(new User("a","a"),new User("c","c"),150));
        debts.add(new Debt(new User("a","a"),new User("d","d"),250));

        DebtToOtherAdapter debtAdapter = new DebtToOtherAdapter(getContext(),debts);
        recyclerView.setAdapter(debtAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
