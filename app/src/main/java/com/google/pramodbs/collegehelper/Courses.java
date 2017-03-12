package com.google.pramodbs.collegehelper;

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

public class Courses extends AppCompatActivity {

    private TextView c1,c2,c3,c4,c5,c6,e1,e2;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    private static int cr1,cr2,cr3,cr4,cr5,cr6,el1,el2;
    private static String cr1nm,cr2nm,cr3nm,cr4nm,cr5nm,cr6nm,el1nm,el2nm,branch,year;
    private static String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mUserId=user.getUid();

        c1=(TextView)findViewById(R.id.crs1);
        c2=(TextView)findViewById(R.id.crs2);
        c3=(TextView)findViewById(R.id.crs3);
        c4=(TextView)findViewById(R.id.crs4);
        c5=(TextView)findViewById(R.id.crs5);
        c6=(TextView)findViewById(R.id.crs6);
        e1=(TextView)findViewById(R.id.elec1);
        e2=(TextView)findViewById(R.id.elec2);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User post = dataSnapshot.getValue(User.class);

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
                    c1.setText(cr1nm);
                }
                if(cr2nm!=null) {
                    c2.setText(cr2nm);
                }
                if(cr3nm!=null){
                    c3.setText(cr3nm);
                }
                if(cr4nm!=null){
                    c4.setText(cr4nm);
                }
                if(cr5nm!=null){
                    c5.setText(cr5nm);
                }
                if(cr6nm!=null){
                    c6.setText(cr6nm);
                }

                if(el1nm!=null){
                    e1.setText(el1nm);
                }
                if(el2nm!=null){
                    e2.setText(el2nm);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        };
        mDatabase.child("users").child(mUserId).addValueEventListener(postListener);

    }
}
