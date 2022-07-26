package com.example.netrootfb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.netrootfb.databinding.ActivitySignInBinding;
import com.example.netrootfb.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
ActivitySignInBinding binding;
FirebaseAuth mauth;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mauth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("log In");
        progressDialog.setMessage("loging in to your account");

       binding.btnLogIn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                progressDialog.show();
               mauth.signInWithEmailAndPassword(binding.etlEmail.getText().toString(),binding.etlPassword.getText().toString())
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               progressDialog.dismiss();
                               if(task.isSuccessful()){
                                   Toast.makeText(SignInActivity.this, "successfully login", Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(SignInActivity.this,MainActivity.class));
                                   finish();
                               }else{
                                   Toast.makeText(SignInActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }
                       });
           }
       });
       if(mauth.getCurrentUser() != null){
           startActivity(new Intent(getApplicationContext(),MainActivity.class));
       }
        binding.tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
            }
        });

    }
}