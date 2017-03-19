package com.google.pramodbs.collegehelper;
//LOC=1029
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Ref;
import java.util.*;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    //private FirebaseUser user;
    //private FirebaseAnalytics mFirebaseAnalytics;

    private Button logoutbtn,save,regbutton;
    private DatabaseReference mDatabase;
    private String mUserId;
    private Query queryref;

    private TextView title,cores,electives;
    private EditText name,roll,reg;
    private Spinner year,branch,sp1,sp2,sp3,sp4,sp5,sp6,spe1,spe2;
    private String yearchosen,branchchosen,t1,t2,t3,t4,t5,t6,te1,te2;

    private TextView b1,b2,b3,b4,b5,b6,be1,be2;

    private String[] yearopts,branchopts,crsopts,c2opts,c3opts,c4opts,c2eopts,c3eopts,c4eopts,e2opts,e3opts,
    e4opts,e2eopts,e3eopts,e4eopts,m2opts,m3opts,m4opts,m2eopts,m3eopts,m4eopts,c2fac,c3fac,c4fac,e2fac,e3fac,e4fac,
    m2fac,m3fac,m4fac;
    private ValueEventListener postListener;

    private String checkreg;

    private Firebase mref;
    private String readname,readrollno,readregno;

    int lock=0,lock1=0;
    //private String[] degopts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //userid=mDatabase.getKey();
        save=(Button)findViewById(R.id.savebtn);
        logoutbtn=(Button)findViewById(R.id.logoutbtn);
        regbutton=(Button)findViewById(R.id.regbtn);

        b1=(TextView) findViewById(R.id.c1);
        b2=(TextView) findViewById(R.id.c2);
        b3=(TextView) findViewById(R.id.c3);
        b4=(TextView) findViewById(R.id.c4);
        b5=(TextView) findViewById(R.id.c5);
        b6=(TextView) findViewById(R.id.c6);
        be1=(TextView) findViewById(R.id.ce1);
        be2=(TextView) findViewById(R.id.ce2);

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

        year=(Spinner)findViewById(R.id.SpinnerYear);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, yearopts);
        year.setAdapter(adapter);
        year.setOnItemSelectedListener(this);

        branch=(Spinner)findViewById(R.id.SpinnerBranch);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, branchopts);
        branch.setAdapter(adapter1);
        branch.setOnItemSelectedListener(this);

        logoutbtn.setOnClickListener(this);
        save.setOnClickListener(this);
        regbutton.setOnClickListener(this);
        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mUserId=user.getUid();
        title=(TextView)findViewById(R.id.titlefield);
        cores=(TextView)findViewById(R.id.core);
        electives=(TextView)findViewById(R.id.elective);
        name=(EditText) findViewById(R.id.namefield);
        roll=(EditText) findViewById(R.id.rollno);
        reg=(EditText) findViewById(R.id.regno);

        //mDatabase.child("users").child(mUserId).removeEventListener(postListener);

        mDatabase.child("CO students").child("2nd year").child(mUserId).removeValue();
        mDatabase.child("CO students").child("3rd year").child(mUserId).removeValue();
        mDatabase.child("CO students").child("4th year").child(mUserId).removeValue();

        mDatabase.child("CO courses").child("2nd year").child("CO200").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("2nd year").child("CO201").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("2nd year").child("CO202").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("2nd year").child("CO203").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("2nd year").child("CO204").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("2nd year").child("CO205").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("2nd year").child("CO250").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("2nd year").child("CO251").child(mUserId).removeValue();

        mDatabase.child("CO courses").child("3rd year").child("CO300").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("3rd year").child("CO301").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("3rd year").child("CO302").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("3rd year").child("CO303").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("3rd year").child("CO304").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("3rd year").child("CO350").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("3rd year").child("CO351").child(mUserId).removeValue();

        mDatabase.child("CO courses").child("4th year").child("CO400").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("4th year").child("CO401").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("4th year").child("CO402").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("4th year").child("CO403").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("4th year").child("CO450").child(mUserId).removeValue();
        mDatabase.child("CO courses").child("4th year").child("CO451").child(mUserId).removeValue();


        mDatabase.child("EE students").child("2nd year").child(mUserId).removeValue();
        mDatabase.child("EE students").child("3rd year").child(mUserId).removeValue();
        mDatabase.child("EE students").child("4th year").child(mUserId).removeValue();

        mDatabase.child("EE courses").child("2nd year").child("EE200").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("2nd year").child("EE201").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("2nd year").child("EE202").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("2nd year").child("EE203").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("2nd year").child("EE204").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("2nd year").child("EE205").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("2nd year").child("EE250").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("2nd year").child("EE251").child(mUserId).removeValue();

        mDatabase.child("EE courses").child("3rd year").child("EE300").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("3rd year").child("EE301").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("3rd year").child("EE302").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("3rd year").child("EE303").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("3rd year").child("EE304").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("3rd year").child("EE305").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("3rd year").child("EE350").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("3rd year").child("EE351").child(mUserId).removeValue();

        mDatabase.child("EE courses").child("4th year").child("EE400").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("4th year").child("EE401").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("4th year").child("EE402").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("4th year").child("EE403").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("4th year").child("EE450").child(mUserId).removeValue();
        mDatabase.child("EE courses").child("4th year").child("EE451").child(mUserId).removeValue();


        mDatabase.child("ME students").child("2nd year").child(mUserId).removeValue();
        mDatabase.child("ME students").child("3rd year").child(mUserId).removeValue();
        mDatabase.child("ME students").child("4th year").child(mUserId).removeValue();

        mDatabase.child("ME courses").child("2nd year").child("ME200").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("2nd year").child("ME201").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("2nd year").child("ME202").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("2nd year").child("ME203").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("2nd year").child("ME204").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("2nd year").child("ME205").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("2nd year").child("ME250").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("2nd year").child("ME251").child(mUserId).removeValue();

        mDatabase.child("ME courses").child("3rd year").child("ME300").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("3rd year").child("ME301").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("3rd year").child("ME302").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("3rd year").child("ME303").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("3rd year").child("ME304").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("3rd year").child("ME350").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("3rd year").child("ME351").child(mUserId).removeValue();

        mDatabase.child("ME courses").child("4th year").child("ME400").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("4th year").child("ME401").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("4th year").child("ME402").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("4th year").child("ME403").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("4th year").child("ME450").child(mUserId).removeValue();
        mDatabase.child("ME courses").child("4th year").child("ME451").child(mUserId).removeValue();

    }

    private void saveuserinfo(){

        String namestr = name.getText().toString();
        String regchosen = reg.getText().toString();
        String rollchosen = roll.getText().toString();

        Student student=new Student(namestr,rollchosen,regchosen,branchchosen,yearchosen);

        //FOR COMPS
            if (branchchosen == "Computer Science and Engineering") {
                //FOR SECOND YEAR
                if (yearchosen == "Second Year") {
                    if(t1==crsopts[0]) {
                        mDatabase.child("CO courses").child("2nd year").child("CO200").child(mUserId).setValue(student);
                    }
                    if(t2==crsopts[0]){
                        mDatabase.child("CO courses").child("2nd year").child("CO201").child(mUserId).setValue(student);
                    }
                    if(t3==crsopts[0]){
                        mDatabase.child("CO courses").child("2nd year").child("CO202").child(mUserId).setValue(student);
                    }
                    if(t4==crsopts[0]){
                        mDatabase.child("CO courses").child("2nd year").child("CO203").child(mUserId).setValue(student);
                    }
                    if(t5==crsopts[0]){
                        mDatabase.child("CO courses").child("2nd year").child("CO204").child(mUserId).setValue(student);
                    }
                    if(t6==crsopts[0]){
                        mDatabase.child("CO courses").child("2nd year").child("CO205").child(mUserId).setValue(student);
                    }

                    if(te1==crsopts[0]) {
                        mDatabase.child("CO courses").child("2nd year").child("CO250").child(mUserId).setValue(student);
                    }
                    if(te2==crsopts[0]){
                        mDatabase.child("CO courses").child("2nd year").child("CO251").child(mUserId).setValue(student);
                    }

                    mDatabase.child("CO students").child("2nd year").child(mUserId).setValue(student);

                    mDatabase.child("CO students").child("2nd year").child(mUserId).child(c2opts[0]).setValue(t1);
                    mDatabase.child("CO students").child("2nd year").child(mUserId).child(c2opts[1]).setValue(t2);
                    mDatabase.child("CO students").child("2nd year").child(mUserId).child(c2opts[2]).setValue(t3);
                    mDatabase.child("CO students").child("2nd year").child(mUserId).child(c2opts[3]).setValue(t4);
                    mDatabase.child("CO students").child("2nd year").child(mUserId).child(c2opts[4]).setValue(t5);
                    mDatabase.child("CO students").child("2nd year").child(mUserId).child(c2opts[5]).setValue(t6);

                    mDatabase.child("CO students").child("2nd year").child(mUserId).child(c2eopts[0]).setValue(te1);
                    mDatabase.child("CO students").child("2nd year").child(mUserId).child(c2eopts[1]).setValue(te2);

                    User user=new User(namestr,branchchosen,yearchosen,regchosen,rollchosen,t1,t2,t3,t4,t5,t6,te1,te2);

                    mDatabase.child("users").child(mUserId).setValue(user);

                }
                //FOR THIRD YEAR
                if (yearchosen == "Third Year") {
                    if(t1==crsopts[0]){
                        mDatabase.child("CO courses").child("3rd year").child("CO300").child(mUserId).setValue(student);
                    }
                    if(t2==crsopts[0]){
                        mDatabase.child("CO courses").child("3rd year").child("CO301").child(mUserId).setValue(student);
                    }
                    if(t3==crsopts[0]){
                        mDatabase.child("CO courses").child("3rd year").child("CO302").child(mUserId).setValue(student);
                    }
                    if(t4==crsopts[0]){
                        mDatabase.child("CO courses").child("3rd year").child("CO303").child(mUserId).setValue(student);
                    }
                    if(t5==crsopts[0]){
                        mDatabase.child("CO courses").child("3rd year").child("CO304").child(mUserId).setValue(student);
                    }

                    if(te1==crsopts[0]) {
                        mDatabase.child("CO courses").child("3rd year").child("CO350").child(mUserId).setValue(student);
                    }
                    if(te2==crsopts[0]){
                        mDatabase.child("CO courses").child("3rd year").child("CO351").child(mUserId).setValue(student);
                    }

                    mDatabase.child("CO students").child("3rd year").child(mUserId).setValue(student);

                    mDatabase.child("CO students").child("3rd year").child(mUserId).child(c3opts[0]).setValue(t1);
                    mDatabase.child("CO students").child("3rd year").child(mUserId).child(c3opts[1]).setValue(t2);
                    mDatabase.child("CO students").child("3rd year").child(mUserId).child(c3opts[2]).setValue(t3);
                    mDatabase.child("CO students").child("3rd year").child(mUserId).child(c3opts[3]).setValue(t4);
                    mDatabase.child("CO students").child("3rd year").child(mUserId).child(c3opts[4]).setValue(t5);

                    mDatabase.child("CO students").child("3rd year").child(mUserId).child(c3eopts[0]).setValue(te1);
                    mDatabase.child("CO students").child("3rd year").child(mUserId).child(c3eopts[1]).setValue(te2);

                    User user=new User(namestr,branchchosen,yearchosen,regchosen,rollchosen,t1,t2,t3,t4,t5,"",te1,te2);

                    mDatabase.child("users").child(mUserId).setValue(user);
                }
                //FOR FINAL YEAR
                if (yearchosen == "Fourth Year") {
                    if(t1==crsopts[0]){
                        mDatabase.child("CO courses").child("4th year").child("CO400").child(mUserId).setValue(student);
                    }
                    if(t2==crsopts[0]){
                        mDatabase.child("CO courses").child("4th year").child("CO401").child(mUserId).setValue(student);
                    }
                    if(t3==crsopts[0]){
                        mDatabase.child("CO courses").child("4th year").child("CO402").child(mUserId).setValue(student);
                    }
                    if(t4==crsopts[0]){
                        mDatabase.child("CO courses").child("4th year").child("CO403").child(mUserId).setValue(student);
                    }

                    if(te1==crsopts[0]) {
                        mDatabase.child("CO courses").child("4th year").child("CO450").child(mUserId).setValue(student);
                    }
                    if(te2==crsopts[0]){
                        mDatabase.child("CO courses").child("4th year").child("CO451").child(mUserId).setValue(student);
                    }

                    mDatabase.child("CO students").child("4th year").child(mUserId).setValue(student);

                    mDatabase.child("CO students").child("4th year").child(mUserId).child(c4opts[0]).setValue(t1);
                    mDatabase.child("CO students").child("4th year").child(mUserId).child(c4opts[1]).setValue(t2);
                    mDatabase.child("CO students").child("4th year").child(mUserId).child(c4opts[2]).setValue(t3);
                    mDatabase.child("CO students").child("4th year").child(mUserId).child(c4opts[3]).setValue(t4);

                    mDatabase.child("CO students").child("4th year").child(mUserId).child(c4eopts[0]).setValue(te1);
                    mDatabase.child("CO students").child("4th year").child(mUserId).child(c4eopts[1]).setValue(te2);

                    User user=new User(namestr,branchchosen,yearchosen,regchosen,rollchosen,t1,t2,t3,t4,"","",te1,te2);

                    mDatabase.child("users").child(mUserId).setValue(user);
                }
            }


            //FOR EEE
            if (branchchosen == "Electrical and Electronic Engineering") {
                //FOR SECOND YEAR
                if (yearchosen == "Second Year") {
                    if(t1==crsopts[0]) {
                        mDatabase.child("EE courses").child("2nd year").child("EE200").child(mUserId).setValue(student);
                    }
                    if(t2==crsopts[0]){
                        mDatabase.child("EE courses").child("2nd year").child("EE201").child(mUserId).setValue(student);
                    }
                    if(t3==crsopts[0]){
                        mDatabase.child("EE courses").child("2nd year").child("EE202").child(mUserId).setValue(student);
                    }
                    if(t4==crsopts[0]){
                        mDatabase.child("EE courses").child("2nd year").child("EE203").child(mUserId).setValue(student);
                    }
                    if(t5==crsopts[0]){
                        mDatabase.child("EE courses").child("2nd year").child("EE204").child(mUserId).setValue(student);
                    }
                    if(t6==crsopts[0]){
                        mDatabase.child("EE courses").child("2nd year").child("EE205").child(mUserId).setValue(student);
                    }

                    if(te1==crsopts[0]) {
                        mDatabase.child("EE courses").child("2nd year").child("EE250").child(mUserId).setValue(student);
                    }
                    if(te2==crsopts[0]){
                        mDatabase.child("EE courses").child("2nd year").child("EE251").child(mUserId).setValue(student);
                    }

                    mDatabase.child("EE students").child("2nd year").child(mUserId).setValue(student);

                    mDatabase.child("EE students").child("2nd year").child(mUserId).child(c2opts[0]).setValue(t1);
                    mDatabase.child("EE students").child("2nd year").child(mUserId).child(c2opts[1]).setValue(t2);
                    mDatabase.child("EE students").child("2nd year").child(mUserId).child(c2opts[2]).setValue(t3);
                    mDatabase.child("EE students").child("2nd year").child(mUserId).child(c2opts[3]).setValue(t4);
                    mDatabase.child("EE students").child("2nd year").child(mUserId).child(c2opts[4]).setValue(t5);
                    mDatabase.child("EE students").child("2nd year").child(mUserId).child(c2opts[5]).setValue(t6);

                    mDatabase.child("EE students").child("2nd year").child(mUserId).child(c2eopts[0]).setValue(te1);
                    mDatabase.child("EE students").child("2nd year").child(mUserId).child(c2eopts[1]).setValue(te2);

                    User user=new User(namestr,branchchosen,yearchosen,regchosen,rollchosen,t1,t2,t3,t4,t5,t6,te1,te2);

                    mDatabase.child("users").child(mUserId).setValue(user);
                }
                //FOR THIRD YEAR
                if (yearchosen == "Third Year") {
                    if(t1==crsopts[0]){
                        mDatabase.child("EE courses").child("3rd year").child("EE300").child(mUserId).setValue(student);
                    }
                    if(t2==crsopts[0]){
                        mDatabase.child("EE courses").child("3rd year").child("EE301").child(mUserId).setValue(student);
                    }
                    if(t3==crsopts[0]){
                        mDatabase.child("EE courses").child("3rd year").child("EE302").child(mUserId).setValue(student);
                    }
                    if(t4==crsopts[0]){
                        mDatabase.child("EE courses").child("3rd year").child("EE303").child(mUserId).setValue(student);
                    }
                    if(t5==crsopts[0]){
                        mDatabase.child("EE courses").child("3rd year").child("EE304").child(mUserId).setValue(student);
                    }

                    if(te1==crsopts[0]) {
                        mDatabase.child("EE courses").child("3rd year").child("EE350").child(mUserId).setValue(student);
                    }
                    if(te2==crsopts[0]){
                        mDatabase.child("EE courses").child("3rd year").child("EE351").child(mUserId).setValue(student);
                    }

                    mDatabase.child("EE students").child("3rd year").child(mUserId).setValue(student);

                    mDatabase.child("EE students").child("3rd year").child(mUserId).child(c3opts[0]).setValue(t1);
                    mDatabase.child("EE students").child("3rd year").child(mUserId).child(c3opts[1]).setValue(t2);
                    mDatabase.child("EE students").child("3rd year").child(mUserId).child(c3opts[2]).setValue(t3);
                    mDatabase.child("EE students").child("3rd year").child(mUserId).child(c3opts[3]).setValue(t4);
                    mDatabase.child("EE students").child("3rd year").child(mUserId).child(c3opts[4]).setValue(t5);

                    mDatabase.child("EE students").child("3rd year").child(mUserId).child(c3eopts[0]).setValue(te1);
                    mDatabase.child("EE students").child("3rd year").child(mUserId).child(c3eopts[1]).setValue(te2);

                    User user=new User(namestr,branchchosen,yearchosen,regchosen,rollchosen,t1,t2,t3,t4,t5,"",te1,te2);

                    mDatabase.child("users").child(mUserId).setValue(user);
                }
                //FOR FINAL YEAR
                if (yearchosen == "Fourth Year") {
                    if(t1==crsopts[0]){
                        mDatabase.child("EE courses").child("4th year").child("EE400").child(mUserId).setValue(student);
                    }
                    if(t2==crsopts[0]){
                        mDatabase.child("EE courses").child("4th year").child("EE401").child(mUserId).setValue(student);
                    }
                    if(t3==crsopts[0]){
                        mDatabase.child("EE courses").child("4th year").child("EE402").child(mUserId).setValue(student);
                    }
                    if(t4==crsopts[0]){
                        mDatabase.child("EE courses").child("4th year").child("EE403").child(mUserId).setValue(student);
                    }

                    if(te1==crsopts[0]) {
                        mDatabase.child("EE courses").child("4th year").child("EE450").child(mUserId).setValue(student);
                    }
                    if(te2==crsopts[0]){
                        mDatabase.child("EE courses").child("4th year").child("EE451").child(mUserId).setValue(student);
                    }

                    mDatabase.child("EE students").child("4th year").child(mUserId).setValue(student);

                    mDatabase.child("EE students").child("4th year").child(mUserId).child(c4opts[0]).setValue(t1);
                    mDatabase.child("EE students").child("4th year").child(mUserId).child(c4opts[1]).setValue(t2);
                    mDatabase.child("EE students").child("4th year").child(mUserId).child(c4opts[2]).setValue(t3);
                    mDatabase.child("EE students").child("4th year").child(mUserId).child(c4opts[3]).setValue(t4);

                    mDatabase.child("EE students").child("4th year").child(mUserId).child(c4eopts[0]).setValue(te1);
                    mDatabase.child("EE students").child("4th year").child(mUserId).child(c4eopts[1]).setValue(te2);

                    User user=new User(namestr,branchchosen,yearchosen,regchosen,rollchosen,t1,t2,t3,t4,"","",te1,te2);

                    mDatabase.child("users").child(mUserId).setValue(user);
                }
            }


            //FOR MECH
            if (branchchosen == "Mechanical Engineering") {
                //FOR SECOND YEAR
                if (yearchosen == "Second Year") {
                    if(t1==crsopts[0]) {
                        mDatabase.child("ME courses").child("2nd year").child("ME200").child(mUserId).setValue(student);
                    }
                    if(t2==crsopts[0]){
                        mDatabase.child("ME courses").child("2nd year").child("ME201").child(mUserId).setValue(student);
                    }
                    if(t3==crsopts[0]){
                        mDatabase.child("ME courses").child("2nd year").child("ME202").child(mUserId).setValue(student);
                    }
                    if(t4==crsopts[0]){
                        mDatabase.child("ME courses").child("2nd year").child("ME203").child(mUserId).setValue(student);
                    }
                    if(t5==crsopts[0]){
                        mDatabase.child("ME courses").child("2nd year").child("ME204").child(mUserId).setValue(student);
                    }
                    if(t6==crsopts[0]){
                        mDatabase.child("ME courses").child("2nd year").child("ME205").child(mUserId).setValue(student);
                    }

                    if(te1==crsopts[0]) {
                        mDatabase.child("ME courses").child("2nd year").child("ME250").child(mUserId).setValue(student);
                    }
                    if(te2==crsopts[0]){
                        mDatabase.child("ME courses").child("2nd year").child("ME251").child(mUserId).setValue(student);
                    }

                    mDatabase.child("ME students").child("2nd year").child(mUserId).setValue(student);

                    mDatabase.child("ME students").child("2nd year").child(mUserId).child(c2opts[0]).setValue(t1);
                    mDatabase.child("ME students").child("2nd year").child(mUserId).child(c2opts[1]).setValue(t2);
                    mDatabase.child("ME students").child("2nd year").child(mUserId).child(c2opts[2]).setValue(t3);
                    mDatabase.child("ME students").child("2nd year").child(mUserId).child(c2opts[3]).setValue(t4);
                    mDatabase.child("ME students").child("2nd year").child(mUserId).child(c2opts[4]).setValue(t5);
                    mDatabase.child("ME students").child("2nd year").child(mUserId).child(c2opts[5]).setValue(t6);

                    mDatabase.child("ME students").child("2nd year").child(mUserId).child(c2eopts[0]).setValue(te1);
                    mDatabase.child("ME students").child("2nd year").child(mUserId).child(c2eopts[1]).setValue(te2);

                    User user=new User(namestr,branchchosen,yearchosen,regchosen,rollchosen,t1,t2,t3,t4,t5,t6,te1,te2);

                    mDatabase.child("users").child(mUserId).setValue(user);
                }
                //FOR THIRD YEAR
                if (yearchosen == "Third Year") {
                    if(t1==crsopts[0]){
                        mDatabase.child("ME courses").child("3rd year").child("ME300").child(mUserId).setValue(student);
                    }
                    if(t2==crsopts[0]){
                        mDatabase.child("ME courses").child("3rd year").child("ME301").child(mUserId).setValue(student);
                    }
                    if(t3==crsopts[0]){
                        mDatabase.child("ME courses").child("3rd year").child("ME302").child(mUserId).setValue(student);
                    }
                    if(t4==crsopts[0]){
                        mDatabase.child("ME courses").child("3rd year").child("ME303").child(mUserId).setValue(student);
                    }
                    if(t5==crsopts[0]){
                        mDatabase.child("ME courses").child("3rd year").child("ME304").child(mUserId).setValue(student);
                    }

                    if(te1==crsopts[0]) {
                        mDatabase.child("ME courses").child("3rd year").child("ME350").child(mUserId).setValue(student);
                    }
                    if(te2==crsopts[0]){
                        mDatabase.child("ME courses").child("3rd year").child("ME351").child(mUserId).setValue(student);
                    }

                    mDatabase.child("ME students").child("3rd year").child(mUserId).setValue(student);

                    mDatabase.child("ME students").child("3rd year").child(mUserId).child(c3opts[0]).setValue(t1);
                    mDatabase.child("ME students").child("3rd year").child(mUserId).child(c3opts[1]).setValue(t2);
                    mDatabase.child("ME students").child("3rd year").child(mUserId).child(c3opts[2]).setValue(t3);
                    mDatabase.child("ME students").child("3rd year").child(mUserId).child(c3opts[3]).setValue(t4);
                    mDatabase.child("ME students").child("3rd year").child(mUserId).child(c3opts[4]).setValue(t5);

                    mDatabase.child("ME students").child("3rd year").child(mUserId).child(c3eopts[0]).setValue(te1);
                    mDatabase.child("ME students").child("3rd year").child(mUserId).child(c3eopts[1]).setValue(te2);

                    User user=new User(namestr,branchchosen,yearchosen,regchosen,rollchosen,t1,t2,t3,t4,t5,"",te1,te2);

                    mDatabase.child("users").child(mUserId).setValue(user);
                }
                //FOR FINAL YEAR
                if (yearchosen == "Fourth Year") {
                    if(t1.equals(crsopts[0])){
                        mDatabase.child("ME courses").child("4th year").child("ME400").child(mUserId).setValue(student);
                    }
                    if(t2.equals(crsopts[0])){
                        mDatabase.child("ME courses").child("4th year").child("ME401").child(mUserId).setValue(student);
                    }
                    if(t3.equals(crsopts[0])){
                        mDatabase.child("ME courses").child("4th year").child("ME402").child(mUserId).setValue(student);
                    }
                    if(t4.equals(crsopts[0])){
                        mDatabase.child("ME courses").child("4th year").child("ME403").child(mUserId).setValue(student);
                    }

                    if(te1.equals(crsopts[0])) {
                        mDatabase.child("ME courses").child("4th year").child("ME450").child(mUserId).setValue(student);
                    }
                    if(te2.equals(crsopts[0])){
                        mDatabase.child("ME courses").child("4th year").child("ME451").child(mUserId).setValue(student);
                    }

                    mDatabase.child("ME students").child("4th year").child(mUserId).setValue(student);

                    mDatabase.child("ME students").child("4th year").child(mUserId).child(c4opts[0]).setValue(t1);
                    mDatabase.child("ME students").child("4th year").child(mUserId).child(c4opts[1]).setValue(t2);
                    mDatabase.child("ME students").child("4th year").child(mUserId).child(c4opts[2]).setValue(t3);
                    mDatabase.child("ME students").child("4th year").child(mUserId).child(c4opts[3]).setValue(t4);

                    mDatabase.child("ME students").child("4th year").child(mUserId).child(c4eopts[0]).setValue(te1);
                    mDatabase.child("ME students").child("4th year").child(mUserId).child(c4eopts[1]).setValue(te2);

                    User user=new User(namestr,branchchosen,yearchosen,regchosen,rollchosen,t1,t2,t3,t4,"","",te1,te2);

                    mDatabase.child("users").child(mUserId).setValue(user);
                }
            }

        }
        //Toast.makeText(this, "Saved data !", Toast.LENGTH_SHORT).show();


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

            String namestr = name.getText().toString();
            String regchosen = reg.getText().toString();
            String rollchosen = roll.getText().toString();
            if (namestr.length() <= 2) {
                Toast.makeText(this, "Name field too short !", Toast.LENGTH_SHORT).show();
            }  else if (regchosen.length() != 6) {
                Toast.makeText(this, "Registration number must be of 6 digits !", Toast.LENGTH_SHORT).show();
            } else if (rollchosen.length() != 7) {
                Toast.makeText(this, "Invalid roll number !", Toast.LENGTH_SHORT).show();
            } else {

                lock = 1;

                if (yearchosen == "Second Year") {

                    sp1=(Spinner)findViewById(R.id.s1);
                    ArrayAdapter<String> adaptersp1 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp1.setAdapter(adaptersp1);
                    sp1.setOnItemSelectedListener(this);
                    sp1.setEnabled(true);

                    sp2=(Spinner)findViewById(R.id.s2);
                    ArrayAdapter<String> adaptersp2 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp2.setAdapter(adaptersp2);
                    sp2.setOnItemSelectedListener(this);
                    sp2.setEnabled(true);

                    sp3=(Spinner)findViewById(R.id.s3);
                    ArrayAdapter<String> adaptersp3 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp3.setAdapter(adaptersp3);
                    sp3.setOnItemSelectedListener(this);
                    sp3.setEnabled(true);

                    sp4=(Spinner)findViewById(R.id.s4);
                    ArrayAdapter<String> adaptersp4 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp4.setAdapter(adaptersp4);
                    sp4.setOnItemSelectedListener(this);
                    sp4.setEnabled(true);

                    sp5=(Spinner)findViewById(R.id.s5);
                    ArrayAdapter<String> adaptersp5 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp5.setAdapter(adaptersp5);
                    sp5.setOnItemSelectedListener(this);
                    sp5.setEnabled(true);

                    sp6=(Spinner)findViewById(R.id.s6);
                    ArrayAdapter<String> adaptersp6 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp6.setAdapter(adaptersp6);
                    sp6.setOnItemSelectedListener(this);
                    sp6.setEnabled(true);

                    spe1=(Spinner)findViewById(R.id.se1);
                    ArrayAdapter<String> adapterspe1 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    spe1.setAdapter(adapterspe1);
                    spe1.setOnItemSelectedListener(this);
                    spe1.setEnabled(true);

                    spe2=(Spinner)findViewById(R.id.se2);
                    ArrayAdapter<String> adapterspe2 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    spe2.setAdapter(adapterspe2);
                    spe2.setOnItemSelectedListener(this);
                    spe2.setEnabled(true);

                    if (branchchosen == "Computer Science and Engineering") {
                        b1.setText(c2opts[0]);
                        b2.setText(c2opts[1]);
                        b3.setText(c2opts[2]);
                        b4.setText(c2opts[3]);
                        b5.setText(c2opts[4]);
                        b6.setText(c2opts[5]);
                        be1.setText(c2eopts[0]);
                        be2.setText(c2eopts[1]);
                    }
                    if (branchchosen == "Electrical and Electronic Engineering") {
                        b1.setText(e2opts[0]);
                        b2.setText(e2opts[1]);
                        b3.setText(e2opts[2]);
                        b4.setText(e2opts[3]);
                        b5.setText(e2opts[4]);
                        b6.setText(e2opts[5]);
                        be1.setText(e2eopts[0]);
                        be2.setText(e2eopts[1]);
                    }
                    if (branchchosen == "Mechanical Engineering") {
                        b1.setText(m2opts[0]);
                        b2.setText(m2opts[1]);
                        b3.setText(m2opts[2]);
                        b4.setText(m2opts[3]);
                        b5.setText(m2opts[4]);
                        b6.setText(m2opts[5]);
                        be1.setText(m2eopts[0]);
                        be2.setText(m2eopts[1]);
                    }
                }

                if (yearchosen == "Third Year") {

                    sp1=(Spinner)findViewById(R.id.s1);
                    ArrayAdapter<String> adaptersp1 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp1.setAdapter(adaptersp1);
                    sp1.setOnItemSelectedListener(this);
                    sp1.setEnabled(true);

                    sp2=(Spinner)findViewById(R.id.s2);
                    ArrayAdapter<String> adaptersp2 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp2.setAdapter(adaptersp2);
                    sp2.setOnItemSelectedListener(this);
                    sp2.setEnabled(true);

                    sp3=(Spinner)findViewById(R.id.s3);
                    ArrayAdapter<String> adaptersp3 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp3.setAdapter(adaptersp3);
                    sp3.setOnItemSelectedListener(this);
                    sp3.setEnabled(true);

                    sp4=(Spinner)findViewById(R.id.s4);
                    ArrayAdapter<String> adaptersp4 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp4.setAdapter(adaptersp4);
                    sp4.setOnItemSelectedListener(this);
                    sp4.setEnabled(true);

                    sp5=(Spinner)findViewById(R.id.s5);
                    ArrayAdapter<String> adaptersp5 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp5.setAdapter(adaptersp5);
                    sp5.setOnItemSelectedListener(this);
                    sp5.setEnabled(true);

                    sp6=(Spinner)findViewById(R.id.s6);
                    ArrayAdapter<String> adaptersp6 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp6.setAdapter(adaptersp6);
                    sp6.setOnItemSelectedListener(this);
                    sp6.setEnabled(false);

                    spe1=(Spinner)findViewById(R.id.se1);
                    ArrayAdapter<String> adapterspe1 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    spe1.setAdapter(adapterspe1);
                    spe1.setOnItemSelectedListener(this);
                    spe1.setEnabled(true);

                    spe2=(Spinner)findViewById(R.id.se2);
                    ArrayAdapter<String> adapterspe2 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    spe2.setAdapter(adapterspe2);
                    spe2.setOnItemSelectedListener(this);
                    spe2.setEnabled(true);

                    b6.setText("");

                    if (branchchosen == "Computer Science and Engineering") {
                        b1.setText(c3opts[0]);
                        b2.setText(c3opts[1]);
                        b3.setText(c3opts[2]);
                        b4.setText(c3opts[3]);
                        b5.setText(c3opts[4]);
                        be1.setText(c3eopts[0]);
                        be2.setText(c3eopts[1]);
                    }
                    if (branchchosen == "Electrical and Electronic Engineering") {
                        b1.setText(e3opts[0]);
                        b2.setText(e3opts[1]);
                        b3.setText(e3opts[2]);
                        b4.setText(e3opts[3]);
                        b5.setText(e3opts[4]);
                        be1.setText(e3eopts[0]);
                        be2.setText(e3eopts[1]);
                    }
                    if (branchchosen == "Mechanical Engineering") {
                        b1.setText(m3opts[0]);
                        b2.setText(m3opts[1]);
                        b3.setText(m3opts[2]);
                        b4.setText(m3opts[3]);
                        b5.setText(m3opts[4]);
                        be1.setText(m3eopts[0]);
                        be2.setText(m3eopts[1]);
                    }
                }

                if (yearchosen == "Fourth Year") {

                    sp1=(Spinner)findViewById(R.id.s1);
                    ArrayAdapter<String> adaptersp1 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp1.setAdapter(adaptersp1);
                    sp1.setOnItemSelectedListener(this);
                    sp1.setEnabled(true);

                    sp2=(Spinner)findViewById(R.id.s2);
                    ArrayAdapter<String> adaptersp2 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp2.setAdapter(adaptersp2);
                    sp2.setOnItemSelectedListener(this);
                    sp2.setEnabled(true);

                    sp3=(Spinner)findViewById(R.id.s3);
                    ArrayAdapter<String> adaptersp3 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp3.setAdapter(adaptersp3);
                    sp3.setOnItemSelectedListener(this);
                    sp3.setEnabled(true);

                    sp4=(Spinner)findViewById(R.id.s4);
                    ArrayAdapter<String> adaptersp4 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp4.setAdapter(adaptersp4);
                    sp4.setOnItemSelectedListener(this);
                    sp4.setEnabled(true);

                    sp5=(Spinner)findViewById(R.id.s5);
                    ArrayAdapter<String> adaptersp5 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp5.setAdapter(adaptersp5);
                    sp5.setOnItemSelectedListener(this);
                    sp5.setEnabled(false);

                    sp6=(Spinner)findViewById(R.id.s6);
                    ArrayAdapter<String> adaptersp6 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    sp6.setAdapter(adaptersp6);
                    sp6.setOnItemSelectedListener(this);
                    sp6.setEnabled(false);

                    spe1=(Spinner)findViewById(R.id.se1);
                    ArrayAdapter<String> adapterspe1 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    spe1.setAdapter(adapterspe1);
                    spe1.setOnItemSelectedListener(this);
                    spe1.setEnabled(true);

                    spe2=(Spinner)findViewById(R.id.se2);
                    ArrayAdapter<String> adapterspe2 = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, crsopts);
                    spe2.setAdapter(adapterspe2);
                    spe2.setOnItemSelectedListener(this);
                    spe2.setEnabled(true);

                    b5.setText("");
                    b6.setText("");

                    if (branchchosen == "Computer Science and Engineering") {
                        b1.setText(c4opts[0]);
                        b2.setText(c4opts[1]);
                        b3.setText(c4opts[2]);
                        b4.setText(c4opts[3]);
                        be1.setText(c4eopts[0]);
                        be2.setText(c4eopts[1]);
                    }
                    if (branchchosen == "Electrical and Electronic Engineering") {
                        b1.setText(e4opts[0]);
                        b2.setText(e4opts[1]);
                        b3.setText(e4opts[2]);
                        b4.setText(e4opts[3]);
                        be1.setText(e4eopts[0]);
                        be2.setText(e4eopts[1]);
                    }
                    if (branchchosen == "Mechanical Engineering") {
                        b1.setText(m4opts[0]);
                        b2.setText(m4opts[1]);
                        b3.setText(m4opts[2]);
                        b4.setText(m4opts[3]);
                        be1.setText(m4eopts[0]);
                        be2.setText(m4eopts[1]);
                    }
                }

            }

        }
        if (v == regbutton) {
            if (lock == 1 ) {
                saveuserinfo();
                Toast.makeText(this, "Registration Done !", Toast.LENGTH_SHORT).show();
                finish();
                Intent gotonext = new Intent(this, OnLoginActivity.class);
                startActivity(gotonext);
            } else {
                Toast.makeText(this, "Enter proper details !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        Spinner spinner1 = (Spinner) parent;

        Spinner si1=(Spinner) parent;
        Spinner si2=(Spinner) parent;
        Spinner si3=(Spinner) parent;
        Spinner si4=(Spinner) parent;
        Spinner si5=(Spinner) parent;
        Spinner si6=(Spinner) parent;
        Spinner sei1=(Spinner) parent;
        Spinner sei2=(Spinner) parent;

        if(spinner.getId() == R.id.SpinnerBranch)
        {
            branchchosen=parent.getItemAtPosition(position).toString();
        }
        if(spinner1.getId() == R.id.SpinnerYear)
        {
            yearchosen=parent.getItemAtPosition(position).toString();
        }
        if(si1.getId() == R.id.s1){
            t1=parent.getItemAtPosition(position).toString();
        }
        if(si2.getId() == R.id.s2){
            t2=parent.getItemAtPosition(position).toString();
        }
        if(si3.getId() == R.id.s3){
            t3=parent.getItemAtPosition(position).toString();
        }
        if(si4.getId() == R.id.s4){
            t4=parent.getItemAtPosition(position).toString();
        }
        if(si5.getId() == R.id.s5){
            t5=parent.getItemAtPosition(position).toString();
        }
        if(si6.getId() == R.id.s6){
            t6=parent.getItemAtPosition(position).toString();
        }
        if(sei1.getId() == R.id.se1){
            te1=parent.getItemAtPosition(position).toString();
        }
        if(sei2.getId() == R.id.se2){
            te2=parent.getItemAtPosition(position).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}




