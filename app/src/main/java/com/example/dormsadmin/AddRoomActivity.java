package com.example.dormsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dormsadmin.Model.Room;
import com.example.dormsadmin.databinding.ActivityRoomDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddRoomActivity extends AppCompatActivity {

    String[] items = {"2","3","4","5","6"};
    ArrayAdapter<String> adapterItem;
    String bedsSt = "0";
    ActivityRoomDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding  = ActivityRoomDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapterItem = new ArrayAdapter<String>(this,R.layout.list_item,items);
        binding.beds.setAdapter(adapterItem);

        binding.beds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(AddRoomActivity.this, "Selected "+items[i], Toast.LENGTH_SHORT).show();
                bedsSt = items[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        binding.addroomDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.progress.setVisibility(View.VISIBLE);
            Toast.makeText(AddRoomActivity.this, "Adding room", Toast.LENGTH_SHORT).show();
            Room room = new Room();
            room.setName(binding.roomName.getText().toString());
            room.setRent(binding.rent.getText().toString());
            room.setBeds(binding.beds.getText().toString());
            room.setRoomId(System.currentTimeMillis() + "");

            FirebaseFirestore.getInstance()
                    .collection("Admin")
                    .document(FirebaseAuth.getInstance().getUid())
                    .collection("Rooms")
                    .document(room.getRoomId())
                    .set(room)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            binding.progress.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(AddRoomActivity.this, "Room added successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else
                                Toast.makeText(AddRoomActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

            }

        });



    }

}