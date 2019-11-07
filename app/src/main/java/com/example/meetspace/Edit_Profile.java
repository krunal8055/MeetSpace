package com.example.meetspace;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_Profile extends Fragment implements View.OnClickListener {

    public NavController navController;
    protected String password;
    protected String confirm_password;
    Context context;
    TextView Emp_id, Designation, EmailID;
    EditText FirstName, LastName, Password, ConfirmPassword;
    Button Save_details;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String UID;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit__profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity().getApplicationContext();
        navController = Navigation.findNavController(view);
        Emp_id = view.findViewById(R.id.emp_id_edit_profile);
        Designation = view.findViewById(R.id.designation_edit_profile);
        FirstName = view.findViewById(R.id.fn_edit_profile);
        LastName = view.findViewById(R.id.ln_edit_profile);
        EmailID = view.findViewById(R.id.email_id_edit_profile);
        Password = view.findViewById(R.id.pwd_edit_profile);
        ConfirmPassword = view.findViewById(R.id.c_pwd_edit_profile);
        Save_details = view.findViewById(R.id.save_edit_profile);
        progressBar = view.findViewById(R.id.progressBar_edit_prf);
        //declaration for firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("User");
        UID = firebaseUser.getUid();

        Save_details.setOnClickListener(this);

        getUserDetails();

    }

    private void getUserDetails() {
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);    //Freeze UI
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    progressBar.setVisibility(View.GONE);
                    //Unfreeze UI
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    String emp_id = dataSnapshot.child(UID).child("Emp_id").getValue().toString();
                    String first_name = dataSnapshot.child(UID).child("Firstname").getValue().toString();
                    String last_name = dataSnapshot.child(UID).child("Lastname").getValue().toString();
                    String designation = dataSnapshot.child(UID).child("Designation").getValue().toString();
                    String email = dataSnapshot.child(UID).child("Email").getValue().toString();
                    Emp_id.setText(emp_id);
                    FirstName.setText(first_name);
                    LastName.setText(last_name);
                    EmailID.setText(email);
                    Designation.setText(designation);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void updateData() {

        progressBar.setVisibility(View.VISIBLE);

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);    //Freeze UI
        if((Password.getText().toString()).isEmpty() && (ConfirmPassword.getText().toString()).isEmpty())
        {

            progressBar.setVisibility(View.GONE);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            //With Firebase Database
            databaseReference.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot!=null) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            //Unfreeze UI
                            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            databaseReference.child(UID).child("Firstname").setValue(FirstName.getText().toString());
                            databaseReference.child(UID).child("Lastname").setValue(LastName.getText().toString());
                            Toast.makeText(context,"Successfully Updated!",Toast.LENGTH_SHORT).show();

                            navController.navigate(R.id.action_edit_Profile_to_homepage);

                        } catch (Exception e) {
                            Toast.makeText(context,"Error :"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        else{
            password = Password.getText().toString();
            confirm_password = ConfirmPassword.getText().toString();
            if(confirm_password.contains(password))
            {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
               // AuthCredential authCredential = EmailAuthProvider.getCredential(EmailID.getText().toString(),)
                firebaseUser.updatePassword(Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            System.out.println("password Updated Successfully!");
                            databaseReference.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot!=null) {
                                        try {
                                            progressBar.setVisibility(View.GONE);
                                            //Unfreeze UI
                                            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                            databaseReference.child(UID).child("Firstname").setValue(FirstName.getText().toString());
                                            databaseReference.child(UID).child("Lastname").setValue(LastName.getText().toString());
                                            databaseReference.child(UID).child("Password").setValue(Password.getText().toString());
                                            Toast.makeText(context,"Successfully Updated!",Toast.LENGTH_SHORT).show();

                                            navController.navigate(R.id.action_edit_Profile_to_homepage);

                                        } catch (Exception e) {
                                            Toast.makeText(context,"Error :"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        }
                        else
                        {
                            System.out.println("Error :"+task.getException());
                            Toast.makeText(context,"Error :"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else
            {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(context,"Password Doesn't match! Try again",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == Save_details) {
            updateData();
        }
    }
}
