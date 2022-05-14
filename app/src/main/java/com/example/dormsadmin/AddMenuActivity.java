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

import com.example.dormsadmin.Model.FoodItem;
import com.example.dormsadmin.databinding.ActivityAddBreakfastBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class AddMenuActivity extends AppCompatActivity {

    ActivityAddBreakfastBinding binding;
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddBreakfastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        type = getIntent().getStringExtra("TYPE");
        binding.typeHeader.setText("Add " + type + " menu");

        binding.menuImage.setOnClickListener(new View.OnClickListener() {
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

        
        binding.addButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private static final String TAG = "333";


    void addItem(){
        if(!IS_PROF_CHOOSE){
            Toast.makeText(AddMenuActivity.this, "Choose a image for item", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.progress.setVisibility(View.VISIBLE);

        FoodItem foodItem = new FoodItem();
        String name = binding.iName.getText().toString();
        String price = binding.price.getText().toString();
        foodItem.setItemId(String.valueOf(System.currentTimeMillis()));
        foodItem.setItemName(name);
        foodItem.setPrice(price);
        foodItem.setType(type);

        binding.progress.setVisibility(View.VISIBLE);
        uploadProfileImage(foodItem.getItemId())
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful()){

                            foodItem.setImgUrl(task.getResult().toString());

                            FirebaseFirestore.getInstance()
                                    .collection("Admin")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .collection(type)
                                    .document(foodItem.getItemId())
                                    .set(foodItem)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            binding.progress.setVisibility(View.GONE);
                                            if(task.isSuccessful()){
                                                Toast.makeText(AddMenuActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(AddMenuActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            };
                                        }
                                    });
                        } else {
                            Toast.makeText(AddMenuActivity.this, "Failed to add image", Toast.LENGTH_SHORT).show();
                            binding.progress.setVisibility(View.GONE);
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
                            binding.menuImage.setImageURI(selectedImage);
                            IS_PROF_CHOOSE = true;
                        }
                    }
                }
            });

    public Task<Uri> uploadProfileImage(String itemId){

        StorageReference refs = FirebaseStorage.getInstance().getReference().child("Admin/" + FirebaseAuth.getInstance().getUid() + "/Food/"+type+"/" + itemId);

        binding.menuImage.setDrawingCacheEnabled(true);
        binding.menuImage.buildDrawingCache();
        Bitmap profileBit = ((BitmapDrawable) binding.menuImage.getDrawable()).getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        profileBit.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = refs.putBytes(data);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Log.d(TAG, "onComplete: Upload Completed.. " );
                Log.d(TAG, task.getException() + "" );
            }
        });

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

}