package com.example.dormsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dormsadmin.Model.Admin;
import com.example.dormsadmin.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity {

    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.goReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = binding.email.getText().toString().trim();
                String name = binding.name.getText().toString().trim();
                String phone = binding.phoneNo.getText().toString().trim();
                String password = binding.password.getText().toString().trim();

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Admin admin = new Admin(FirebaseAuth.getInstance().getUid(),name,password,email,phone);

                            FirebaseFirestore.getInstance()
                                    .collection("Admin")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .set(admin)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                Toast.makeText(RegisterActivity.this,"User Created", Toast.LENGTH_SHORT).show();

                                                SharedPreferences.Editor editor = getSharedPreferences("PREF_DORMS",MODE_PRIVATE).edit();
                                                editor.putBoolean("IS_LOGIN",true);
                                                Gson gson = new Gson();
                                                String json = gson.toJson(admin);
                                                editor.putString("ADMIN_DATA",json);
                                                editor.commit();

                                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                            }
                                        }
                                    });

                        }else {
                            Toast.makeText(RegisterActivity.this,"Error ! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }

        });
    }
}