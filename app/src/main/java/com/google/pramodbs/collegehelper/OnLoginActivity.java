package com.google.pramodbs.collegehelper;
//LOC=41
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OnLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    private Button nreg,editdetails,atten,seecrs,lgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_login);

        nreg=(Button)findViewById(R.id.newreg);
        editdetails=(Button)findViewById(R.id.edit);
        //atten=(Button)findViewById(R.id.seeatt);
        seecrs=(Button)findViewById(R.id.view);
        lgot=(Button)findViewById(R.id.logout);

        nreg.setOnClickListener(this);
        editdetails.setOnClickListener(this);
        //atten.setOnClickListener(this);
        seecrs.setOnClickListener(this);
        lgot.setOnClickListener(this);

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public void onClick(View v) {
        if(v==nreg){
            Toast.makeText(OnLoginActivity.this,"Enter Your Details !",Toast.LENGTH_SHORT).show();
            Intent newint=new Intent(this,ProfileActivity.class);
            startActivity(newint);
        }
        if(v==editdetails){
            Toast.makeText(OnLoginActivity.this,"Edit Your Details !",Toast.LENGTH_SHORT).show();
            Intent newintedit=new Intent(this,EditDetails.class);
            startActivity(newintedit);
        }
        /*if(v==atten){
            Toast.makeText(OnLoginActivity.this,"Please wait !",Toast.LENGTH_SHORT).show();
            Intent newintatt=new Intent(this,Attendance.class);
            startActivity(newintatt);
        }*/
        if(v==seecrs){
            Toast.makeText(OnLoginActivity.this,"Your Courses !",Toast.LENGTH_SHORT).show();
            Intent newintatt=new Intent(this,Courses.class);
            startActivity(newintatt);
        }
        if(v==lgot){
            Toast.makeText(OnLoginActivity.this,"Logged out !",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
            finish();
            Intent newintlg=new Intent(this,MainActivity.class);
            startActivity(newintlg);
        }
    }
}
