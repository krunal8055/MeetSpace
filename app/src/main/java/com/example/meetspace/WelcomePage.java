package com.example.meetspace;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomePage extends Fragment {
    Context context;
    NavController navController;

    public WelcomePage() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome_page, container, false);
    }
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        context = getActivity().getApplicationContext();
        ImageView imageView = view.findViewById(R.id.imageView3);
        TextView textView = view.findViewById(R.id.name);
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.fade);
        imageView.startAnimation(animation);
        textView.startAnimation(animation);

        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    navController.navigate(R.id.action_welcomePage_to_loginPage);

                    super.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }
}
