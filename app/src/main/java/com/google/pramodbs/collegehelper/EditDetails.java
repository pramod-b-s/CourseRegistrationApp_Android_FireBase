package com.google.pramodbs.collegehelper;
//LOC=786
import android.app.ProgressDialog;
import android.content.Intent;
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

public class EditDetails extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    //private FirebaseUser user;
    //private FirebaseAnalytics mFirebaseAnalytics;

    private Button logoutbtn,save,regbutton;
    private DatabaseReference mDatabase;
    private String mUserId;
    private Query queryref;

    private TextView title,cores,electives;
    private EditText name,roll,reg;
    private Spinner year,branch;
    private String yearchosen,branchchosen;

    private ProgressDialog progress;

    private String readname,readrollno,readregno,readbranch,readyear;
    private static String cr1nm,cr2nm,cr3nm,cr4nm,cr5nm,cr6nm,el1nm,el2nm;

    private Firebase mref;

    private CheckBox b1,b2,b3,b4,b5,b6,be1,be2;

    private ValueEventListener postListener;

    private String[] yearopts,branchopts,c2opts,c3opts,c4opts,c2eopts,c3eopts,c4eopts,e2opts,e3opts,
            e4opts,e2eopts,e3eopts,e4eopts,m2opts,m3opts,m4opts,m2eopts,m3eopts,m4eopts;
    //private String[] degopts;
    int lock=0,lock1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //userid=mDatabase.getKey();
        save=(Button)findViewById(R.id.savebtn);
        logoutbtn=(Button)findViewById(R.id.logoutbtn);
        regbutton=(Button)findViewById(R.id.regbtn);

        progress=new ProgressDialog(this);

        b1=(CheckBox)findViewById(R.id.c1);
        b2=(CheckBox)findViewById(R.id.c2);
        b3=(CheckBox)findViewById(R.id.c3);
        b4=(CheckBox)findViewById(R.id.c4);
        b5=(CheckBox)findViewById(R.id.c5);
        b6=(CheckBox)findViewById(R.id.c6);
        be1=(CheckBox)findViewById(R.id.ce1);
        be2=(CheckBox)findViewById(R.id.ce2);

        this.yearopts = new String[] {
                "Second Year", "Third Year", "Fourth Year"
        };
        this.branchopts = new String[] {
                "Computer Science and Engineering", "Electrical and Electronic Engineering", "Mechanical Engineering"
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
                "EE200", "EE201", "EE202","EE203","EE204","EE205"
        };
        this.e3opts = new String[] {
                "EE300", "EE301", "EE302","EE303","EE304",""
        };
        this.e4opts = new String[] {
                "EE400", "EE401", "EE402","EE403","",""
        };
        this.e2eopts = new String[] {
                "EE250", "EE251"
        };
        this.e3eopts = new String[] {
                "EE350", "EE351"
        };
        this.e4eopts = new String[] {
                "EE450", "EE451"
        };

        //mech
        this.m2opts = new String[] {
                "ME200", "ME201", "ME202","ME203","ME204","ME205"
        };
        this.m3opts = new String[] {
                "ME300", "ME301", "ME302","ME303","ME304",""
        };
        this.m4opts = new String[] {
                "ME400", "ME401", "ME402","ME403","",""
        };
        this.m2eopts = new String[] {
                "ME250", "ME251"
        };
        this.m3eopts = new String[] {
                "ME350", "ME351"
        };
        this.m4eopts = new String[] {
                "ME450", "ME451"
        };

        year=(Spinner)findViewById(R.id.SpinnerYear);
        branch=(Spinner)findViewById(R.id.SpinnerBranch);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, yearopts);
        year.setAdapter(adapter);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, branchopts);
        branch.setAdapter(adapter1);
        year.setOnItemSelectedListener(this);
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

        /*mDatabase.orderByValue().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        })*/
        /*mref=new Firebase("https://collegeassistant-e803f.firebaseio.com/");

        mref.child("users").child("regno").addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Toast.makeText(EditDetails.this,dataSnapshot.getValue().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/

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

                if(readname.length()<=2){
                    Toast.makeText(EditDetails.this,"Please Register first !!",Toast.LENGTH_SHORT).show();
                    finish();
                }

                name.setText(readname);
                roll.setText(readrollno);
                reg.setText(readregno);
                if(readbranch==branchopts[0]) {
                    year.setSelection(0);
                }
                if(readbranch==branchopts[1]) {
                    year.setSelection(1);
                }
                if(readbranch==branchopts[2]) {
                    year.setSelection(2);
                }
                if(readyear==branchopts[0]) {
                    year.setSelection(0);
                }
                if(readyear==branchopts[1]) {
                    year.setSelection(1);
                }
                if(readyear==branchopts[2]) {
                    year.setSelection(2);
                }

                Toast.makeText(EditDetails.this,"Welcome back "+readname,Toast.LENGTH_SHORT).show();

                cr1nm=post.CORE1;
                cr2nm=post.CORE2;
                cr3nm=post.CORE3;
                cr4nm=post.CORE4;
                cr5nm=post.CORE5;
                cr6nm=post.CORE6;
                el1nm=post.ELECTIVE1;
                el2nm=post.ELECTIVE2;

                if(cr1nm!=null) {
                    b1.setChecked(true);
                    b1.setText(c2opts[0]);
                }
                if(cr2nm!=null) {
                    b2.setChecked(true);
                    b2.setText(c2opts[1]);
                }
                if(cr3nm!=null){
                    b3.setChecked(true);
                    b3.setText(c2opts[2]);
                }
                if(cr4nm!=null){
                    b4.setChecked(true);
                    b4.setText(c2opts[3]);
                }
                if(cr5nm!=null){
                    b5.setChecked(true);
                    b5.setText(c2opts[4]);
                }
                if(cr6nm!=null){
                    b6.setChecked(true);
                    b6.setText(c2opts[5]);
                }

                if(el1nm!=null){
                    be1.setChecked(true);
                    be1.setText(c2eopts[0]);
                }
                if(el2nm!=null){
                    be2.setChecked(true);
                    be2.setText(c2eopts[1]);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        };
        mDatabase.child("users").child(mUserId).addValueEventListener(postListener);

        /*mDatabase.child("CO students").child("2nd year").child(mUserId).removeValue();
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

        Toast.makeText(this, "Existing details removed\nEnter new details !", Toast.LENGTH_SHORT).show();
*/
    }


    private void saveuserinfo(){

        String namestr = name.getText().toString();
        String regchosen = reg.getText().toString();
        String rollchosen = roll.getText().toString();

        Student student=new Student(namestr,rollchosen,regchosen,branchchosen,yearchosen);
        mDatabase.child("users").child(mUserId).setValue(student);

        /*mDatabase.child("names").child(mUserId).child("Name").setValue(name);
        mDatabase.child("branches").child(mUserId).child("Branch").setValue(branchchosen);
        mDatabase.child("years").child(mUserId).child("Year").setValue(yearchosen);

        /*Map<String,User> mParent = new HashMap<String ,User>();
        mParent.put(namestr,new User(branchchosen,yearchosen));*/

        /*if(b1.isChecked()==true && b2.isChecked()==true && b3.isChecked()==true &&
                b4.isChecked()==true && b5.isChecked()==true && b6.isChecked()==true) {
                if (yearchosen == "Second Year") {
                    lock1 = 1;
                } else{
                    Toast.makeText(ProfileActivity.this, "All CORE Courses MUST be chosen !", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        else if(b1.isChecked()==true && b2.isChecked()==true && b3.isChecked()==true &&
                b4.isChecked()==true && b5.isChecked()==true) {
                if (yearchosen == "Third Year") {
                    lock1 = 1;
                }else{
                    Toast.makeText(ProfileActivity.this, "All CORE Courses MUST be chosen !", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        else if(b1.isChecked()==true && b2.isChecked()==true && b3.isChecked()==true &&
                b4.isChecked()==true) {
            if (yearchosen == "Fourth Year") {
                lock1 = 1;
            } else {
                Toast.makeText(ProfileActivity.this, "All CORE Courses MUST be chosen !", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(ProfileActivity.this, " !"+lock1, Toast.LENGTH_SHORT).show();*/

        //FOR COMPS
        if (branchchosen == "Computer Science and Engineering") {
            //FOR SECOND YEAR
            if (yearchosen == "Second Year") {
                mDatabase.child("CO courses").child("2nd year").child("CO200").child(mUserId).setValue(student);
                mDatabase.child("CO courses").child("2nd year").child("CO201").child(mUserId).setValue(student);
                mDatabase.child("CO courses").child("2nd year").child("CO202").child(mUserId).setValue(student);
                mDatabase.child("CO courses").child("2nd year").child("CO203").child(mUserId).setValue(student);
                mDatabase.child("CO courses").child("2nd year").child("CO204").child(mUserId).setValue(student);
                mDatabase.child("CO courses").child("2nd year").child("CO205").child(mUserId).setValue(student);

                if (be1.isChecked() == false && be2.isChecked() == false) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, c2opts[0],
                            c2opts[1], c2opts[2], c2opts[3], c2opts[4], c2opts[5]);
                    mDatabase.child("CO students").child("2nd year").child(mUserId).setValue(user);
                    mDatabase.child("users").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true && be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, c2opts[0],
                            c2opts[1], c2opts[2], c2opts[3], c2opts[4], c2opts[5], c2eopts[0], c2eopts[1]);
                    mDatabase.child("CO courses").child("2nd year").child("CO250").child(mUserId).setValue(student);
                    mDatabase.child("CO courses").child("2nd year").child("CO251").child(mUserId).setValue(student);
                    mDatabase.child("CO students").child("2nd year").child(mUserId).setValue(user);
                    mDatabase.child("users").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, c2opts[0],
                            c2opts[1], c2opts[2], c2opts[3], c2opts[4], c2opts[5], c2eopts[0]);
                    mDatabase.child("CO courses").child("2nd year").child("CO250").child(mUserId).setValue(student);
                    mDatabase.child("CO students").child("2nd year").child(mUserId).setValue(user);
                    mDatabase.child("users").child(mUserId).setValue(user);
                } else if (be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, c2opts[0],
                            c2opts[1], c2opts[2], c2opts[3], c2opts[4], c2opts[5], c2eopts[1]);
                    mDatabase.child("CO courses").child("2nd year").child("CO251").child(mUserId).setValue(student);
                    mDatabase.child("CO students").child("2nd year").child(mUserId).setValue(user);
                    mDatabase.child("users").child(mUserId).setValue(user);
                }
            }
            //FOR THIRD YEAR
            if (yearchosen == "Third Year") {

                mDatabase.child("CO courses").child("3rd year").child("CO300").child(mUserId).setValue(student);
                mDatabase.child("CO courses").child("3rd year").child("CO301").child(mUserId).setValue(student);
                mDatabase.child("CO courses").child("3rd year").child("CO302").child(mUserId).setValue(student);
                mDatabase.child("CO courses").child("3rd year").child("CO303").child(mUserId).setValue(student);
                mDatabase.child("CO courses").child("3rd year").child("CO304").child(mUserId).setValue(student);

                if (be1.isChecked() == false && be2.isChecked() == false) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, c3opts[0],
                            c3opts[1], c3opts[2], c3opts[3], c3opts[4], c3opts[5]);
                    mDatabase.child("CO students").child("3rd year").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true && be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, c3opts[0],
                            c3opts[1], c3opts[2], c3opts[3], c3opts[4], c3opts[5], c3eopts[0], c3eopts[1]);
                    mDatabase.child("CO courses").child("3rd year").child("CO350").child(mUserId).setValue(student);
                    mDatabase.child("CO courses").child("3rd year").child("CO351").child(mUserId).setValue(student);
                    mDatabase.child("CO students").child("3rd year").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, c3opts[0],
                            c3opts[1], c3opts[2], c3opts[3], c3opts[4], c3opts[5], c3eopts[0]);
                    mDatabase.child("CO courses").child("3rd year").child("CO350").child(mUserId).setValue(student);
                    mDatabase.child("CO students").child("3rd year").child(mUserId).setValue(user);
                } else if (be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, c3opts[0],
                            c3opts[1], c3opts[2], c3opts[3], c3opts[4], c3opts[5], c3eopts[1]);
                    mDatabase.child("CO courses").child("3rd year").child("CO351").child(mUserId).setValue(student);
                    mDatabase.child("CO students").child("3rd year").child(mUserId).setValue(user);
                }
            }
            //FOR FINAL YEAR
            if (yearchosen == "Fourth Year") {
                mDatabase.child("CO courses").child("4th year").child("CO400").child(mUserId).setValue(student);
                mDatabase.child("CO courses").child("4th year").child("CO401").child(mUserId).setValue(student);
                mDatabase.child("CO courses").child("4th year").child("CO402").child(mUserId).setValue(student);
                mDatabase.child("CO courses").child("4th year").child("CO403").child(mUserId).setValue(student);

                if (be1.isChecked() == false && be2.isChecked() == false) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, c4opts[0],
                            c4opts[1], c4opts[2], c4opts[3], c4opts[4], c4opts[5]);
                    mDatabase.child("CO students").child("4th year").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true && be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, c4opts[0],
                            c4opts[1], c4opts[2], c4opts[3], c4opts[4], c4opts[5], c4eopts[0], c4eopts[1]);
                    mDatabase.child("CO courses").child("4th year").child("CO450").child(mUserId).setValue(student);
                    mDatabase.child("CO courses").child("4th year").child("CO451").child(mUserId).setValue(student);
                    mDatabase.child("CO students").child("4th year").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, c4opts[0],
                            c4opts[1], c4opts[2], c4opts[3], c4opts[4], c4opts[5], c4eopts[0]);
                    mDatabase.child("CO courses").child("4th year").child("CO450").child(mUserId).setValue(student);
                    mDatabase.child("CO students").child("4th year").child(mUserId).setValue(user);
                } else if (be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, c4opts[0],
                            c4opts[1], c4opts[2], c4opts[3], c4opts[4], c4opts[5], c4eopts[0], c4eopts[1]);
                    mDatabase.child("CO courses").child("4th year").child("CO450").child(mUserId).setValue(student);
                    mDatabase.child("CO courses").child("4th year").child("CO451").child(mUserId).setValue(student);
                    mDatabase.child("CO students").child("4th year").child(mUserId).setValue(user);
                }
            }
        }


        //FOR EEE
        if (branchchosen == "Electrical and Electronic Engineering") {
            //FOR SECOND YEAR
            if (yearchosen == "Second Year") {
                mDatabase.child("EE courses").child("2nd year").child("EE200").child(mUserId).setValue(student);
                mDatabase.child("EE courses").child("2nd year").child("EE201").child(mUserId).setValue(student);
                mDatabase.child("EE courses").child("2nd year").child("EE202").child(mUserId).setValue(student);
                mDatabase.child("EE courses").child("2nd year").child("EE203").child(mUserId).setValue(student);
                mDatabase.child("EE courses").child("2nd year").child("EE204").child(mUserId).setValue(student);
                mDatabase.child("EE courses").child("2nd year").child("EE205").child(mUserId).setValue(student);

                if (be1.isChecked() == false && be2.isChecked() == false) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, e2opts[0],
                            e2opts[1], e2opts[2], e2opts[3], e2opts[4], e2opts[5]);
                    mDatabase.child("EE students").child("2nd year").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true && be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, e2opts[0],
                            e2opts[1], e2opts[2], e2opts[3], e2opts[4], e2opts[5], e2eopts[0], e2eopts[1]);
                    mDatabase.child("EE courses").child("2nd year").child("EE250").child(mUserId).setValue(student);
                    mDatabase.child("EE courses").child("2nd year").child("EE251").child(mUserId).setValue(student);
                    mDatabase.child("EE students").child("2nd year").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, e2opts[0],
                            e2opts[1], e2opts[2], e2opts[3], e2opts[4], e2opts[5], e2eopts[0]);
                    mDatabase.child("EE courses").child("2nd year").child("EE250").child(mUserId).setValue(student);
                    mDatabase.child("EE students").child("2nd year").child(mUserId).setValue(user);
                } else if (be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, e2opts[0],
                            e2opts[1], e2opts[2], e2opts[3], e2opts[4], e2opts[5], e2eopts[1]);
                    mDatabase.child("EE courses").child("2nd year").child("EE251").child(mUserId).setValue(student);
                    mDatabase.child("EE students").child("2nd year").child(mUserId).setValue(user);
                }
            }
            //FOR THIRD YEAR
            if (yearchosen == "Third Year") {
                mDatabase.child("EE courses").child("3rd year").child("EE300").child(mUserId).setValue(student);
                mDatabase.child("EE courses").child("3rd year").child("EE301").child(mUserId).setValue(student);
                mDatabase.child("EE courses").child("3rd year").child("EE302").child(mUserId).setValue(student);
                mDatabase.child("EE courses").child("3rd year").child("EE303").child(mUserId).setValue(student);
                mDatabase.child("EE courses").child("3rd year").child("EE304").child(mUserId).setValue(student);

                if (be1.isChecked() == false && be2.isChecked() == false) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, e3opts[0],
                            e3opts[1], e3opts[2], e3opts[3], e3opts[4], e3opts[5]);
                    mDatabase.child("EE students").child("3rd year").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true && be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, e3opts[0],
                            e3opts[1], e3opts[2], e3opts[3], e3opts[4], e3opts[5], e3eopts[0], e3eopts[1]);
                    mDatabase.child("EE courses").child("3rd year").child("EE350").child(mUserId).setValue(student);
                    mDatabase.child("EE courses").child("3rd year").child("EE351").child(mUserId).setValue(student);
                    mDatabase.child("EE students").child("3rd year").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, e3opts[0],
                            e3opts[1], e3opts[2], e3opts[3], e3opts[4], e3opts[5], e3eopts[0]);
                    mDatabase.child("EE courses").child("3rd year").child("EE350").child(mUserId).setValue(student);
                    mDatabase.child("EE students").child("3rd year").child(mUserId).setValue(user);
                } else if (be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, e3opts[0],
                            e3opts[1], e3opts[2], e3opts[3], e3opts[4], e3opts[5], e3eopts[1]);
                    mDatabase.child("EE courses").child("3rd year").child("EE351").child(mUserId).setValue(student);
                    mDatabase.child("EE students").child("3rd year").child(mUserId).setValue(user);
                }
            }
            //FOR FINAL YEAR
            if (yearchosen == "Fourth Year") {
                mDatabase.child("EE courses").child("4th year").child("EE400").child(mUserId).setValue(student);
                mDatabase.child("EE courses").child("4th year").child("EE401").child(mUserId).setValue(student);
                mDatabase.child("EE courses").child("4th year").child("EE402").child(mUserId).setValue(student);
                mDatabase.child("EE courses").child("4th year").child("EE403").child(mUserId).setValue(student);

                if (be1.isChecked() == false && be2.isChecked() == false) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, e4opts[0],
                            e4opts[1], e4opts[2], e4opts[3], e4opts[4], e4opts[5]);
                    mDatabase.child("EE students").child("4th year").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true && be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, e4opts[0],
                            e4opts[1], e4opts[2], e4opts[3], e4opts[4], e4opts[5], e4eopts[0], e4eopts[1]);
                    mDatabase.child("EE courses").child("4th year").child("EE450").child(mUserId).setValue(student);
                    mDatabase.child("EE courses").child("4th year").child("EE451").child(mUserId).setValue(student);
                    mDatabase.child("EE students").child("4th year").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, e4opts[0],
                            e4opts[1], e4opts[2], e4opts[3], e4opts[4], e4opts[5], e4eopts[0]);
                    mDatabase.child("EE courses").child("4th year").child("EE450").child(mUserId).setValue(student);
                    mDatabase.child("EE students").child("4th year").child(mUserId).setValue(user);
                } else if (be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, e4opts[0],
                            e4opts[1], e4opts[2], e4opts[3], e4opts[4], e4opts[5], e4eopts[1]);
                    mDatabase.child("EE courses").child("4th year").child("EE451").child(mUserId).setValue(student);
                    mDatabase.child("EE students").child("4th year").child(mUserId).setValue(user);
                }
            }
        }


        //FOR MECH
        if (branchchosen == "Mechanical Engineering") {
            //FOR SECOND YEAR
            if (yearchosen == "Second Year") {
                mDatabase.child("ME courses").child("2nd year").child("ME200").child(mUserId).setValue(student);
                mDatabase.child("ME courses").child("2nd year").child("ME201").child(mUserId).setValue(student);
                mDatabase.child("ME courses").child("2nd year").child("ME202").child(mUserId).setValue(student);
                mDatabase.child("ME courses").child("2nd year").child("ME203").child(mUserId).setValue(student);
                mDatabase.child("ME courses").child("2nd year").child("ME204").child(mUserId).setValue(student);
                mDatabase.child("ME courses").child("2nd year").child("ME205").child(mUserId).setValue(student);

                if (be1.isChecked() == false && be2.isChecked() == false) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, m2opts[0],
                            m2opts[1], m2opts[2], m2opts[3], m2opts[4], m2opts[5]);
                    mDatabase.child("ME students").child("2nd year").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true && be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, m2opts[0],
                            m2opts[1], m2opts[2], m2opts[3], m2opts[4], m2opts[5], m2eopts[0], m2eopts[1]);
                    mDatabase.child("ME courses").child("2nd year").child("ME250").child(mUserId).setValue(student);
                    mDatabase.child("ME courses").child("2nd year").child("ME251").child(mUserId).setValue(student);
                    mDatabase.child("ME students").child("2nd year").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, m2opts[0],
                            m2opts[1], m2opts[2], m2opts[3], m2opts[4], m2opts[5], m2eopts[0]);
                    mDatabase.child("ME courses").child("2nd year").child("ME250").child(mUserId).setValue(student);
                    mDatabase.child("ME students").child("2nd year").child(mUserId).setValue(user);
                } else if (be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, m2opts[0],
                            m2opts[1], m2opts[2], m2opts[3], m2opts[4], m2opts[5], m2eopts[1]);
                    mDatabase.child("ME courses").child("2nd year").child("ME251").child(mUserId).setValue(student);
                    mDatabase.child("ME students").child("2nd year").child(mUserId).setValue(user);
                }
            }
            //FOR THIRD YEAR
            if (yearchosen == "Third Year") {
                mDatabase.child("ME courses").child("3rd year").child("ME300").child(mUserId).setValue(student);
                mDatabase.child("ME courses").child("3rd year").child("ME301").child(mUserId).setValue(student);
                mDatabase.child("ME courses").child("3rd year").child("ME302").child(mUserId).setValue(student);
                mDatabase.child("ME courses").child("3rd year").child("ME303").child(mUserId).setValue(student);
                mDatabase.child("ME courses").child("3rd year").child("ME304").child(mUserId).setValue(student);

                if (be1.isChecked() == false && be2.isChecked() == false) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, m3opts[0],
                            m3opts[1], m3opts[2], m3opts[3], m3opts[4], m3opts[5]);
                    mDatabase.child("ME students").child("3rd year").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true && be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, m3opts[0],
                            m3opts[1], m3opts[2], m3opts[3], m3opts[4], m3opts[5], m3eopts[0], m3eopts[1]);
                    mDatabase.child("ME courses").child("3rd year").child("ME350").child(mUserId).setValue(student);
                    mDatabase.child("ME courses").child("3rd year").child("ME351").child(mUserId).setValue(student);
                    mDatabase.child("ME students").child("3rd year").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, m3opts[0],
                            m3opts[1], m3opts[2], m3opts[3], m3opts[4], m3opts[5], m3eopts[0]);
                    mDatabase.child("ME courses").child("3rd year").child("ME350").child(mUserId).setValue(student);
                    mDatabase.child("ME students").child("3rd year").child(mUserId).setValue(user);
                } else if (be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, m3opts[0],
                            m3opts[1], m3opts[2], m3opts[3], m3opts[4], m3opts[5], m3eopts[1]);
                    mDatabase.child("ME courses").child("3rd year").child("ME351").child(mUserId).setValue(student);
                    mDatabase.child("ME students").child("3rd year").child(mUserId).setValue(user);
                }
            }
            //FOR FINAL YEAR
            if (yearchosen == "Fourth Year") {
                mDatabase.child("ME courses").child("4th year").child("ME400").child(mUserId).setValue(student);
                mDatabase.child("ME courses").child("4th year").child("ME401").child(mUserId).setValue(student);
                mDatabase.child("ME courses").child("4th year").child("ME402").child(mUserId).setValue(student);
                mDatabase.child("ME courses").child("4th year").child("ME403").child(mUserId).setValue(student);

                if (be1.isChecked() == false && be2.isChecked() == false) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, m4opts[0],
                            m4opts[1], m4opts[2], m4opts[3], m4opts[4], m4opts[5]);
                    mDatabase.child("ME students").child("4th year").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true && be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, m4opts[0],
                            m4opts[1], m4opts[2], m4opts[3], m4opts[4], m4opts[5], m4eopts[0], m4eopts[1]);
                    mDatabase.child("ME courses").child("4th year").child("ME450").child(mUserId).setValue(student);
                    mDatabase.child("ME courses").child("4th year").child("ME451").child(mUserId).setValue(student);
                    mDatabase.child("ME students").child("4th year").child(mUserId).setValue(user);
                } else if (be1.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, m4opts[0],
                            m4opts[1], m4opts[2], m4opts[3], m4opts[4], m4opts[5], m4eopts[0]);
                    mDatabase.child("ME courses").child("4th year").child("ME450").child(mUserId).setValue(student);
                    mDatabase.child("ME students").child("4th year").child(mUserId).setValue(user);
                } else if (be2.isChecked() == true) {
                    User user = new User(namestr, branchchosen, yearchosen, regchosen, rollchosen, m4opts[0],
                            m4opts[1], m4opts[2], m4opts[3], m4opts[4], m4opts[5], m4eopts[1]);
                    mDatabase.child("ME courses").child("4th year").child("ME451").child(mUserId).setValue(student);
                    mDatabase.child("ME students").child("4th year").child(mUserId).setValue(user);
                }
            }
        }

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
                    b1.setEnabled(true);
                    b2.setEnabled(true);
                    b3.setEnabled(true);
                    b4.setEnabled(true);
                    b5.setEnabled(true);
                    b6.setEnabled(true);
                    b1.setChecked(true);
                    b2.setChecked(true);
                    b3.setChecked(true);
                    b4.setChecked(true);
                    b5.setChecked(true);
                    b6.setChecked(true);
                    be1.setEnabled(true);
                    be2.setEnabled(true);
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
                    b1.setEnabled(true);
                    b2.setEnabled(true);
                    b3.setEnabled(true);
                    b4.setEnabled(true);
                    b5.setEnabled(true);
                    b1.setChecked(true);
                    b2.setChecked(true);
                    b3.setChecked(true);
                    b4.setChecked(true);
                    b5.setChecked(true);
                    be1.setEnabled(true);
                    be2.setEnabled(true);
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
                    b1.setEnabled(true);
                    b2.setEnabled(true);
                    b3.setEnabled(true);
                    b4.setEnabled(true);
                    b1.setChecked(true);
                    b2.setChecked(true);
                    b3.setChecked(true);
                    b4.setChecked(true);
                    be1.setEnabled(true);
                    be2.setEnabled(true);
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

            /*if(branchchosen=="Computer Science and Engineering"){
                reg.setText("CO");
            }
            if(branchchosen=="Electrical and Electronic Engineering"){
                reg.setText("EE");
            }
            if(branchchosen=="Mechanical Engineering"){
                reg.setText("ME");
            }*/
            }

        }
        if (v == regbutton) {
            if (lock == 1) {
                mDatabase.child("users").child(mUserId).removeEventListener(postListener);
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
        if(spinner.getId() == R.id.SpinnerBranch)
        {
            branchchosen=parent.getItemAtPosition(position).toString();
        }
        if(spinner1.getId() == R.id.SpinnerYear)
        {
            yearchosen=parent.getItemAtPosition(position).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}

