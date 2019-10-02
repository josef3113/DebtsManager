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
import android.widget.Toast;

import com.example.debtsmanager.R;
import com.example.debtsmanager.adapters.DebtToOtherAdapter;
import com.example.debtsmanager.controllers.FirebaseController;
import com.example.debtsmanager.controllers.Repository;
import com.example.debtsmanager.interfaces.DataChangeObserver;
import com.example.debtsmanager.interfaces.RequestListener;
import com.example.debtsmanager.models.Debt;
import com.example.debtsmanager.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DebtToOtherFragment extends Fragment {

    Repository repository;
    FirebaseController firebaseController;

    public DebtToOtherFragment()
    {
        // Required empty public constructor//
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
        repository = Repository.getInstance();

        RecyclerView recyclerView = view.findViewById(R.id.debtToPayRecycleView);

        //ArrayList<Debt> tempdebts = (ArrayList<Debt>) repository.getDebtsToOther();

        firebaseController = FirebaseController.getInstance();

        final DebtToOtherAdapter debtAdapter = new DebtToOtherAdapter(getContext(), (ArrayList<Debt>) repository.getDebtsToOther());
        recyclerView.setAdapter(debtAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        repository.setObserver(new DataChangeObserver() {
            @Override
            public void dataChanged()
            {
                debtAdapter.setlist((ArrayList<Debt>) repository.getDebtsToOther());
                debtAdapter.notifyDataSetChanged();
            }
        });
    }
}
