package com.example.meetspace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;


public class ForgetPasswordPage extends Fragment {
    NavController navController;
    TextInputLayout Email_for_Forget_Password;
    Button send_Verification_mail_button;

    public ForgetPasswordPage() {

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forget_password_page, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        Email_for_Forget_Password = view.findViewById(R.id.email_forget_password);
        send_Verification_mail_button = view.findViewById(R.id.send_verification_mail_button);
        String email = Email_for_Forget_Password.getEditText().getText().toString().trim();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("ForgetPassword");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
