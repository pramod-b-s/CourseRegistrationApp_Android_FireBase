package com.google.pramodbs.collegehelper;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class Attendance extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText edit;
    private TextView ans;
    private Spinner year,branch,num;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private Button clk;

    private String a1,a2,a3,a4,a5,a6,a7,a8;

    //private static int cr1,cr2,cr3,cr4,cr5,cr6,el1,el2;
    //private static String cr1nm,cr2nm,cr3nm,cr4nm,cr5nm,cr6nm,el1nm,el2nm,branch,year;
    private static String mUserId;
    private String yearchosen,branchchosen,crschosen,subchosen;
    private String[] yearopts,branchopts,crsopts;
    private static int f1=0,f2=0,f3=0,f4=0,f5=0,f6=0,f7=0,f8=0,f9=0;

    private String yr,brnch,crs;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        edit=(EditText)findViewById(R.id.rdcrs);
        clk=(Button)findViewById(R.id.btn);
        clk.setOnClickListener(this);

        ans=(TextView)findViewById(R.id.crs2);

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        this.yearopts = new String[] {
                "2nd Year", "3rd Year", "4th Year"
        };
        this.branchopts = new String[] {
                "CO courses", "EE courses", "ME courses"
        };
        this.crsopts = new String[] {
                "CORE1","CORE2","CORE3","CORE4","CORE5","CORE6","ELECTIVE1","ELECTIVE2"
        };

        year=(Spinner)findViewById(R.id.SpinnerYear);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, yearopts);
        year.setAdapter(adapter);

        branch=(Spinner)findViewById(R.id.SpinnerBranch);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, branchopts);
        branch.setAdapter(adapter1);

        num=(Spinner)findViewById(R.id.SpinnerNum);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, crsopts);
        num.setAdapter(adapter2);

        year.setOnItemSelectedListener(this);
        branch.setOnItemSelectedListener(this);
        num.setOnItemSelectedListener(this);

        mUserId=user.getUid();

    }

    private void getattendance(){

        subchosen=edit.getText().toString();

        ValueEventListener postListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User post = dataSnapshot.getValue(User.class);
                /*a1=post.CORE1;
                a2=post.CORE2;
                a3=post.CORE3;
                a4=post.CORE4;
                a5=post.CORE5;
                a6=post.CORE6;
                a7=post.ELECTIVE1;
                a8=post.ELECTIVE2;*/

                //if(crschosen==a1){
                    ans.setText(a1);
                /*}
                if(crschosen==a2){
                    ans.setText(crschosen);
                }
                if(crschosen==a3){
                    ans.setText(crschosen);
                }
                if(crschosen==a4){
                    ans.setText(crschosen);
                }
                if(crschosen==a5){
                    ans.setText(crschosen);
                }
                if(crschosen==a6){
                    ans.setText(crschosen);
                }
                if(crschosen==a7){
                    ans.setText(crschosen);
                }
                if(crschosen==a8){
                    ans.setText(crschosen);
                }*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.child(branchchosen).child(yearchosen).child(crschosen).child(mUserId).child(crschosen).addValueEventListener(postListener);
        //Toast.makeText(Attendance.this,a1+a2+a3+a4+a5+a6+a7+a8,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        Spinner spinner1 = (Spinner) parent;
        Spinner spinner2 = (Spinner) parent;
        if(spinner.getId() == R.id.SpinnerBranch)
        {
            branchchosen=parent.getItemAtPosition(position).toString();
        }
        if(spinner1.getId() == R.id.SpinnerYear)
        {
            yearchosen=parent.getItemAtPosition(position).toString();
        }
        if(spinner2.getId() == R.id.SpinnerNum)
        {
            crschosen=parent.getItemAtPosition(position).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View v) {
        if(v==clk){
            subchosen=edit.getText().toString();
            getattendance();
        }
    }
}

