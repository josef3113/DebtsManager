package com.example.debtsmanager.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.debtsmanager.R;

public class LottieAnimation extends DialogFragment
{

    LottieAnimationView animationView;
    int animationToPlay;
    TextView animaionText;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = null;

        view = inflater.inflate(R.layout.lottie_animation_fragment,container,false);

        animationView = view.findViewById(R.id.lottie_Animation);
        animaionText = view.findViewById(R.id.lottie_text);


        animationToPlay = getArguments().getInt("animation");

        String string;

        if(getArguments().getString("text") != null)
        {
            string = getArguments().getString("text");
            animaionText.setVisibility(View.VISIBLE);
            animaionText.setText(string);
            animaionText.setTextColor(getResources().getColor(R.color.grey_3));
        }


        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        this.setCancelable(false);

        animationView.setAnimation(animationToPlay);

        animationView.playAnimation();

        return view;
    }


    @Override
    public void onStop()
    {
        animationView.cancelAnimation();
        super.onStop();
    }
}

