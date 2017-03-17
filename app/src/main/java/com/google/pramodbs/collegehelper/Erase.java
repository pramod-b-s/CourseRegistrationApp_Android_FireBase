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

import java.util.Objects;

public class Erase extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    //private FirebaseUser user;
    //private FirebaseAnalytics mFirebaseAnalytics;

    private Button logoutbtn, save, ersbutton;
    private DatabaseReference mDatabase;
    private String mUserId;
    private Query queryref;

    private TextView bx1, bx2;

    private TextView title, cores, electives;
    private EditText name, roll, reg;
    private Spinner year, branch;
    private String yearchosen, branchchosen;

    private ProgressDialog progress;

    private String readname, readrollno, readregno, readbranch, readyear;
    private static String cr1nm, cr2nm, cr3nm, cr4nm, cr5nm, cr6nm, el1nm, el2nm;

    private Firebase mref;

    private CheckBox b1, b2, b3, b4, b5, b6, be1, be2;

    private String[] yearopts, branchopts, c2opts, c3opts, c4opts, c2eopts, c3eopts, c4eopts, e2opts, e3opts,
            e4opts, e2eopts, e3eopts, e4eopts, m2opts, m3opts, m4opts, m2eopts, m3eopts, m4eopts;
    //private String[] degopts;
    int lock = 0, lock1 = 0;

    private String s1, s2, s3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erase);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ersbutton=(Button)findViewById(R.id.ersbtn);
        ersbutton.setOnClickListener(this);

        this.yearopts = new String[] {
                "Second Year", "Third Year", "Fourth Year"
        };
        this.branchopts = new String[] {
                "Computer Science and Engineering", "Electrical and Electronic Engineering", "Mechanical Engineering"
        };

        mUserId = user.getUid();

        year=(Spinner)findViewById(R.id.SpinnerYear1);
        branch=(Spinner)findViewById(R.id.SpinnerBranch1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, yearopts);
        year.setAdapter(adapter);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, branchopts);
        branch.setAdapter(adapter1);

        year.setOnItemSelectedListener(this);
        branch.setOnItemSelectedListener(this);
    }

    private void saveuserinfo(){
        if(branchchosen==branchopts[0]){
            if(yearchosen==yearopts[0]){
                mDatabase.child("CO students").child("2nd year").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("2nd year").child("CO200").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("2nd year").child("CO201").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("2nd year").child("CO202").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("2nd year").child("CO203").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("2nd year").child("CO204").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("2nd year").child("CO205").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("2nd year").child("CO250").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("2nd year").child("CO251").child(mUserId).removeValue();
                mDatabase.child("users").child(mUserId).removeValue();
            }
            if(yearchosen==yearopts[1]){
                mDatabase.child("CO students").child("3rd year").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("3rd year").child("CO300").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("3rd year").child("CO301").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("3rd year").child("CO302").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("3rd year").child("CO303").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("3rd year").child("CO304").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("3rd year").child("CO350").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("3rd year").child("CO351").child(mUserId).removeValue();
            }
            if(yearchosen==yearopts[2]){
                mDatabase.child("users").child(mUserId).removeValue();
                mDatabase.child("CO students").child("4th year").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("4th year").child("CO400").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("4th year").child("CO401").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("4th year").child("CO402").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("4th year").child("CO403").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("4th year").child("CO450").child(mUserId).removeValue();
                mDatabase.child("CO courses").child("4th year").child("CO451").child(mUserId).removeValue();
            }
        }

        if(branchchosen==branchopts[1]){
            if(yearchosen==yearopts[0]){
                mDatabase.child("users").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("2nd year").child("EE200").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("2nd year").child("EE201").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("2nd year").child("EE202").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("2nd year").child("EE203").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("2nd year").child("EE204").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("2nd year").child("EE205").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("2nd year").child("EE250").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("2nd year").child("EE251").child(mUserId).removeValue();
                mDatabase.child("EE students").child("2nd year").child(mUserId).removeValue();
            }
            if(yearchosen==yearopts[1]){
                mDatabase.child("users").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("3rd year").child("EE300").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("3rd year").child("EE301").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("3rd year").child("EE302").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("3rd year").child("EE303").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("3rd year").child("EE304").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("3rd year").child("EE305").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("3rd year").child("EE350").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("3rd year").child("EE351").child(mUserId).removeValue();
                mDatabase.child("EE students").child("3rd year").child(mUserId).removeValue();
            }
            if(yearchosen==yearopts[2]){
                mDatabase.child("users").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("4th year").child("EE400").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("4th year").child("EE401").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("4th year").child("EE402").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("4th year").child("EE403").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("4th year").child("EE450").child(mUserId).removeValue();
                mDatabase.child("EE courses").child("4th year").child("EE451").child(mUserId).removeValue();
                mDatabase.child("EE students").child("4th year").child(mUserId).removeValue();
            }
        }

        if(branchchosen==branchopts[2]){
            if(yearchosen==yearopts[0]){
                mDatabase.child("users").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("2nd year").child("ME200").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("2nd year").child("ME201").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("2nd year").child("ME202").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("2nd year").child("ME203").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("2nd year").child("ME204").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("2nd year").child("ME205").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("2nd year").child("ME250").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("2nd year").child("ME251").child(mUserId).removeValue();
                mDatabase.child("ME students").child("2nd year").child(mUserId).removeValue();
            }
            if(yearchosen==yearopts[1]){
                mDatabase.child("users").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("3rd year").child("ME300").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("3rd year").child("ME301").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("3rd year").child("ME302").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("3rd year").child("ME303").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("3rd year").child("ME304").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("3rd year").child("ME350").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("3rd year").child("ME351").child(mUserId).removeValue();
                mDatabase.child("ME students").child("3rd year").child(mUserId).removeValue();
            }
            if(yearchosen==yearopts[2]){
                mDatabase.child("users").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("4th year").child("ME400").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("4th year").child("ME401").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("4th year").child("ME402").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("4th year").child("ME403").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("4th year").child("ME450").child(mUserId).removeValue();
                mDatabase.child("ME courses").child("4th year").child("ME451").child(mUserId).removeValue();
                mDatabase.child("ME students").child("4th year").child(mUserId).removeValue();

            }
        }

        Toast.makeText(this, "Existing details removed\nEnter new details !", Toast.LENGTH_SHORT).show();
        finish();
        Intent gotonext = new Intent(this, ProfileActivity.class);
        startActivity(gotonext);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        Spinner spinner1 = (Spinner) parent;
        if(spinner.getId() == R.id.SpinnerBranch1)
        {
            branchchosen=parent.getItemAtPosition(position).toString();
        }
        if(spinner1.getId() == R.id.SpinnerYear1)
        {
            yearchosen=parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v==ersbutton){
            saveuserinfo();
            Toast.makeText(this, "Erased !", Toast.LENGTH_SHORT).show();
            finish();
            Intent gotonext = new Intent(this, ProfileActivity.class);
            startActivity(gotonext);
        }
    }
}