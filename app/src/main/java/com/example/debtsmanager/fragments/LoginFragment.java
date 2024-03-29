package com.example.debtsmanager.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.debtsmanager.R;
import com.example.debtsmanager.controllers.FirebaseController;
import com.example.debtsmanager.controllers.Repository;
import com.example.debtsmanager.interfaces.RequestListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

/**
 * A simple {@link Fragment} subclass.
 */

public class LoginFragment extends Fragment
{


    private EditText emailEt;
    private EditText passwordEt;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState)
    {

        final Repository repository = Repository.getInstance();

        getActivity().setTitle("Debt Manager");

        TextView signUpBtn = view.findViewById(R.id.loginSignUpBtn);

        emailEt = view.findViewById(R.id.loginEmailEt);
        passwordEt = view.findViewById(R.id.loginPasswordEt);
        Button submitBtn = view.findViewById(R.id.loginLoginBtn);

        emailEt.requestFocus();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                final DialogAnimation dialogAnimation = new DialogAnimation();

                Bundle bundle = new Bundle();
                bundle.putString("text","Login");
                dialogAnimation.setArguments(bundle);

                dialogAnimation.show(transaction,"dialog");

                repository.login(emailEt.getText().toString(), passwordEt.getText().toString()
                        , new RequestListener() {
                            @Override
                            public void onComplete(Object o)
                            {
                                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_tabsMenuFragment);
                                dialogAnimation.dismiss();
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
