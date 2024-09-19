package com.example.registrationapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import Adapters.CoursesAdapter;
import Adapters.CoursesSetGet;
import Adapters.RequestsAdapter;
import Adapters.RequestsSetGet;

public class Dashboard extends AppCompatActivity {
    RecyclerView recyclerView,request_recyclerview;
    CoursesAdapter adapter;
    RequestsAdapter request_adapter;
    RelativeLayout uploadbtn;
    AlertDialog new_course_dialog,course_description_dialog;
    LinearLayout homebutton,requestbutton,coursesRegistered,requestAvalable;
    ProgressBar request_progressbar;
    TextView no_request_text;
    Calendar calendar;
    public static String currentdate;

    private List<CoursesSetGet> coursesSetGetList=new ArrayList<>();
    private List<RequestsSetGet> requestsSetGetList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        calendar = Calendar.getInstance();
        currentdate = DateFormat.getInstance().format(calendar.getTime());
        request_progressbar=findViewById(R.id.request_progressbar);
        no_request_text=findViewById(R.id.no_request_text);
        request_recyclerview=findViewById(R.id.requests_recyclerview);
        homebutton=findViewById(R.id.ll_homeBtn);
        requestbutton=findViewById(R.id.ll_requestsBtn);
        coursesRegistered=findViewById(R.id.courses_layout);
        requestAvalable=findViewById(R.id.requests_layaout);
        recyclerView=(RecyclerView) findViewById(R.id.courses_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        adapter=new CoursesAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        request_recyclerview.setLayoutManager(new LinearLayoutManager(Dashboard.this));
        request_adapter=new RequestsAdapter(new ArrayList<>());
        request_recyclerview.setAdapter(request_adapter);
        for (int x=0; x<20; x++){

        }

        DatabaseReference fetch_courses=FirebaseDatabase.getInstance().getReference()
                .child("All Courses");
        fetch_courses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                coursesSetGetList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String courseNm=dataSnapshot.child("Course Name").getValue(String.class);
                    String courseDur=dataSnapshot.child("Course Duration").getValue(String.class);
                    String courseDes=dataSnapshot.child("Course Description").getValue(String.class);
                    String courseID=dataSnapshot.getKey().toString();
                    CoursesSetGet coursesSetGet=new CoursesSetGet(courseNm+"",courseDur+"",courseID+"",courseDes+"");
                    coursesSetGetList.add(coursesSetGet);

                }
                adapter.updateData(coursesSetGetList);
                Collections.reverse(coursesSetGetList);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                            DatabaseReference newcourse= FirebaseDatabase.getInstance().getReference("All Courses").push();
                            newcourse.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    HashMap<String,Object> hashMap=new HashMap<>();
                                    hashMap.put("Course Name",cname);
                                    hashMap.put("Course Duration",cduration);
                                    hashMap.put("Course Description",cdescription);
                                    newcourse.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Dashboard.this, "Success!", Toast.LENGTH_SHORT).show();
                                            new_course_dialog.dismiss();
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });
            }
        });


        adapter.setOnItemClickListener(new CoursesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, CoursesSetGet itemSetGet) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
                View popupView = LayoutInflater.from(Dashboard.this).inflate(R.layout.course_description, null);
                builder.setView(popupView);
                course_description_dialog = builder.create();
                course_description_dialog.show();
                Button upload=popupView.findViewById(R.id.button_request);
                TextView courseName=popupView.findViewById(R.id.coursename);
                TextView courseDuration=popupView.findViewById(R.id.courseduration);
                TextView courseDescription=popupView.findViewById(R.id.coursedescription);
                courseName.setText(itemSetGet.getCourseName());
                courseDuration.setText("Duration: "+itemSetGet.getCourseDuration());
                courseDescription.setText(itemSetGet.getCourseDescription());
            }
        });

        requestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coursesRegistered.setVisibility(View.GONE);
                requestAvalable.setVisibility(View.VISIBLE);
                no_request_text.setVisibility(View.GONE);
                request_progressbar.setVisibility(View.VISIBLE);

                DatabaseReference fetch_requests=FirebaseDatabase.getInstance().getReference()
                        .child("Requests");
                fetch_requests.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        requestsSetGetList.clear();
                        if (snapshot.exists()){
                            if (snapshot.hasChildren()){
                                request_progressbar.setVisibility(View.GONE);
                                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                    String studentName=dataSnapshot.child("Student Name").getValue(String.class);
                                    String studentID=dataSnapshot.child("StudentID").getValue(String.class);
                                    String courseName=dataSnapshot.child("Course Name").getValue(String.class);
                                    String requestDate=dataSnapshot.child("Request Date").getValue(String.class);
                                    String requestStatus=dataSnapshot.child("Request Status").getValue(String.class);
                                    String requestID=dataSnapshot.getKey().toString();
                                    RequestsSetGet requestsSetGet=new RequestsSetGet(requestID+"",studentName+"",studentID+"",courseName+"",requestDate+"",requestStatus+"");
                                    requestsSetGetList.add(requestsSetGet);

                                }
                                request_adapter.updateData(requestsSetGetList);
                                Collections.reverse(requestsSetGetList);
                                request_adapter.notifyDataSetChanged();
                            }else {
                                request_progressbar.setVisibility(View.GONE);
                                no_request_text.setVisibility(View.VISIBLE);
                            }
                        }else{
                            request_progressbar.setVisibility(View.GONE);
                            no_request_text.setVisibility(View.VISIBLE);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        request_adapter.setOnItemClickListener(new RequestsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, RequestsSetGet itemSetGet) {
                if(itemSetGet.getRequestStatus().equals("Unread")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
                    View popupView = LayoutInflater.from(Dashboard.this).inflate(R.layout.responding_to_request, null);
                    builder.setView(popupView);
                    course_description_dialog = builder.create();
                    course_description_dialog.show();
                    Button Decline = popupView.findViewById(R.id.button_decline);
                    Button Approve = popupView.findViewById(R.id.button_accept);
                    TextView desc = popupView.findViewById(R.id.description);
                    desc.setText(itemSetGet.getStudentName() + " requests to take " + itemSetGet.getCourseName() + ".");
                    Approve.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("Student Name", itemSetGet.getStudentName());
                            hashMap.put("StudentID", itemSetGet.getStudentID());
                            hashMap.put("Course Name", itemSetGet.getCourseName());
                            hashMap.put("Request Date", currentdate + " Hrs");
                            hashMap.put("Request Status", "Accepted");
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                                    .child("Requests").child(itemSetGet.getRequestID());
                            databaseReference.child("Request Status").setValue("Accepted").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    DatabaseReference fetch_requests = FirebaseDatabase.getInstance().getReference()
                                            .child("Feedback").child(itemSetGet.getStudentID()).child(itemSetGet.getRequestID());
                                    fetch_requests.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(Dashboard.this, "success!", Toast.LENGTH_SHORT).show();
                                            course_description_dialog.dismiss();
                                        }
                                    });
                                }
                            });
                        }
                    });
                    Decline.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("Student Name", itemSetGet.getStudentName());
                            hashMap.put("StudentID", itemSetGet.getStudentID());
                            hashMap.put("Course Name", itemSetGet.getCourseName());
                            hashMap.put("Request Date", currentdate + " Hrs");
                            hashMap.put("Request Status", "Declined");
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                                    .child("Requests").child(itemSetGet.getRequestID());
                            databaseReference.child("Request Status").setValue("Declined").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    DatabaseReference fetch_requests = FirebaseDatabase.getInstance().getReference()
                                            .child("Feedback").child(itemSetGet.getStudentID()).child(itemSetGet.getRequestID());
                                    fetch_requests.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(Dashboard.this, "success!", Toast.LENGTH_SHORT).show();
                                            course_description_dialog.dismiss();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }else{
                    Toast.makeText(Dashboard.this, "Request already worked on", Toast.LENGTH_SHORT).show();
                }
            }
        });
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coursesRegistered.setVisibility(View.VISIBLE);
                requestAvalable.setVisibility(View.GONE);

            }
        });
    }
}