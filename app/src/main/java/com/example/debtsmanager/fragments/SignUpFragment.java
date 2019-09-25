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
import com.example.debtsmanager.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    FirebaseController firebaseController;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        firebaseController = FirebaseController.getInstance();

        Button submitBtn = view.findViewById(R.id.signUpSubmitBtn);

        final EditText userNameET = view.findViewById(R.id.signUpUserNameET);
        final EditText emailET = view.findViewById(R.id.signUpEmailET);
        final EditText passwordET = view.findViewById(R.id.signUpPasswordET);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                User user = new User(emailET.getText().toString(),userNameET.getText().toString());

                firebaseController.signUpUser(user, passwordET.getText().toString(), new RequestListener()
                {
                    @Override
                    public void onComplete(Object o)
                    {
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void onError(String msg)
                    {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
