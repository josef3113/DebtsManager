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
import android.widget.Toast;

import com.example.debtsmanager.R;
import com.example.debtsmanager.adapters.DebtToOtherAdapter;
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
        return inflater.inflate(R.layout.fragment_manager_all_debts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {

        repository = Repository.getInstance();

        managerAllDebts = view.findViewById(R.id.managerAllDebts);

        final DebtToOtherAdapter debtAdapter = new DebtToOtherAdapter((ArrayList<Debt>) repository.getAllDebts());

        managerAllDebts.setAdapter(debtAdapter);
        managerAllDebts.setLayoutManager(new LinearLayoutManager(getContext()));

        debtAdapter.setPressReader(this);

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
            public void onClick(DialogInterface dialog, int which) {

                Animation animationFade = AnimationUtils.loadAnimation(getContext(),R.anim.fade);
                animationFade.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        repository.deleteDebt(debt, new RequestListener() {
                            @Override
                            public void onComplete(Object o) {
                                Toast.makeText(getContext(), "Debt Deleted", Toast.LENGTH_SHORT).show();
                                managerAllDebts.getAdapter().notifyDataSetChanged();
                            }

                            @Override
                            public void onError(String msg) {
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                        });


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                managerAllDebts.getChildAt(repository.getAllDebts().indexOf(debt))
                        .startAnimation(animationFade);
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
