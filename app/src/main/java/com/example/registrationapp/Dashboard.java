package com.example.registrationapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Adapters.CoursesAdapter;
import Adapters.CoursesSetGet;

public class Dashboard extends AppCompatActivity {
    RecyclerView recyclerView;
    CoursesAdapter adapter;
    RelativeLayout uploadbtn;
    AlertDialog new_course_dialog;

    private List<CoursesSetGet> coursesSetGetList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        uploadbtn=findViewById(R.id.rl_uploadButton);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
                View popupView = LayoutInflater.from(Dashboard.this).inflate(R.layout.new_course, null);
                builder.setView(popupView);
                new_course_dialog = builder.create();
                new_course_dialog.show();
                Button upload=popupView.findViewById(R.id.button_upload);
                EditText courseName=popupView.findViewById(R.id.courseName);
                EditText courseDuration=popupView.findViewById(R.id.courseDuration);
                EditText courseDescription=popupView.findViewById(R.id.courseDescription);
                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String cname=courseName.getText().toString().trim();
                        String cduration=courseDuration.getText().toString().trim();
                        String cdescription=courseDescription.getText().toString().trim();
                        if (cname.isEmpty()){
                            courseName.setError("Required!");
                            courseName.requestFocus();
                        } else if (cduration.isEmpty()) {
                            courseDuration.setError("Required!");
                            courseDuration.requestFocus();
                        } else if (cdescription.isEmpty()) {
                            courseDescription.setError("Required!");
                            courseDescription.requestFocus();
                        }else{

                        }
                    }
                });
            }
        });

        recyclerView=(RecyclerView) findViewById(R.id.courses_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        adapter=new CoursesAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        for (int x=0; x<20; x++){
            CoursesSetGet coursesSetGet=new CoursesSetGet("BSc. Computer science","short course","one");
            coursesSetGetList.add(coursesSetGet);
            adapter.updateData(coursesSetGetList);
            Collections.reverse(coursesSetGetList);
            adapter.notifyDataSetChanged();
        }
        adapter.setOnItemClickListener(new CoursesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, CoursesSetGet itemSetGet) {

            }
        });
    }
}