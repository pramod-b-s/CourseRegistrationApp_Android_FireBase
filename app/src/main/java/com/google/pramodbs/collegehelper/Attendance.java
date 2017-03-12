package com.google.pramodbs.collegehelper;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Attendance extends AppCompatActivity {

    private TextView c1,c2,c3,c4,c5,c6,e1,e2;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    private static int cr1,cr2,cr3,cr4,cr5,cr6,el1,el2;
    private static String cr1nm,cr2nm,cr3nm,cr4nm,cr5nm,cr6nm,el1nm,el2nm,branch,year;
    private static String mUserId;

    private static int f1=0,f2=0,f3=0,f4=0,f5=0,f6=0,f7=0,f8=0,f9=0;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        c1=(TextView)findViewById(R.id.crs1);
        c2=(TextView)findViewById(R.id.crs2);
        c3=(TextView)findViewById(R.id.crs3);
        c4=(TextView)findViewById(R.id.crs4);
        c5=(TextView)findViewById(R.id.crs5);
        c6=(TextView)findViewById(R.id.crs6);
        e1=(TextView)findViewById(R.id.elec1);
        e2=(TextView)findViewById(R.id.elec2);

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mUserId=user.getUid();

        //getattendance();
        /**/
    }

    /*private void getattendance(){

        ValueEventListener postListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student post = dataSnapshot.getValue(Student.class);
                //branch=post.branch;
                //year=post.year;
                //cr1nm=post.branch;
                //c1.setText(cr1nm);

                cr1=post.CORE1att;
                cr2=post.CORE2att;
                cr3=post.CORE3att;
                cr4=post.CORE4att;
                cr5=post.CORE5att;
                cr6=post.CORE6att;
                el1=post.ELECTIVE1att;
                el2=post.ELECTIVE2att;

                cr1nm=post.CORE1;
                cr2nm=post.CORE2;
                cr3nm=post.CORE3;
                cr4nm=post.CORE4;
                cr5nm=post.CORE5;
                cr6nm=post.CORE6;
                el1nm=post.ELECTIVE1;
                el2nm=post.ELECTIVE2;
                branch=post.Branch;
                year=post.Year;

                if(cr1nm!=null) {
                    c1.setText(cr1nm + "     --     " + cr1);
                }
                if(cr2nm!=null) {
                    c2.setText(cr2nm + "     --     " + cr2);
                }
                if(cr3nm!=null){
                    c3.setText(cr3nm+"     --     "+cr3);
                }
                if(cr4nm!=null){
                    c4.setText(cr4nm+"     --     "+cr4);
                }
                if(cr5nm!=null){
                    c5.setText(cr5nm+"     --     "+cr5);
                }
                if(cr6nm!=null){
                    c6.setText(cr6nm+"     --     "+cr6);
                }

                if(el1nm!=null){
                    e1.setText(el1nm+"     --     "+el1);
                }
                if(el2nm!=null){
                    e2.setText(el2nm+"     --     "+el2);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mDatabase.child("users").child(mUserId).addValueEventListener(postListener);
    }*/


}

