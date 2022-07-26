package com.example.netrootfb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.netrootfb.Model.Users;
import com.example.netrootfb.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
ActivitySignUpBinding binding;
private FirebaseAuth mauth;
private FirebaseDatabase database;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        mauth=FirebaseAuth.getInstance();
        database =FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Please wait a while");
     binding.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             progressDialog.show();

             mauth.createUserWithEmailAndPassword(binding.etEmail.getText().toString(),
                     binding.etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         Users user=new Users(binding.etUserName.getText().toString(),binding.etEmail.getText().toString(),
                                 binding.etPassword.getText().toString());
                         String id=task.getResult().getUser().getUid();
                         database.getReference().child("Users").child(id).setValue(user);
                         Toast.makeText(SignUpActivity.this, "Successfully created", Toast.LENGTH_SHORT).show();
                         progressDialog.dismiss();
                         mauth.signOut();
                         startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                         finish();

                     }else{
                         Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                         progressDialog.dismiss();
                     }
                 }
             });

         }
     });

     binding.tvlogin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             startActivity(new Intent(getApplicationContext(),SignInActivity.class));
         }
     });

    }
}