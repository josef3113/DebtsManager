package com.example.debtsmanager.fragments;


import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.debtsmanager.R;
import com.example.debtsmanager.adapters.DebtAdapter;
import com.example.debtsmanager.controllers.Repository;
import com.example.debtsmanager.interfaces.DataChangeObserver;
import com.example.debtsmanager.interfaces.LongPressReader;
import com.example.debtsmanager.interfaces.RequestListener;
import com.example.debtsmanager.models.Debt;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerAllDebtsFragment extends Fragment implements LongPressReader<Debt>
{

    private Repository repository;
    private RecyclerView managerAllDebts;

    public ManagerAllDebtsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_debts_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {

        repository = Repository.getInstance();

        managerAllDebts = view.findViewById(R.id.debtsRecycleView);

        final DebtAdapter debtAdapter = new DebtAdapter((ArrayList<Debt>) repository.getAllDebts());
        debtAdapter.setPressReader(this);

        managerAllDebts.setAdapter(debtAdapter);
        managerAllDebts.setLayoutManager(new LinearLayoutManager(getContext()));


        repository.setObserver(new DataChangeObserver() {
            @Override
            public void dataChanged()
            {
                debtAdapter.setlist(repository.getAllDebts());
                debtAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onClicked(final Debt debt) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Are you sure you want to remove this debt?");

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                final DialogAnimation dialogAnimation = new DialogAnimation();

                Bundle bundle = new Bundle();
                bundle.putString("text", "Delete From DB");
                dialogAnimation.setArguments(bundle);

                dialogAnimation.show(transaction, "dialog");

                repository.deleteDebt(debt, new RequestListener() {
                    @Override
                    public void onComplete(Object o) {

                        Toast.makeText(getContext(), "Debt Deleted", Toast.LENGTH_SHORT).show();
                        final Animation animationFade = AnimationUtils.loadAnimation(getContext(), R.anim.fade);
                        managerAllDebts.startAnimation(animationFade);
                        dialogAnimation.dismiss();
                    }

                    @Override
                    public void onError(String msg) {
                        dialogAnimation.dismiss();
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }
}
