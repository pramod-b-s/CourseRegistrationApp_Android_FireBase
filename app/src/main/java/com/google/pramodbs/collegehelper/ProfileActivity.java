package com.google.pramodbs.collegehelper;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

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

    private TextView title;
    private EditText name;
    private Spinner year,branch;
    private String yearchosen,branchchosen;

    private CheckBox b1,b2,b3,b4,b5,b6;

    private String[] yearopts,branchopts,c2opts,c3opts,c4opts;
    //private String[] degopts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //userid=mDatabase.push().getKey();
        save=(Button)findViewById(R.id.savebtn);
        logoutbtn=(Button)findViewById(R.id.logoutbtn);
        regbutton=(Button)findViewById(R.id.regbtn);

        b1=(CheckBox)findViewById(R.id.c1);
        b2=(CheckBox)findViewById(R.id.c2);
        b3=(CheckBox)findViewById(R.id.c3);
        b4=(CheckBox)findViewById(R.id.c4);
        b5=(CheckBox)findViewById(R.id.c5);
        b6=(CheckBox)findViewById(R.id.c6);
        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);
        b4.setEnabled(false);
        b5.setEnabled(false);
        b6.setEnabled(false);

        this.yearopts = new String[] {
                "Second Year", "Third Year", "Fourth Year"
        };
        this.branchopts = new String[] {
                "Computer Science and Engineering", "Electrical and Electronic Engineering", "Mechanical Engineering"
        };
        this.c2opts = new String[] {
                "CO200", "CO201", "CO202","CO203","CO204","CO205"
        };
        this.c3opts = new String[] {
                "CO300", "CO301", "CO302","CO303","CO304"
        };
        this.c4opts = new String[] {
                "CO400", "CO401", "CO402","CO403"
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
        name=(EditText) findViewById(R.id.namefield);
    }

    private void saveuserinfo(){
        String namestr = name.getText().toString();

        User user = new User(namestr, branchchosen,yearchosen);
        /*mDatabase.child("names").child(mUserId).push().child("Name").setValue(name);
        mDatabase.child("branches").child(mUserId).push().child("Branch").setValue(branchchosen);
        mDatabase.child("years").child(mUserId).push().child("Year").setValue(yearchosen);

        /*Map<String,User> mParent = new HashMap<String ,User>();
        mParent.put(namestr,new User(branchchosen,yearchosen));*/

        mDatabase.child("users").child(mUserId).push().setValue(user);
        if(b1.isChecked()&&yearchosen=="Second Year"){
            mDatabase.child("CO200").child(mUserId).push().setValue(namestr);
        }
        if(b2.isChecked()&&yearchosen=="Second Year"){
            mDatabase.child("CO201").child(mUserId).push().setValue(namestr);
        }
        if(b3.isChecked()&&yearchosen=="Second Year"){
            mDatabase.child("CO202").child(mUserId).push().setValue(namestr);
        }
        if(b4.isChecked()&&yearchosen=="Second Year"){
            mDatabase.child("CO203").child(mUserId).push().setValue(namestr);
        }
        if(b5.isChecked()&&yearchosen=="Second Year"){
            mDatabase.child("CO204").child(mUserId).push().setValue(namestr);
        }
        if(b6.isChecked()&&yearchosen=="Second Year"){
            mDatabase.child("CO205").child(mUserId).push().setValue(namestr);
        }

        if(b1.isChecked()&&yearchosen=="Third Year"){
            mDatabase.child("CO300").child(mUserId).push().setValue(namestr);
        }
        if(b2.isChecked()&&yearchosen=="Third Year"){
            mDatabase.child("CO301").child(mUserId).push().setValue(namestr);
        }
        if(b3.isChecked()&&yearchosen=="Third Year"){
            mDatabase.child("CO302").child(mUserId).push().setValue(namestr);
        }
        if(b4.isChecked()&&yearchosen=="Third Year"){
            mDatabase.child("CO303").child(mUserId).push().setValue(namestr);
        }
        if(b5.isChecked()&&yearchosen=="Third Year"){
            mDatabase.child("CO304").child(mUserId).push().setValue(namestr);
        }

        if(b1.isChecked()&&yearchosen=="Fourth Year"){
            mDatabase.child("CO400").child(mUserId).push().setValue(namestr);
        }
        if(b2.isChecked()&&yearchosen=="Fourth Year"){
            mDatabase.child("CO401").child(mUserId).push().setValue(namestr);
        }
        if(b3.isChecked()&&yearchosen=="Fourth Year"){
            mDatabase.child("CO402").child(mUserId).push().setValue(namestr);
        }
        if(b4.isChecked()&&yearchosen=="Fourth Year"){
            mDatabase.child("CO403").child(mUserId).push().setValue(namestr);
        }

        Toast.makeText(this, "Saved data !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v==logoutbtn){
            firebaseAuth.signOut();
            Toast.makeText(this,"Signed Out successfully !",Toast.LENGTH_SHORT).show();
            Intent gotomain=new Intent(this,MainActivity.class);
            finish();
            startActivity(gotomain);
        }
        if(v==save){
            if(yearchosen=="Second Year"){
                b1.setEnabled(true);
                b2.setEnabled(true);
                b3.setEnabled(true);
                b4.setEnabled(true);
                b5.setEnabled(true);
                b6.setEnabled(true);
                b1.setText(c2opts[0]);
                b2.setText(c2opts[1]);
                b3.setText(c2opts[2]);
                b4.setText(c2opts[3]);
                b5.setText(c2opts[4]);
                b6.setText(c2opts[5]);
            }
            if(yearchosen=="Third Year"){
                b1.setEnabled(true);
                b2.setEnabled(true);
                b3.setEnabled(true);
                b4.setEnabled(true);
                b5.setEnabled(true);
                b1.setText(c3opts[0]);
                b2.setText(c3opts[1]);
                b3.setText(c3opts[2]);
                b4.setText(c3opts[3]);
                b5.setText(c3opts[4]);
            }
            if(yearchosen=="Fourth Year"){
                b1.setEnabled(true);
                b2.setEnabled(true);
                b3.setEnabled(true);
                b4.setEnabled(true);
                b1.setText(c4opts[0]);
                b2.setText(c4opts[1]);
                b3.setText(c4opts[2]);
                b4.setText(c4opts[3]);
            }
        }
        if(v==regbutton){
            saveuserinfo();
            Toast.makeText(this,"Registration Done !",Toast.LENGTH_SHORT).show();
            Intent gotonext=new Intent(this,MainActivity.class);
            startActivity(gotonext);
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



