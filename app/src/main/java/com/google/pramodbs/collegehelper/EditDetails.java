package com.google.pramodbs.collegehelper;
//LOC=786
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;

public class EditDetails extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private Button logoutbtn,save,regbutton,back;
    private DatabaseReference mDatabase;
    private String mUserId;
    private Query queryref;

    private TextView title,cores,electives;
    private TextView name,roll,reg;
    private Spinner year,branch,sp1,sp2,sp3,sp4,sp5,sp6,spe1,spe2;
    private String yearchosen,branchchosen,t1,t2,t3,t4,t5,t6,te1,te2;

    private TextView b1,b2,b3,b4,b5,b6,be1,be2,year1,brnch1;

    private static String cr1nm,cr2nm,cr3nm,cr4nm,cr5nm,cr6nm,el1nm,el2nm;
    private String[] yearopts,branchopts,crsopts,c2opts,c3opts,c4opts,c2eopts,c3eopts,c4eopts,e2opts,e3opts,
            e4opts,e2eopts,e3eopts,e4eopts,m2opts,m3opts,m4opts,m2eopts,m3eopts,m4eopts,c2fac,c3fac,c4fac,e2fac,e3fac,e4fac,
            m2fac,m3fac,m4fac;
    private ValueEventListener postListener;

    private String checkreg;

    private Firebase mref;
    private String readname,readrollno,readregno,readbranch,readyear;

    int lock=0,lock1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        save=(Button)findViewById(R.id.savebtn);
        back=(Button)findViewById(R.id.backbtn);

        b1=(TextView) findViewById(R.id.c1);
        b2=(TextView) findViewById(R.id.c2);
        b3=(TextView) findViewById(R.id.c3);
        b4=(TextView) findViewById(R.id.c4);
        b5=(TextView) findViewById(R.id.c5);
        b6=(TextView) findViewById(R.id.c6);
        be1=(TextView) findViewById(R.id.ce1);
        be2=(TextView) findViewById(R.id.ce2);
        year1=(TextView)findViewById(R.id.Year);
        brnch1=(TextView)findViewById(R.id.Branch);

        this.yearopts = new String[] {
                "Second Year", "Third Year", "Fourth Year"
        };
        this.branchopts = new String[] {
                "Computer Science and Engineering", "Electrical and Electronic Engineering", "Mechanical Engineering"
        };
        this.crsopts=new String[]{
                "Undertake","Drop"
        };

        //comps
        this.c2opts = new String[] {
                "CO200  --  BT", "CO201  --  BRC", "CO202  --  SGK","CO203  --  OPS","CO204  --  MB","CO205  --  JR"
        };
        this.c3opts = new String[] {
                "CO300  --  MPS", "CO301  --  ST", "CO302  --  VM","CO303  --  MPT","CO304  --  BRC",""
        };
        this.c4opts = new String[] {
                "CO400  --  ST", "CO401  --  BRC", "CO402  --  AA","CO403  --  SGK","",""
        };
        this.c2eopts = new String[] {
                "CO250  --  JR", "CO251  --  AA"
        };
        this.c3eopts = new String[] {
                "CO350  --  ST", "CO351  --  AA"
        };
        this.c4eopts = new String[] {
                "CO450  --  VM", "CO451  --  BT"
        };

        //eee
        this.e2opts = new String[] {
                "EE200  --  ABD", "EE201  --  HN", "EE202  --  KL","EE203  --  DJ","EE204  --  HHR","EE205  --  GB"
        };
        this.e3opts = new String[] {
                "EE300  --  ABS", "EE301  --  SPP", "EE302  --  HR","EE303  --  PBM","EE304  --  MSS",""
        };
        this.e4opts = new String[] {
                "EE400  --  SKN", "EE401  -- NSR", "EE402  --  SV","EE403  --  HC ","",""
        };
        this.e2eopts = new String[] {
                "EE250  --  NHS", "EE251  --  GGR"
        };
        this.e3eopts = new String[] {
                "EE350  --  SBN", "EE351  --  HCV"
        };
        this.e4eopts = new String[] {
                "EE450  --  PBS", "EE451  --  ABS"
        };

        //mech
        this.m2opts = new String[] {
                "ME200  --  KLR", "ME201  --  VK", "ME202  --  HP","ME203  --  IS","ME204  --  UY","ME205  --  RD"
        };
        this.m3opts = new String[] {
                "ME300  --  MSD", "ME301  --  SRT", "ME302  --  RJ","ME303  --  AK","ME304  --  RA",""
        };
        this.m4opts = new String[] {
                "ME400  --  NDM", "ME401  --  AS", "ME402  --  RS","ME403  --  MP","",""
        };
        this.m2eopts = new String[] {
                "ME250  --  YC", "ME251  --  KN"
        };
        this.m3eopts = new String[] {
                "ME350  --  RS", "ME351N  --  SR"
        };
        this.m4eopts = new String[] {
                "ME450  --  KJ", "ME451  -- AR"
        };

        save.setOnClickListener(this);
        back.setOnClickListener(this);

        mUserId=user.getUid();
        title=(TextView)findViewById(R.id.titlefield);
        cores=(TextView)findViewById(R.id.core);
        electives=(TextView)findViewById(R.id.elective);
        name=(TextView) findViewById(R.id.namefield);
        roll=(TextView) findViewById(R.id.rollno);
        reg=(TextView) findViewById(R.id.regno);

        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User post = dataSnapshot.getValue(User.class);
                readname=post.Name;
                readregno=post.regnumber;
                readrollno=post.rollnumber;
                readbranch=post.Branch;
                readyear=post.Year;

                yearchosen=readyear;
                branchchosen=readbranch;

                year1.setText(readyear);
                brnch1.setText(readbranch);
                name.setText(readname);
                roll.setText(readrollno);
                reg.setText(readregno);

                Toast.makeText(EditDetails.this,"Welcome back "+readname,Toast.LENGTH_SHORT).show();

                cr1nm=post.C1;
                cr2nm=post.C2;
                cr3nm=post.C3;
                cr4nm=post.C4;
                cr5nm=post.C5;
                cr6nm=post.C6;
                el1nm=post.E1;
                el2nm=post.E2;

                //Toast.makeText(EditDetails.this,readname+readbranch+readyear+cr1nm,Toast.LENGTH_SHORT).show();

                if(readyear.equals(yearopts[0])){
                    if(readbranch.equals(branchopts[0])) {
                        mDatabase.child("CO students").child("2nd year").child(mUserId).removeValue();

                        if(cr1nm.equals(crsopts[0])) {
                            mDatabase.child("CO courses").child("2nd year").child("CO200").child(mUserId).removeValue();
                        }
                        if(cr2nm.equals(crsopts[0])){
                            mDatabase.child("CO courses").child("2nd year").child("CO201").child(mUserId).removeValue();
                        }
                        if(cr3nm.equals(crsopts[0])){
                            mDatabase.child("CO courses").child("2nd year").child("CO202").child(mUserId).removeValue();
                        }
                        if(cr4nm.equals(crsopts[0])){
                            mDatabase.child("CO courses").child("2nd year").child("CO203").child(mUserId).removeValue();
                        }
                        if(cr5nm.equals(crsopts[0])){
                            mDatabase.child("CO courses").child("2nd year").child("CO204").child(mUserId).removeValue();
                        }
                        if(cr6nm.equals(crsopts[0])){
                            mDatabase.child("CO courses").child("2nd year").child("CO205").child(mUserId).removeValue();
                        }

                        if(el1nm.equals(crsopts[0])) {
                            mDatabase.child("CO courses").child("2nd year").child("CO250").child(mUserId).removeValue();
                        }
                        if(el2nm.equals(crsopts[0])){
                            mDatabase.child("CO courses").child("2nd year").child("CO251").child(mUserId).removeValue();
                        }

                    }
                    if(readbranch.equals(branchopts[1])){
                        mDatabase.child("EE students").child("2nd year").child(mUserId).removeValue();

                        if(cr1nm.equals(crsopts[0])) {
                            mDatabase.child("EE courses").child("2nd year").child("EE200").child(mUserId).removeValue();
                        }
                        if(cr2nm.equals(crsopts[0])){
                            mDatabase.child("EE courses").child("2nd year").child("EE201").child(mUserId).removeValue();
                        }
                        if(cr3nm.equals(crsopts[0])){
                            mDatabase.child("EE courses").child("2nd year").child("EE202").child(mUserId).removeValue();
                        }
                        if(cr4nm.equals(crsopts[0])){
                            mDatabase.child("EE courses").child("2nd year").child("EE203").child(mUserId).removeValue();
                        }
                        if(cr5nm.equals(crsopts[0])){
                            mDatabase.child("EE courses").child("2nd year").child("EE204").child(mUserId).removeValue();
                        }
                        if(cr6nm.equals(crsopts[0])){
                            mDatabase.child("EE courses").child("2nd year").child("EE205").child(mUserId).removeValue();
                        }

                        if(el1nm.equals(crsopts[0])) {
                            mDatabase.child("EE courses").child("2nd year").child("EE250").child(mUserId).removeValue();
                        }
                        if(el2nm.equals(crsopts[0])){
                            mDatabase.child("EE courses").child("2nd year").child("EE251").child(mUserId).removeValue();
                        }

                    }
                    if(readbranch.equals(branchopts[2])){
                        mDatabase.child("ME students").child("2nd year").child(mUserId).removeValue();

                        if(cr1nm.equals(crsopts[0])) {
                            mDatabase.child("ME courses").child("2nd year").child("ME200").child(mUserId).removeValue();
                        }
                        if(cr2nm.equals(crsopts[0])){
                            mDatabase.child("ME courses").child("2nd year").child("ME201").child(mUserId).removeValue();
                        }
                        if(cr3nm.equals(crsopts[0])){
                            mDatabase.child("ME courses").child("2nd year").child("ME202").child(mUserId).removeValue();
                        }
                        if(cr4nm.equals(crsopts[0])){
                            mDatabase.child("ME courses").child("2nd year").child("ME203").child(mUserId).removeValue();
                        }
                        if(cr5nm.equals(crsopts[0])){
                            mDatabase.child("ME courses").child("2nd year").child("ME204").child(mUserId).removeValue();
                        }
                        if(cr6nm.equals(crsopts[0])){
                            mDatabase.child("ME courses").child("2nd year").child("ME205").child(mUserId).removeValue();
                        }

                        if(el1nm.equals(crsopts[0])) {
                            mDatabase.child("ME courses").child("2nd year").child("ME250").child(mUserId).removeValue();
                        }
                        if(el2nm.equals(crsopts[0])){
                            mDatabase.child("ME courses").child("2nd year").child("ME251").child(mUserId).removeValue();
                        }
                    }
                }

                if(readyear.equals(yearopts[1])){
                    if(readbranch.equals(branchopts[0])){
                        mDatabase.child("CO students").child("3rd year").child(mUserId).removeValue();

                        if(cr1nm.equals(crsopts[0])) {
                            mDatabase.child("CO courses").child("3rd year").child("CO300").child(mUserId).removeValue();
                        }
                        if(cr2nm.equals(crsopts[0])){
                            mDatabase.child("CO courses").child("3rd year").child("CO301").child(mUserId).removeValue();
                        }
                        if(cr3nm.equals(crsopts[0])){
                            mDatabase.child("CO courses").child("3rd year").child("CO302").child(mUserId).removeValue();
                        }
                        if(cr4nm.equals(crsopts[0])){
                            mDatabase.child("CO courses").child("3rd year").child("CO303").child(mUserId).removeValue();
                        }
                        if(cr5nm.equals(crsopts[0])){
                            mDatabase.child("CO courses").child("3rd year").child("CO304").child(mUserId).removeValue();
                        }

                        if(el1nm.equals(crsopts[0])) {
                            mDatabase.child("CO courses").child("3rd year").child("CO350").child(mUserId).removeValue();
                        }
                        if(el2nm.equals(crsopts[0])){
                            mDatabase.child("CO courses").child("3rd year").child("CO351").child(mUserId).removeValue();
                        }
                    }
                    if(readbranch.equals(branchopts[1])){
                        mDatabase.child("EE students").child("3rd year").child(mUserId).removeValue();

                        if(cr1nm.equals(crsopts[0])) {
                            mDatabase.child("EE courses").child("3rd year").child("EE300").child(mUserId).removeValue();
                        }
                        if(cr2nm.equals(crsopts[0])){
                            mDatabase.child("EE courses").child("3rd year").child("EE301").child(mUserId).removeValue();
                        }
                        if(cr3nm.equals(crsopts[0])){
                            mDatabase.child("EE courses").child("3rd year").child("EE302").child(mUserId).removeValue();
                        }
                        if(cr4nm.equals(crsopts[0])){
                            mDatabase.child("EE courses").child("3rd year").child("EE303").child(mUserId).removeValue();
                        }
                        if(cr5nm.equals(crsopts[0])){
                            mDatabase.child("EE courses").child("3rd year").child("EE304").child(mUserId).removeValue();
                        }

                        if(el1nm.equals(crsopts[0])) {
                            mDatabase.child("EE courses").child("3rd year").child("EE350").child(mUserId).removeValue();
                        }
                        if(el2nm.equals(crsopts[0])){
                            mDatabase.child("EE courses").child("3rd year").child("EE351").child(mUserId).removeValue();
                        }
                    }
                    if(readbranch.equals(branchopts[2])){
                        mDatabase.child("ME students").child("3rd year").child(mUserId).removeValue();

                        if(cr1nm.equals(crsopts[0])) {
                            mDatabase.child("ME courses").child("3rd year").child("ME300").child(mUserId).removeValue();
                        }
                        if(cr2nm.equals(crsopts[0])){
                            mDatabase.child("ME courses").child("3rd year").child("ME301").child(mUserId).removeValue();
                        }
                        if(cr3nm.equals(crsopts[0])){
                            mDatabase.child("ME courses").child("3rd year").child("ME302").child(mUserId).removeValue();
                        }
                        if(cr4nm.equals(crsopts[0])){
                            mDatabase.child("ME courses").child("3rd year").child("ME303").child(mUserId).removeValue();
                        }
                        if(cr5nm.equals(crsopts[0])){
                            mDatabase.child("ME courses").child("3rd year").child("ME304").child(mUserId).removeValue();
                        }

                        if(el1nm.equals(crsopts[0])) {
                            mDatabase.child("ME courses").child("3rd year").child("ME350").child(mUserId).removeValue();
                        }
                        if(el2nm.equals(crsopts[0])){
                            mDatabase.child("ME courses").child("3rd year").child("ME351").child(mUserId).removeValue();
                        }
                    }
                }

                if(readyear.equals(yearopts[2])){
                    if(readbranch.equals(branchopts[0])){
                        mDatabase.child("CO students").child("4th year").child(mUserId).removeValue();

                        if(cr1nm.equals(crsopts[0])) {
                            mDatabase.child("CO courses").child("4th year").child("CO400").child(mUserId).removeValue();
                        }
                        if(cr2nm.equals(crsopts[0])){
                            mDatabase.child("CO courses").child("4th year").child("CO401").child(mUserId).removeValue();
                        }
                        if(cr3nm.equals(crsopts[0])){
                            mDatabase.child("CO courses").child("4th year").child("CO402").child(mUserId).removeValue();
                        }
                        if(cr4nm.equals(crsopts[0])){
                            mDatabase.child("CO courses").child("4th year").child("CO403").child(mUserId).removeValue();
                        }

                        if(el1nm.equals(crsopts[0])) {
                            mDatabase.child("CO courses").child("4th year").child("CO450").child(mUserId).removeValue();
                        }
                        if(el2nm.equals(crsopts[0])){
                            mDatabase.child("CO courses").child("4th year").child("CO451").child(mUserId).removeValue();
                        }
                    }
                    if(readbranch.equals(branchopts[1])){
                        mDatabase.child("EE students").child("4th year").child(mUserId).removeValue();

                        if(cr1nm.equals(crsopts[0])) {
                            mDatabase.child("EE courses").child("4th year").child("EE400").child(mUserId).removeValue();
                        }
                        if(cr2nm.equals(crsopts[0])){
                            mDatabase.child("EE courses").child("4th year").child("EE401").child(mUserId).removeValue();
                        }
                        if(cr3nm.equals(crsopts[0])){
                            mDatabase.child("EE courses").child("4th year").child("EE402").child(mUserId).removeValue();
                        }
                        if(cr4nm.equals(crsopts[0])){
                            mDatabase.child("EE courses").child("4th year").child("EE403").child(mUserId).removeValue();
                        }

                        if(el1nm.equals(crsopts[0])) {
                            mDatabase.child("EE courses").child("4th year").child("EE450").child(mUserId).removeValue();
                        }
                        if(el2nm.equals(crsopts[0])){
                            mDatabase.child("EE courses").child("4th year").child("EE451").child(mUserId).removeValue();
                        }
                    }
                    if(readbranch.equals(branchopts[2])){
                        mDatabase.child("ME students").child("4th year").child(mUserId).removeValue();

                        if(cr1nm.equals(crsopts[0])) {
                            mDatabase.child("ME courses").child("4th year").child("ME400").child(mUserId).removeValue();
                        }
                        if(cr2nm.equals(crsopts[0])){
                            mDatabase.child("ME courses").child("4th year").child("ME401").child(mUserId).removeValue();
                        }
                        if(cr3nm.equals(crsopts[0])){
                            mDatabase.child("ME courses").child("4th year").child("ME402").child(mUserId).removeValue();
                        }
                        if(cr4nm.equals(crsopts[0])){
                            mDatabase.child("ME courses").child("4th year").child("ME403").child(mUserId).removeValue();
                        }

                        if(el1nm.equals(crsopts[0])) {
                            mDatabase.child("ME courses").child("4th year").child("ME450").child(mUserId).removeValue();
                        }
                        if(el2nm.equals(crsopts[0])){
                            mDatabase.child("ME courses").child("4th year").child("ME451").child(mUserId).removeValue();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        };

        mDatabase.child("users").child(mUserId).addListenerForSingleValueEvent(postListener);
        //mDatabase.child("users").child(mUserId).removeEventListener(postListener);
        //mDatabase.child("users").child(mUserId).removeValue();

        //Toast.makeText(this, readbranch+readyear+cr1nm, Toast.LENGTH_SHORT).show();

        //mDatabase.child("users").child(mUserId).removeEventListener(postListener);
    }

    @Override
    public void onClick(View v) {
        if (v == logoutbtn) {
            firebaseAuth.signOut();
            Toast.makeText(this, "Signed Out successfully !", Toast.LENGTH_SHORT).show();
            Intent gotomain = new Intent(this, MainActivity.class);
            finish();
            startActivity(gotomain);
        }
        if (v == save) {
            mDatabase.child("users").child(mUserId).removeEventListener(postListener);
            Toast.makeText(this, "Register again !", Toast.LENGTH_SHORT).show();
            finish();
            Intent gotonext = new Intent(this, ProfileActivity.class);
            startActivity(gotonext);
        }
        if(v==back){
            finish();
            Intent gotonext = new Intent(this, OnLoginActivity.class);
            startActivity(gotonext);
        }
    }


}


