package com.example.meetspace;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ForgetPasswordPage extends Fragment implements View.OnClickListener {
    NavController navController;
    TextInputLayout Email_for_Forget_Password;
    Button send_Verification_mail_button;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String email;
    Context context;
    ProgressBar progressBar;
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
        context = getActivity().getApplicationContext();
        progressBar = view.findViewById(R.id.progress_bar_forgetPassword);
        navController = Navigation.findNavController(view);
        Email_for_Forget_Password = view.findViewById(R.id.email_forget_password);
        send_Verification_mail_button = view.findViewById(R.id.send_verification_mail_button);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("ForgotPassword");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        send_Verification_mail_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == send_Verification_mail_button)
        {progressBar.setVisibility(View.VISIBLE);
            email = Email_for_Forget_Password.getEditText().getText().toString();
            if(TextUtils.isEmpty(email)) {
                progressBar.setVisibility(View.GONE);
                Email_for_Forget_Password.setError("Please Enter Valid Email id.");
            }
            else {
                progressBar.setVisibility(View.GONE);
                firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Password Resetlink sent Successfully!", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.action_forgetPasswordPage_to_loginPage);
                    }
                });
            }
        }
    }
}
