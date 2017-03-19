package com.google.pramodbs.collegehelper;
//LOC=123
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button regbtn;
    private EditText regemail;
    private EditText regpasswd;
    private EditText reregpwd;
    private TextView signin;
    private Character c;
    private Integer f=0,f1=0;

    private ProgressDialog progress;

    private FirebaseAuth firebaseauth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            regbtn=(Button)findViewById(R.id.regbutton);
            regemail=(EditText)findViewById(R.id.regemail);
            regemail.setText("@nitk.edu.in");
            //regname=(EditText)findViewById(R.id.nametext);
            //regbranch=(EditText)findViewById(R.id.branchtext);
            regpasswd=(EditText)findViewById(R.id.regpassword);
            signin=(TextView)findViewById(R.id.regtxtview);
            reregpwd=(EditText)findViewById(R.id.regpasswordre);

            progress=new ProgressDialog(this);

            firebaseauth=FirebaseAuth.getInstance();

            regbtn.setOnClickListener(this);
            signin.setOnClickListener(this);

        }


        private void registeruser() {
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
            } else {
                firebaseauth.createUserWithEmailAndPassword(emailid, passwdentered)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progress.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "User registered successfully !", Toast.LENGTH_SHORT).show();

                                    Toast.makeText(RegisterActivity.this,"Sign In !",Toast.LENGTH_SHORT).show();
                                    Intent gototlogin=new Intent(RegisterActivity.this,MainActivity.class);
                                    startActivity(gototlogin);
                                } else {
                                /*Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                                Matcher m = p.matcher(passwdentered);
                                boolean b = m.find();
                                if(b==false){
                                    Toast.makeText(RegisterActivity.this,"Password should contain special character",Toast.LENGTH_SHORT).show();
                                }*/
                                    if (passwdentered.length() < 6) {
                                        Toast.makeText(RegisterActivity.this, "Password should be of atleast 6 characters", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "User with this Email ID already exists !", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        }

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
        }
}
