package com.example.meetspace;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginPage extends Fragment implements View.OnClickListener {
    TextInputLayout email_id,password;
    Button Login,ForgetPassword;
    ProgressBar progressBar;
    NavController navController;
    Context context;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    private String Email_ID;
    private String PASSWORD;


    public LoginPage() {
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI(user);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    /*@Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


     return inflater.inflate(R.layout.fragment_login_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        context = getActivity().getApplicationContext();
        firebaseAuth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(view);

        progressBar = view.findViewById(R.id.progressBar_cyclic);
        email_id = view.findViewById(R.id.email_id);
        password = view.findViewById(R.id.password);
        Login = view.findViewById(R.id.login_button);
        ForgetPassword = view.findViewById(R.id.forget_password);
        progressBar.setVisibility(View.GONE);

        Login.setOnClickListener(this);
        ForgetPassword.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
            if(view == Login)
            {
                Email_ID = email_id.getEditText().getText().toString().trim();
                PASSWORD = password.getEditText().getText().toString().trim();
                if(TextUtils.isEmpty(Email_ID))
                {
                    email_id.setError("Please Enter Email id");
                    //Toast.makeText(getActivity().getApplicationContext(),"",Toast.LENGTH_LONG).show();

                }
                if(TextUtils.isEmpty(PASSWORD))
                {
                    password.setError("Please Enter Password");
                    //Toast.makeText(getActivity().getApplicationContext(),"Please Enter Password",Toast.LENGTH_LONG).show();
                }
                if(!TextUtils.isEmpty(Email_ID) && !TextUtils.isEmpty(PASSWORD))
                {
                    Signin_Home();
                }
        }
        else if(view == ForgetPassword)
        {
            navController.navigate(R.id.action_loginPage_to_forgetPasswordPage);
            //Toast.makeText(getActivity().getApplicationContext(),"Click on Forget Password!", Toast.LENGTH_LONG).show();
        }
    }
    public void Signin_Home()
    {
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        firebaseAuth.signInWithEmailAndPassword(Email_ID,PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if(task.isSuccessful())
                {
                    updateUI(user);
                    Intent i = new Intent(context,Main2Activity.class);
                    startActivity(i);
                    getActivity().finish();
                    //firebaseUser = firebaseAuth.getCurrentUser();
                    Toast.makeText(getActivity().getApplicationContext(),"Successfully Logged in!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(),"There is some Problem. Please Try Again!", Toast.LENGTH_LONG).show();
                    updateUI(null);
                }

            }
        });


    }
    public void updateUI(FirebaseUser user) {

        user = firebaseAuth.getCurrentUser();
        progressBar.setVisibility(View.GONE);
        if(user != null)
        {
            Intent i = new Intent(context,Main2Activity.class);
            startActivity(i);
            getActivity().finish();
        }
    }
}
