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

        final Repository repository = Repository.getInstance();

        firebaseController = FirebaseController.getInstance();

        Button addDebtBtn = view.findViewById(R.id.addDebtAddDebtBtn);

        final EditText amountET = view.findViewById(R.id.addDebtAmountET);

        final Spinner namesSpinner = view.findViewById(R.id.usersNameSpinner);
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(getContext()
                ,R.layout.spinner_item
                ,repository.getAllTheUsers());


        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        namesSpinner.setAdapter(spinnerAdapter);

        addDebtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String toUser = namesSpinner.getSelectedItem().toString();
                final String amount = amountET.getText().toString();

                if(toUser.equals(repository.getCurrentUser().getName()))
                {
                    Toast.makeText(getContext(),"Cant choose yourself",Toast.LENGTH_SHORT).show();
                }else
                {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                    Bundle bundle = new Bundle();

                    final LottieAnimation lottieAnimation = new LottieAnimation();

                    bundle.putInt("animation",R.raw.exchange);
                    lottieAnimation.setArguments(bundle);

                    lottieAnimation.show(transaction,"lottieDialog");

                    Debt newDebt = new Debt(repository.getCurrentUser().getName()
                            ,toUser
                            ,Integer.parseInt(amount));

                    repository.addDebt(newDebt, new RequestListener() {
                        @Override
                        public void onComplete(Object o) {
                            lottieAnimation.dismiss();
                            getActivity().onBackPressed();
                        }

                        @Override
                        public void onError(String msg) {
                            lottieAnimation.dismiss();
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}


