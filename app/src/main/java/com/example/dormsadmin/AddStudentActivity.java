package com.example.dormsadmin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dormsadmin.Model.Student;
import com.example.dormsadmin.databinding.ActivityAddStudentBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import co.nedim.maildroidx.MaildroidX;
import co.nedim.maildroidx.MaildroidXType;

public class AddStudentActivity extends AppCompatActivity {

    ActivityAddStudentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                profSelcResult.launch(chooserIntent);
            }
        });


        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!IS_PROF_CHOOSE){
                    Toast.makeText(AddStudentActivity.this, "Select profile picture", Toast.LENGTH_SHORT).show();
                    return;
                }
                gatherdata();
            }

        });
    }

    Student student;
    void gatherdata(){

        binding.progress.setVisibility(View.VISIBLE);
        student = new Student();
        student.setFull_name(binding.nameStu.getText().toString());
        student.setPhone(binding.phoneNoStu.getText().toString());
        student.setAddress(binding.addressInp.getText().toString());
        student.setEmail(binding.emailStu.getText().toString());
        student.setGname(binding.guardianName.getText().toString());
        student.setGphonenumber(binding.phoneNoGuard.getText().toString());
        student.setStudId(String.valueOf(System.currentTimeMillis()));

        student.setPassword(binding.password.getText().toString());
        student.setCollege(binding.collegeInp.getText().toString());


        student.setAdminId(FirebaseAuth.getInstance().getUid());
        uploadProfileImage(student.getStudId())
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful()){

                            student.setProfileUrl(task.getResult().toString());

                            FirebaseFirestore.getInstance()
                                    .collection("Student")
                                    .document(student.getStudId())
                                    .set(student)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            binding.progress.setVisibility(View.GONE);

                                            if (task.isSuccessful()) {

                                                Toast.makeText(AddStudentActivity.this, " Student added successfully", Toast.LENGTH_SHORT).show();
                                                sendMail();

                                                AppSingleton.getINSTANCE().setSelectedStudent(student);
                                                startActivity(new Intent(AddStudentActivity.this,StudentDetailActivity.class));
                                                finish();

                                            } else
                                                Toast.makeText(AddStudentActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(AddStudentActivity.this, "Failed add student", Toast.LENGTH_SHORT).show();
                            Toast.makeText(AddStudentActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    Boolean IS_PROF_CHOOSE = false;
    ActivityResultLauncher<Intent> profSelcResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if(data != null){
                            Uri selectedImage = data.getData();
                            binding.imgStu.setImageURI(selectedImage);
                            IS_PROF_CHOOSE = true;
                        }
                    }
                }
            });


    public Task<Uri> uploadProfileImage(String studId){

        StorageReference refs = FirebaseStorage.getInstance().getReference().child("Profile/" + studId);

        binding.imgStu.setDrawingCacheEnabled(true);
        binding.imgStu.buildDrawingCache();
        Bitmap profileBit = ((BitmapDrawable) binding.imgStu.getDrawable()).getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        profileBit.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = refs.putBytes(data);

        return uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return refs.getDownloadUrl();
            }
        });
    }


    void sendMail(){
        new MaildroidX.Builder()
                .smtp("smtp.gmail.com")
                .smtpUsername("dormshostel@gmail.com")
                .smtpPassword("wrubbhlykjaknxga")
                .port("465")
                .type(MaildroidXType.HTML)
                .to(student.getEmail())
                .from("dormshostel@gmail.com")
                .subject("Welcome to Dorms Hostel App")
                .body("Hi " + student.getFull_name()
                +"\n\n"+
                        "This is a confiration mail regarding your account creation\nDownload dorms students and and login using the following credentials\n\n" +
                                "Username : " + student.getEmail()
                        + "\nPassword : " + student.getPassword() +"\n\n\nThank you."
                )
                .onCompleteCallback(new MaildroidX.onCompleteCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(AddStudentActivity.this, "Email had sent to student", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(String s) {
                        Toast.makeText(AddStudentActivity.this, "Failed to send email", Toast.LENGTH_SHORT).show();
                        Toast.makeText(AddStudentActivity.this, s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public long getTimeout() {
                        return 5000;
                    }
                })
                .mail();
    }

}