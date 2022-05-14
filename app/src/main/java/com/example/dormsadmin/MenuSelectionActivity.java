package com.example.dormsadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dormsadmin.Model.MenuSelection;
import com.example.dormsadmin.Model.Student;
import com.example.dormsadmin.databinding.ActivityMenuSelectionBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MenuSelectionActivity extends AppCompatActivity implements StudentListAdapter.OnStudClick {

    ActivityMenuSelectionBinding binding;
    String type = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuSelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        type = getIntent().getStringExtra("TYPE");
        binding.typeHeader.setText(type + " Menu Selections");
        setcalRange();

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void setcalRange(){

        Calendar maxCal = Calendar.getInstance();
        maxCal.add(7,Calendar.DAY_OF_MONTH);
        binding.calView.setMaxDate(maxCal.getTimeInMillis());
        binding.calView.setMinDate(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance();
        int m = calendar.get(Calendar.MONTH) + 1;
        selectedDate = calendar.get(Calendar.DAY_OF_MONTH) + "/" + m +"/" + calendar.get(Calendar.YEAR);
        fetchMenuSelection();
        binding.calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                int month = i1 + 1;
                selectedDate = i2 + "/" + month + "/" + i;
                fetchMenuSelection();
            }
        });
    }

    String selectedDate = "";
    void fetchMenuSelection(){
        binding.swipe.setRefreshing(true);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {

            Date d = sdf.parse(selectedDate);
            selectionList = new ArrayList<>();
            updateRecycler();

            FirebaseFirestore.getInstance().collection("Admin")
                    .document(FirebaseAuth.getInstance().getUid())
                    .collection("Selection")
                    .whereEqualTo("foodType",type)
                    .whereEqualTo("menuDate",d.getTime())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            binding.swipe.setRefreshing(false);
                            if(task.isSuccessful()){
                                for (DocumentSnapshot doc : task.getResult()){
                                    selectionList.add(doc.toObject(MenuSelection.class));
                                }
                                updateRecycler();
                                
                                if(task.getResult().isEmpty())
                                    Toast.makeText(MenuSelectionActivity.this, "No Entries found", Toast.LENGTH_SHORT).show();
                            } else{
                                Toast.makeText(MenuSelectionActivity.this, "Failed to fetch", Toast.LENGTH_SHORT).show();
                                Toast.makeText(MenuSelectionActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } catch (ParseException e){
            Log.d("333", "fetchMenuSelection: Parse error" + e);
        }

    }

    StudentListAdapter studentListAdapter;
    List<MenuSelection> selectionList;
    void updateRecycler(){

        if(studentListAdapter == null){
            studentListAdapter = new StudentListAdapter(this,this,"MENU_SELECTION");
            binding.menuRecycler.setAdapter(studentListAdapter);
            binding.menuRecycler.setLayoutManager(new LinearLayoutManager(this));
        }

        List<Student> stList = new ArrayList<>();
        for (MenuSelection m : selectionList){
            stList.add(new Student(m.getStudId(),m.getStudentName(),String.valueOf(m.getFoodItems().size())));
        }
        studentListAdapter.updateList(stList);

    }

    @Override
    public void onStudClick(Student data) {

        for (MenuSelection m : selectionList){
            if(m.getStudId().equals(data.getStudId())){
                AppSingleton.getINSTANCE().setSelectedMenu(m);
                Intent intent = new Intent(MenuSelectionActivity.this,MenuListActivity.class);
                intent.putExtra("IS_SELECTION",true);
                intent.putExtra("TYPE",type);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onStudDelete(Student student) {

    }
}
