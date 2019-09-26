package com.example.debtsmanager.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.debtsmanager.R;
import com.example.debtsmanager.controllers.FirebaseController;
import com.example.debtsmanager.interfaces.RequestListener;
import com.example.debtsmanager.models.Debt;
import com.example.debtsmanager.models.User;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DebtPayFragment extends Fragment {

    FirebaseController firebaseController;


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

        try {


            firebaseController = FirebaseController.getInstance();

            Button addDebtBtn = view.findViewById(R.id.addDebtAddDebtBtn);

            final EditText debtToET = view.findViewById(R.id.addDebtToET);
            final EditText amountET = view.findViewById(R.id.addDebtAmountET);

            addDebtBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String toUser = debtToET.getText().toString();
                    final String amount = amountET.getText().toString();


                    firebaseController.addDebt(toUser, amount, new RequestListener() {
                        @Override
                        public void onComplete(Object o) {
                            debtToET.setText("");
                            amountET.setText("");
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String msg) {
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });
        }catch (Exception ex)
        {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}


