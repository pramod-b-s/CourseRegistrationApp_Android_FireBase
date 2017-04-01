package com.google.pramodbs.collegehelper;
//LOC=385
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Courses extends AppCompatActivity {

    private TextView b1,b2,b3,b4,b5,b6,be1,be2,hd;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    private static int cr1,cr2,cr3,cr4,cr5,cr6,el1,el2;
    private static String cr1nm,cr2nm,cr3nm,cr4nm,cr5nm,cr6nm,el1nm,el2nm,branch,year,name,roll;
    private static String mUserId,reg;

    private String[] yearopts,branchopts,crsopts,c2opts,c3opts,c4opts,c2eopts,c3eopts,c4eopts,e2opts,e3opts,
            e4opts,e2eopts,e3eopts,e4eopts,m2opts,m3opts,m4opts,m2eopts,m3eopts,m4eopts,c2fac,c3fac,c4fac,e2fac,e3fac,e4fac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mUserId=user.getUid();

        hd=(TextView)findViewById(R.id.disp);

        b1=(TextView)findViewById(R.id.crs1);
        b2=(TextView)findViewById(R.id.crs2);
        b3=(TextView)findViewById(R.id.crs3);
        b4=(TextView)findViewById(R.id.crs4);
        b5=(TextView)findViewById(R.id.crs5);
        b6=(TextView)findViewById(R.id.crs6);
        be1=(TextView)findViewById(R.id.elec1);
        be2=(TextView)findViewById(R.id.elec2);

        this.yearopts = new String[] {
                "Second Year", "Third Year", "Fourth Year"
        };
        this.branchopts = new String[] {
                "Computer Science and Engineering", "Electrical and Electronic Engineering", "Mechanical Engineering"
        };
        this.crsopts=new String[]{
                "Undertake","Drop"
        };

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

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User post = dataSnapshot.getValue(User.class);

                cr1nm=post.C1;
                cr2nm=post.C2;
                cr3nm=post.C3;
                cr4nm=post.C4;
                cr5nm=post.C5;
                cr6nm=post.C6;
                el1nm=post.E1;
                el2nm=post.E2;
                name=post.Name;
                branch=post.Branch;
                year=post.Year;
                reg=post.regnumber;
                roll=post.rollnumber;

                if(reg.length()!=6){
                    Toast.makeText(Courses.this,"Please Register first !",Toast.LENGTH_SHORT).show();
                    finish();
                    Intent newintlg=new Intent(Courses.this,OnLoginActivity.class);
                    startActivity(newintlg);
                }
                //Toast.makeText(Courses.this,name+cr1nm,Toast.LENGTH_SHORT).show();
                hd.setText(name+"'s Courses");

                if (year.equals("Second Year")) {

                    if (branch.equals("Computer Science and Engineering")) {
                        if(cr1nm.equals(crsopts[0])){
                            b1.setText(c2opts[0]);
                        }
                        if(cr2nm.equals(crsopts[0])){
                            b2.setText(c2opts[1]);
                        }
                        if(cr3nm.equals(crsopts[0])){
                            b3.setText(c2opts[2]);
                        }
                        if(cr4nm.equals(crsopts[0])){
                            b4.setText(c2opts[3]);
                        }
                        if(cr5nm.equals(crsopts[0])){
                            b5.setText(c2opts[4]);
                        }
                        if(cr6nm.equals(crsopts[0])){
                            b6.setText(c2opts[5]);
                        }
                        if(el1nm.equals(crsopts[0])){
                            be1.setText(c2eopts[0]);
                        }
                        if(el2nm.equals(crsopts[0])){
                            be2.setText(c2eopts[1]);
                        }
                    }
                    if (branch.equals("Electrical and Electronic Engineering")) {
                        if(cr1nm.equals(crsopts[0])){
                            b1.setText(e2opts[0]);
                        }
                        if(cr2nm.equals(crsopts[0])){
                            b2.setText(e2opts[1]);
                        }
                        if(cr3nm.equals(crsopts[0])){
                            b3.setText(e2opts[2]);
                        }
                        if(cr4nm.equals(crsopts[0])){
                            b4.setText(e2opts[3]);
                        }
                        if(cr5nm.equals(crsopts[0])){
                            b5.setText(e2opts[4]);
                        }
                        if(cr6nm.equals(crsopts[0])){
                            b6.setText(e2opts[5]);
                        }
                        if(el1nm.equals(crsopts[0])){
                            be1.setText(e2eopts[0]);
                        }
                        if(el2nm.equals(crsopts[0])){
                            be2.setText(e2eopts[1]);
                        }
                    }
                    if (branch.equals("Mechanical Engineering")) {
                        if(cr1nm.equals(crsopts[0])){
                            b1.setText(m2opts[0]);
                        }
                        if(cr2nm.equals(crsopts[0])){
                            b2.setText(m2opts[1]);
                        }
                        if(cr3nm.equals(crsopts[0])){
                            b3.setText(m2opts[2]);
                        }
                        if(cr4nm.equals(crsopts[0])){
                            b4.setText(m2opts[3]);
                        }
                        if(cr5nm.equals(crsopts[0])){
                            b5.setText(m2opts[4]);
                        }
                        if(cr6nm.equals(crsopts[0])){
                            b6.setText(m2opts[5]);
                        }
                        if(el1nm.equals(crsopts[0])){
                            be1.setText(m2eopts[0]);
                        }
                        if(el2nm.equals(crsopts[0])){
                            be2.setText(m2eopts[1]);
                        }
                    }
                }

                if (year.equals("Third Year")) {

                    if (branch.equals("Computer Science and Engineering")) {
                        if(cr1nm.equals(crsopts[0])){
                            b1.setText(c3opts[0]);
                        }
                        if(cr2nm.equals(crsopts[0])){
                            b2.setText(c3opts[1]);
                        }
                        if(cr3nm.equals(crsopts[0])){
                            b3.setText(c3opts[2]);
                        }
                        if(cr4nm.equals(crsopts[0])){
                            b4.setText(c3opts[3]);
                        }
                        if(cr5nm.equals(crsopts[0])){
                            b5.setText(c3opts[4]);
                        }
                        if(el1nm.equals(crsopts[0])){
                            be1.setText(c3eopts[0]);
                        }
                        if(el2nm.equals(crsopts[0])){
                            be2.setText(c3eopts[1]);
                        }
                    }
                    if (branch.equals("Electrical and Electronic Engineering")) {
                        if(cr1nm.equals(crsopts[0])){
                            b1.setText(e3opts[0]);
                        }
                        if(cr2nm.equals(crsopts[0])){
                            b2.setText(e3opts[1]);
                        }
                        if(cr3nm.equals(crsopts[0])){
                            b3.setText(e3opts[2]);
                        }
                        if(cr4nm.equals(crsopts[0])){
                            b4.setText(e3opts[3]);
                        }
                        if(cr5nm.equals(crsopts[0])){
                            b5.setText(e3opts[4]);
                        }
                        if(el1nm.equals(crsopts[0])){
                            be1.setText(e3eopts[0]);
                        }
                        if(el2nm.equals(crsopts[0])){
                            be2.setText(e3eopts[1]);
                        }
                    }
                    if (branch.equals("Mechanical Engineering")) {
                        if(cr1nm.equals(crsopts[0])){
                            b1.setText(m3opts[0]);
                        }
                        if(cr2nm.equals(crsopts[0])){
                            b2.setText(m3opts[1]);
                        }
                        if(cr3nm.equals(crsopts[0])){
                            b3.setText(m3opts[2]);
                        }
                        if(cr4nm.equals(crsopts[0])){
                            b4.setText(m3opts[3]);
                        }
                        if(cr5nm.equals(crsopts[0])){
                            b5.setText(m3opts[4]);
                        }
                        if(el1nm.equals(crsopts[0])){
                            be1.setText(m3eopts[0]);
                        }
                        if(el2nm.equals(crsopts[0])){
                            be2.setText(m3eopts[1]);
                        }
                    }
                }

                if (year.equals("Fourth Year")) {

                    if (branch.equals("Computer Science and Engineering")) {
                        if(cr1nm.equals(crsopts[0])){
                            b1.setText(c4opts[0]);
                        }
                        if(cr2nm.equals(crsopts[0])){
                            b2.setText(c4opts[1]);
                        }
                        if(cr3nm.equals(crsopts[0])){
                            b3.setText(c4opts[2]);
                        }
                        if(cr4nm.equals(crsopts[0])){
                            b4.setText(c4opts[3]);
                        }
                        if(el1nm.equals(crsopts[0])){
                            be1.setText(c4eopts[0]);
                        }
                        if(el2nm.equals(crsopts[0])){
                            be2.setText(c4eopts[1]);
                        }
                    }
                    if (branch.equals("Electrical and Electronic Engineering")) {
                        if(cr1nm.equals(crsopts[0])){
                            b1.setText(e4opts[0]);
                        }
                        if(cr2nm.equals(crsopts[0])){
                            b2.setText(e4opts[1]);
                        }
                        if(cr3nm.equals(crsopts[0])){
                            b3.setText(e4opts[2]);
                        }
                        if(cr4nm.equals(crsopts[0])){
                            b4.setText(e4opts[3]);
                        }
                        if(el1nm.equals(crsopts[0])) {
                            be1.setText(e4eopts[0]);
                        }
                        if(el2nm.equals(crsopts[0])){
                            be2.setText(e4eopts[1]);
                        }
                    }
                    if (branch.equals("Mechanical Engineering")) {
                        if(cr1nm.equals(crsopts[0])){
                            b1.setText(m4opts[0]);
                        }
                        if(cr2nm.equals(crsopts[0])){
                            b2.setText(m4opts[1]);
                        }
                        if(cr3nm.equals(crsopts[0])){
                            b3.setText(m4opts[2]);
                        }
                        if(cr4nm.equals(crsopts[0])){
                            b4.setText(m4opts[3]);
                        }
                        if(el1nm.equals(crsopts[0])){
                            be1.setText(m4eopts[0]);
                        }
                        if(el2nm.equals(crsopts[0])){
                            be2.setText(m4eopts[1]);
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
        mDatabase.child("USERS").child(mUserId).addValueEventListener(postListener);

    }
}

