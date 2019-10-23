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
import com.example.debtsmanager.controllers.FirebaseController;
import com.example.debtsmanager.controllers.Repository;
import com.example.debtsmanager.interfaces.RequestListener;
import com.example.debtsmanager.models.Debt;

/**
 * A simple {@link Fragment} subclass.
 */
public class DebtPayFragment extends Fragment {

    public DebtPayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_debt_pay, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        final Repository repository = Repository.getInstance();


        Button addDebtBtn = view.findViewById(R.id.addDebtAddDebtBtn);

        final EditText amountET = view.findViewById(R.id.addDebtAmountET);

        final Spinner namesSpinner = view.findViewById(R.id.usersNameSpinner);
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(getContext()
                , R.layout.spinner_item
                , repository.getAllTheUsers());

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        namesSpinner.setAdapter(spinnerAdapter);



        addDebtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toUser = namesSpinner.getSelectedItem().toString();
                String amount = amountET.getText().toString();

                boolean amountParsed = true;
                int amountInt = 0;

                try {
                    amountInt = Integer.parseInt(amount);
                } catch (NumberFormatException ex) {
                    amountParsed = false;
                    Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (amountParsed)
                {


                    Debt newDebt = new Debt(repository.getCurrentUser().getName()
                            , toUser
                            , amountInt);

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                    Bundle bundle = new Bundle();

                    final DialogAnimation dialogAnimation = new DialogAnimation();

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


