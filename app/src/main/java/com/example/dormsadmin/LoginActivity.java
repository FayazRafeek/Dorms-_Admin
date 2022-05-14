package com.example.dormsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.dormsadmin.Model.Admin;
import com.example.dormsadmin.databinding.ActivityLoginDormsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginDormsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginDormsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLogin();
            }
        });

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


    }

    void startLogin(){

        String email = binding.uName.getText().toString().trim();
        String password = binding.passwd.getText().toString().trim();

        if(email == null | email.isEmpty()){
            binding.uName.setError("Email required");
            return;
        }
        if(password == null | password.isEmpty()){
            binding.passwd.setError("Password must be greater than 5 characters");
            return;
        }

        binding.progress.setVisibility(View.VISIBLE);

        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseFirestore.getInstance().collection("Admin")
                            .document(FirebaseAuth.getInstance().getUid())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    binding.progress.setVisibility(View.GONE);
                                    if(task.isSuccessful()){
                                        Toast.makeText(LoginActivity.this,"Logged in successfully", Toast.LENGTH_SHORT).show();
                                        saveAdmin(task.getResult().toObject(Admin.class));
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                    } else {
                                        Toast.makeText(LoginActivity.this,"failed to get admin data", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                } else {
                    binding.progress.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,"Error ! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void saveAdmin(Admin admin){
        SharedPreferences.Editor editor = getSharedPreferences("PREF_DORMS",MODE_PRIVATE).edit();
        editor.putBoolean("IS_LOGIN",true);
        Gson gson = new Gson();
        String json = gson.toJson(admin);
        editor.putString("ADMIN_DATA",json);
        editor.commit();
    }

}