package com.example.debtsmanager.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.debtsmanager.R;
import com.example.debtsmanager.controllers.Repository;
import com.example.debtsmanager.interfaces.RequestListener;
import com.example.debtsmanager.models.Debt;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerAddDebtFragment extends Fragment {


    public ManagerAddDebtFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manager_add_debt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        final Repository repository = Repository.getInstance();


        final Spinner namesFromSpinner = view.findViewById(R.id.managerAddDebtFromSpinner);
        final Spinner namesToSpinner = view.findViewById(R.id.managerAddDebtToSpinner);
        final EditText amountET = view.findViewById(R.id.managerAddDebtAmountET);
        Button managerAddDebtBtn = view.findViewById(R.id.managerAddDebtAddDebtBtn);


        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(getContext()
                , R.layout.spinner_item
                , repository.getAllTheUsers());


        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        namesFromSpinner.setAdapter(spinnerAdapter);
        namesToSpinner.setAdapter(spinnerAdapter);



        managerAddDebtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromUser = namesFromSpinner.getSelectedItem().toString();
                String toUser = namesToSpinner.getSelectedItem().toString();
                String amount = amountET.getText().toString();


                boolean amountParsed = false;
                int amountInt = 0;

                try {
                    amountInt = Integer.parseInt(amount);
                    amountParsed = true;
                } catch (NumberFormatException ex) {
                    Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (amountParsed) {

                    Debt newDebt = new Debt(
                            fromUser
                            , toUser
                            , amountInt);


                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                    final DialogAnimation dialogAnimation = new DialogAnimation();

                    Bundle bundle = new Bundle();
                    bundle.putString("text", "Transferring Debt");
                    dialogAnimation.setArguments(bundle);

                    dialogAnimation.show(transaction, "dialog");

                    repository.addDebt(newDebt, new RequestListener() {
                        @Override
                        public void onComplete(Object o) {
                            dialogAnimation.dismiss();
                            Toast.makeText(getContext(), "Debt Transferred", Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                        }

                        @Override
                        public void onError(String msg) {
                            dialogAnimation.dismiss();
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}
