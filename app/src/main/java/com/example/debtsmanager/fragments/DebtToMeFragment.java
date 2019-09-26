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
import com.example.debtsmanager.interfaces.RequestListener;
import com.example.debtsmanager.models.Debt;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DebtToMeFragment extends Fragment {

    FirebaseController firebaseController;

    public DebtToMeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_debt_to_me, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        try {


            RecyclerView recyclerView = view.findViewById(R.id.debtToOtherFragmentRecycleView);

            ArrayList<Debt> tempdebts = new ArrayList<>();

            firebaseController = FirebaseController.getInstance();

            final DebtToOtherAdapter debtAdapter = new DebtToOtherAdapter(getContext(), tempdebts);
            recyclerView.setAdapter(debtAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            firebaseController.debtToOthers(new RequestListener() {
                @Override
                public void onComplete(Object o) {
                    List<Debt> debts1 = (List<Debt>) o;

                    debtAdapter.setlist(debts1);
                }

                @Override
                public void onError(String msg) {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception ex)
        {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
