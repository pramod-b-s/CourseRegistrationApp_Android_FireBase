package com.google.pramodbs.collegehelper;
//LOC=123
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.MediaStore;

import com.firebase.client.authentication.Constants;
import com.firebase.tubesock.Base64;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button regbtn;
    private EditText regemail;
    private EditText regpasswd;
    private EditText reregpwd;
    private TextView signin;
    private Character c;
    private Integer f=0,f1=0,imgflag=0;

    private static final int CAMERA_REQUEST = 1;
    private ImageView imageView;
    private Button okbutton,discardbutton;
    private Bitmap photo;

    private ProgressDialog progress;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private static String mUserId;

    private StorageReference mStorage;

    private String[] yearopts,branchopts;
    private Spinner year,branch;
    private EditText name,roll,reg;
    private String yearchosen,branchchosen;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            firebaseAuth=FirebaseAuth.getInstance();

            regbtn=(Button)findViewById(R.id.regbutton);
            regemail=(EditText)findViewById(R.id.regemail);
            regemail.setText("@nitk.edu.in");
            this.yearopts = new String[] {
                    "Second Year", "Third Year", "Fourth Year"
            };
            this.branchopts = new String[] {
                    "Computer Science and Engineering", "Electrical and Electronic Engineering", "Mechanical Engineering"
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

            name=(EditText) findViewById(R.id.namefield);
            roll=(EditText) findViewById(R.id.rollno);
            reg=(EditText) findViewById(R.id.regno);

            regpasswd=(EditText)findViewById(R.id.regpassword);
            signin=(TextView)findViewById(R.id.regtxtview);
            reregpwd=(EditText)findViewById(R.id.regpasswordre);

            progress=new ProgressDialog(this);

            firebaseAuth=FirebaseAuth.getInstance();

            regbtn.setOnClickListener(this);
            signin.setOnClickListener(this);

            //imageView = (ImageView)this.findViewById(R.id.img);
            /*okbutton = (Button) this.findViewById(R.id.okbtn);
            okbutton.setOnClickListener(this);
            discardbutton = (Button) this.findViewById(R.id.discardbtn);
            discardbutton.setOnClickListener(this);*/

            mStorage=FirebaseStorage.getInstance().getReference();

        }

        private void registeruser() {
            String namestr = name.getText().toString();
            String regchosen = reg.getText().toString();
            String rollchosen = roll.getText().toString();

            final Student student=new Student(namestr,rollchosen,regchosen,branchchosen,yearchosen);

            imgflag=1;

            String emailid = regemail.getText().toString();
            final String passwdentered = regpasswd.getText().toString();
            String repasswdentered = reregpwd.getText().toString();

            if (emailid.endsWith("@nitk.edu.in") == false) {
                Toast.makeText(this, "Enter NITK email ID to proceed !", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(emailid)) {
                Toast.makeText(this, "Enter email ID to proceed !", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(namestr)) {
                Toast.makeText(this, "Enter name to proceed !", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(regchosen)) {
                Toast.makeText(this, "Enter Reg. No. to proceed !", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(rollchosen)) {
                Toast.makeText(this, "Enter Roll No. to proceed !", Toast.LENGTH_SHORT).show();
                return;
            }
            if (namestr.length() <= 2) {
                Toast.makeText(this, "Name field too short !", Toast.LENGTH_SHORT).show();
                return;
            }
            if (regchosen.length() != 6) {
                Toast.makeText(this, "Registration number must be of 6 digits !", Toast.LENGTH_SHORT).show();
                return;
            }
            if (rollchosen.length() != 7) {
                Toast.makeText(this, "Invalid roll number !", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(passwdentered)) {
                Toast.makeText(this, "Enter password to proceed !", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(repasswdentered)) {
                Toast.makeText(this, "Enter password to proceed !", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!(passwdentered.equals(repasswdentered))) {
                Toast.makeText(this, "Re-Enter password correctly !", Toast.LENGTH_SHORT).show();
                return;
            }

            progress.setMessage("Breathe In.. Breathe Out");
            progress.show();

            for (char c : passwdentered.toCharArray()) {
                if (c > 32 && c < 48) {
                    f = 1;
                }
                if (c > 57 && c < 65) {
                    f = 1;
                }
                if (c > 122 && c < 126) {
                    f = 1;
                }
                if (c > 64 && c < 91) {
                    f1 = 1;
                }
                if (f == 1 && f1 == 1) {
                    break;
                }
            }
            if (f == 0 || f1 == 0) {
                Toast.makeText(RegisterActivity.this, "Password should contain atleast one special character and one capital letter", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
            else if(imgflag==0){
                Toast.makeText(RegisterActivity.this, "Image must be uploaded !", Toast.LENGTH_SHORT).show();
            } else {
                firebaseAuth.createUserWithEmailAndPassword(emailid, passwdentered)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progress.dismiss();
                                if (task.isSuccessful()) {
                                    mUserId=task.getResult().getUser().getUid();
                                    //Toast.makeText(RegisterActivity.this, mUserId, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(RegisterActivity.this, "User registered successfully !", Toast.LENGTH_SHORT).show();

                                    Toast.makeText(RegisterActivity.this,"Sign In !",Toast.LENGTH_SHORT).show();
                                    Intent gototlogin=new Intent(RegisterActivity.this,MainActivity.class);
                                    startActivity(gototlogin);
                                } else {

                                    if (passwdentered.length() < 6) {
                                        Toast.makeText(RegisterActivity.this, "Password should be of atleast 6 characters", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "User with this Email ID already exists !", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                //Toast.makeText(RegisterActivity.this, mUserId+"final", Toast.LENGTH_SHORT).show();
                FirebaseUser user=firebaseAuth.getCurrentUser();
                mDatabase = FirebaseDatabase.getInstance().getReference();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(namestr)
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    mDatabase.child("users").child(mUserId).setValue(student);

                                    if (branchchosen.equals("Computer Science and Engineering")) {
                                        //FOR SECOND YEAR
                                        if (yearchosen.equals("Second Year")) {
                                            mDatabase.child("CO students").child("2nd year").child(mUserId).setValue(student);
                                            mDatabase.child("users").child(mUserId).setValue(student);
                                        }
                                        //FOR THIRD YEAR
                                        if (yearchosen.equals("Third Year")) {
                                            mDatabase.child("CO students").child("3rd year").child(mUserId).setValue(student);
                                            mDatabase.child("users").child(mUserId).setValue(student);
                                        }
                                        //FOR FINAL YEAR
                                        if (yearchosen.equals("Fourth Year")) {
                                            mDatabase.child("CO students").child("4th year").child(mUserId).setValue(student);
                                            mDatabase.child("users").child(mUserId).setValue(student);
                                        }
                                    }

                                    //FOR EEE
                                    if (branchchosen.equals("Electrical and Electronic Engineering")) {
                                        //FOR SECOND YEAR
                                        if (yearchosen.equals("Second Year")) {
                                            mDatabase.child("EE students").child("2nd year").child(mUserId).setValue(student);
                                            mDatabase.child("users").child(mUserId).setValue(student);
                                        }
                                        //FOR THIRD YEAR
                                        if (yearchosen.equals("Third Year")) {
                                            mDatabase.child("EE students").child("3rd year").child(mUserId).setValue(student);
                                            mDatabase.child("users").child(mUserId).setValue(student);
                                        }
                                        //FOR FINAL YEAR
                                        if (yearchosen.equals("Fourth Year")) {
                                            mDatabase.child("EE students").child("4th year").child(mUserId).setValue(student);
                                            mDatabase.child("users").child(mUserId).setValue(student);
                                        }
                                    }

                                    //FOR MECH
                                    if (branchchosen.equals("Mechanical Engineering")) {
                                        //FOR SECOND YEAR
                                        if (yearchosen.equals("Second Year")) {
                                            mDatabase.child("ME students").child("2nd year").child(mUserId).setValue(student);
                                            mDatabase.child("users").child(mUserId).setValue(student);
                                        }
                                        //FOR THIRD YEAR
                                        if (yearchosen.equals("Third Year")) {
                                            mDatabase.child("ME students").child("3rd year").child(mUserId).setValue(student);
                                            mDatabase.child("users").child(mUserId).setValue(student);
                                        }
                                        //FOR FINAL YEAR
                                        if (yearchosen.equals("Fourth Year")) {
                                            mDatabase.child("ME students").child("4th year").child(mUserId).setValue(student);
                                            mDatabase.child("users").child(mUserId).setValue(student);
                                        }
                                    }
                                    //Toast.makeText(RegisterActivity.this, "updated !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                /*user.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Verification email sent !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });*/


                //Toast.makeText(RegisterActivity.this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
                /*if (branchchosen.equals("Computer Science and Engineering")) {
                    //FOR SECOND YEAR
                    if (yearchosen.equals("Second Year")) {
                        mDatabase.child("CO students").child("2nd year").child(mUserId).setValue(student);
                        mDatabase.child("users").child(mUserId).setValue(student);
                    }
                    //FOR THIRD YEAR
                    if (yearchosen == "Third Year") {
                        mDatabase.child("CO students").child("3rd year").child(mUserId).setValue(student);
                        mDatabase.child("users").child(mUserId).setValue(student);
                    }
                    //FOR FINAL YEAR
                    if (yearchosen == "Fourth Year") {
                        mDatabase.child("CO students").child("4th year").child(mUserId).setValue(student);
                        mDatabase.child("users").child(mUserId).setValue(student);
                    }
                }

                //FOR EEE
                if (branchchosen == "Electrical and Electronic Engineering") {
                    //FOR SECOND YEAR
                    if (yearchosen == "Second Year") {
                        mDatabase.child("EE students").child("2nd year").child(mUserId).setValue(student);
                        mDatabase.child("users").child(mUserId).setValue(student);
                    }
                    //FOR THIRD YEAR
                    if (yearchosen == "Third Year") {
                        mDatabase.child("EE students").child("3rd year").child(mUserId).setValue(student);
                        mDatabase.child("users").child(mUserId).setValue(student);
                    }
                    //FOR FINAL YEAR
                    if (yearchosen == "Fourth Year") {
                        mDatabase.child("EE students").child("4th year").child(mUserId).setValue(student);
                        mDatabase.child("users").child(mUserId).setValue(student);
                    }
                }

                //FOR MECH
                if (branchchosen == "Mechanical Engineering") {
                    //FOR SECOND YEAR
                    if (yearchosen == "Second Year") {
                        mDatabase.child("ME students").child("2nd year").child(mUserId).setValue(student);
                        mDatabase.child("users").child(mUserId).setValue(student);
                    }
                    //FOR THIRD YEAR
                    if (yearchosen == "Third Year") {
                        mDatabase.child("ME students").child("3rd year").child(mUserId).setValue(student);
                        mDatabase.child("users").child(mUserId).setValue(student);
                    }
                    //FOR FINAL YEAR
                    if (yearchosen == "Fourth Year") {
                        mDatabase.child("ME students").child("4th year").child(mUserId).setValue(student);
                        mDatabase.child("users").child(mUserId).setValue(student);
                    }
                }*/

            }
        }

    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);

            progress.setMessage("Breathe In.. Breathe Out");
            progress.show();

            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmap = imageView.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data1 = baos.toByteArray();

            String path="photos/"+UUID.randomUUID()+".png";
            StorageReference picsRef=mStorage;

            UploadTask uploadtask=picsRef.putBytes(data1);

            uploadtask.addOnSuccessListener(RegisterActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progress.dismiss();
                }
            });
            imgflag=1;
        }
    }*/

        @Override
        public void onClick(View v) {
            if(v==regbtn){
                registeruser();
            }
            if(v==signin){
                Toast.makeText(RegisterActivity.this,"Sign In !",Toast.LENGTH_SHORT).show();
                Intent gototlogin=new Intent(this,MainActivity.class);

                startActivity(gototlogin);
            }
            /*if (v == okbutton) {
                imgflag=0;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
            if(v==discardbutton){
                imgflag=0;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
            }*/
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
