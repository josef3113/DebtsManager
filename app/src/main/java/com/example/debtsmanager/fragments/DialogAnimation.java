package com.example.debtsmanager.fragments;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.debtsmanager.MainActivity;
import com.example.debtsmanager.R;

public class DialogAnimation extends DialogFragment
{


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view;

        view = inflater.inflate(R.layout.dialog_fragment_animation,container,false);

        TextView animaionText = view.findViewById(R.id.dialog_animation_text);
        ImageView image = view.findViewById(R.id.dialog_animation_image);

        Animation animationBlink = AnimationUtils.loadAnimation(getContext(),R.anim.blink);

        image.startAnimation(animationBlink);


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

        return view;
    }

    @Override
    public int show(@NonNull FragmentTransaction transaction, @Nullable String tag)
    {
        MainActivity.getInstance().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        return super.show(transaction,tag);
    }

    @Override
    public void dismiss()
    {
        MainActivity.getInstance().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        super.dismiss();
    }
}

