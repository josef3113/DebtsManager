package com.example.debtsmanager.fragments;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.debtsmanager.R;
import com.example.debtsmanager.adapters.DebtToOtherAdapter;
import com.example.debtsmanager.controllers.Repository;
import com.example.debtsmanager.interfaces.DataChangeObserver;
import com.example.debtsmanager.interfaces.LongPressReader;
import com.example.debtsmanager.models.Debt;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DebtToMeFragment extends Fragment implements LongPressReader<Debt> {

    private Repository repository;
    private RecyclerView recyclerView;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        repository = Repository.getInstance();

        recyclerView = view.findViewById(R.id.debtToOtherFragmentRecycleView);


        final DebtToOtherAdapter debtAdapter = new DebtToOtherAdapter((ArrayList<Debt>) repository.getDebtsToMe());
        recyclerView.setAdapter(debtAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        repository.setObserver(new DataChangeObserver() {
            @Override
            public void dataChanged() {
                debtAdapter.setlist(repository.getDebtsToMe());
                debtAdapter.notifyDataSetChanged();
            }
        });


        debtAdapter.setPressReader(this);

    }

    @Override
    public void onClicked(final Debt debt) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Are you sure you want to remove this debt?");


        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Animation animationFade = AnimationUtils.loadAnimation(getContext(), R.anim.fade);
                recyclerView.getChildAt(repository.getDebtsToMe().indexOf(debt))
                        .startAnimation(animationFade);

                dialog.dismiss();
                for (int i = 0; i < 10000000; i++) ;
                repository.deleteDebt(debt);
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }
}
