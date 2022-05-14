package com.example.dormsadmin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dormsadmin.Model.Complaint;
import com.example.dormsadmin.databinding.ActivityComplaintDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ComplaintDetailActivity extends AppCompatActivity {


    ActivityComplaintDetailBinding binding;
    Complaint complaint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityComplaintDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        complaint = AppSingleton.getINSTANCE().getSelectedComplaint();
        setCompDetail();

        binding.replyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitReply();
            }
        });
        refetchComplaint();

        binding.replyDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteReply();
            }
        });

        binding.studItem.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComplaintDetailActivity.this, StudentDetailActivity.class);
                intent.putExtra("STUDENT_ID",complaint.getUserId());
                startActivity(intent);
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void setCompDetail(){

        binding.studItem.studName.setText(complaint.getStudName());

        Date date = new Date();
        date.setTime(Long.parseLong(complaint.getTimestamp()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        String time = dateFormat.format(date);

        binding.timeTxt.setText(time);

        binding.compTxt.setText(complaint.getComplaint());

        if(complaint.getStatus().equals("SUBMITTED"))
            updateCompViewStatus();


        if(complaint.getReply() != null && complaint.getReply().length() > 0){
            binding.responseItem.setVisibility(View.VISIBLE);
            binding.replyTxt.setText(complaint.getReply());
            binding.replyParent.setVisibility(View.GONE);
        } else {
            binding.responseItem.setVisibility(View.GONE);
            binding.replyParent.setVisibility(View.VISIBLE);
        }
    }


    void updateCompViewStatus(){

        FirebaseFirestore.getInstance()
                .collection("Complaints")
                .document(complaint.getComplaintId())
                .update("status","VIEWED")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

    }

    void submitReply(){
        String reply = binding.replyInp.getText().toString();
        complaint.setReply(reply);
        complaint.setStatus("RESPONDED");

        FirebaseFirestore.getInstance()
                .collection("Complaints")
                .document(complaint.getComplaintId())
                .set(complaint)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ComplaintDetailActivity.this, "Reply posted successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ComplaintDetailActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    void deleteReply(){

        complaint.setReply(null);
        complaint.setStatus("VIEWED");

        FirebaseFirestore.getInstance()
                .collection("Complaints")
                .document(complaint.getComplaintId())
                .set(complaint)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ComplaintDetailActivity.this, "Reply deleted successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ComplaintDetailActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    void refetchComplaint(){

        FirebaseFirestore.getInstance()
                .collection("Complaints")
                .document(complaint.getComplaintId())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){
                            Toast.makeText(ComplaintDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Complaint doc = value.toObject(Complaint.class);
                        if(doc != null)
                        complaint = doc;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setCompDetail();
                            }
                        },2000);


                    }
                });
    }

}
