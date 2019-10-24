package com.example.debtsmanager.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.debtsmanager.R;
import com.example.debtsmanager.controllers.Repository;
import com.example.debtsmanager.interfaces.RequestListener;
import com.example.debtsmanager.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeTypeFragment extends Fragment {
    private User selectedUser;


    public ChangeTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final Repository repository = Repository.getInstance();

        final Spinner namesSpinner = view.findViewById(R.id.managerChangeTypeSpinner);
        final Button managerChangeTypeBtn = view.findViewById(R.id.managerChangeTypeBtn);

        final ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext()
                , R.layout.spinner_item
                , repository.getAllTheUsers());

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        namesSpinner.setAdapter(spinnerAdapter);

        namesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (repository.getAllTheUsers().get(position).isIsmanager()) {
                    managerChangeTypeBtn.setText("Change To Regular User");

                } else {
                    managerChangeTypeBtn.setText("Change To Manager");
                }
                selectedUser = repository.getAllTheUsers().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                managerChangeTypeBtn.setText("Waiting For User");
            }


        });

        managerChangeTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();


                final DialogAnimation dialogAnimation = new DialogAnimation();

                Bundle bundle = new Bundle();
                bundle.putString("text", "Change Type");
                dialogAnimation.setArguments(bundle);

                dialogAnimation.show(transaction, "dialog");


                repository.changeUserType(selectedUser, new RequestListener() {
                    @Override
                    public void onComplete(Object o) {
                        Toast.makeText(getContext(), "Type Changed", Toast.LENGTH_SHORT).show();
                        dialogAnimation.dismiss();
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void onError(String msg) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        dialogAnimation.dismiss();
                    }
                });

            }
        });

    }
}
