package com.canberkgulgun.instagramcloneandroid.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.canberkgulgun.instagramcloneandroid.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth auth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);




        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();

        if (user != null){
            Intent intent = new Intent(LoginActivity.this , FeedActivity.class);
            startActivity(intent);
        }

    }

    public void loginButtonClicked(View view) {
        String email = binding.emailTxt.getText().toString();
        String password = binding.passwordTxt.getText().toString();

        if (email.equals(" ") || password.equals(" ")){

            Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_LONG).show();
        }else{

            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intentToFeed = new Intent(LoginActivity.this , FeedActivity.class);
                    startActivity(intentToFeed);
                    finish();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    public void registerButtonClicked(View view){

        Intent intentToRegister = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intentToRegister);



    }



}