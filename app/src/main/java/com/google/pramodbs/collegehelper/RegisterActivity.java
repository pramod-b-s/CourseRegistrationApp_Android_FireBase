package com.google.pramodbs.collegehelper;
//LOC=141
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button regbtn;
    private EditText regemail;
    private EditText regpasswd;
    private EditText reregpwd;
    private TextView signin;
    //private EditText regname;
    //private EditText regbranch;

    private ProgressDialog progress;

    private FirebaseAuth firebaseauth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            /*if(firebaseauth.getCurrentUser()!=null){
                //profile activity
                /*Intent gotoprofile=new Intent(getApplicationContext(),ProfileActivity.class);
                finish();
                startActivity(gotoprofile);
            }*/

            regbtn=(Button)findViewById(R.id.regbutton);
            regemail=(EditText)findViewById(R.id.regemail);
            regemail.setText("@nitk.ac.in");
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


        private void registeruser(){
            String emailid=regemail.getText().toString();
            final String passwdentered=regpasswd.getText().toString();
            String repasswdentered=reregpwd.getText().toString();
            //String entname=regname.getText().toString();
            //String entbranch=regbranch.getText().toString();

            if(emailid.endsWith("@nitk.ac.in")==false){
                Toast.makeText(this,"Enter NITK email ID to proceed !",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(emailid)){
                Toast.makeText(this,"Enter email ID to proceed !",Toast.LENGTH_SHORT).show();
                return;
            }
            /*if(TextUtils.isEmpty(entbranch)){
                Toast.makeText(this,"Enter Branch to proceed !",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(entname)){
                Toast.makeText(this,"Enter Name to proceed !",Toast.LENGTH_SHORT).show();
                return;
            }*/
            if(TextUtils.isEmpty(passwdentered)){
                Toast.makeText(this,"Enter password to proceed !",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(repasswdentered)){
                Toast.makeText(this,"Enter password to proceed !",Toast.LENGTH_SHORT).show();
                return;
            }
            if(!(passwdentered.equals(repasswdentered))){
                Toast.makeText(this,"Re-Enter password correctly !",Toast.LENGTH_SHORT).show();
                return;
            }

            progress.setMessage("Breathe In.. Breathe Out");
            progress.show();

            firebaseauth.createUserWithEmailAndPassword(emailid,passwdentered)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progress.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this,"User registered successfully !",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(passwdentered.length()<6){
                                    Toast.makeText(RegisterActivity.this,"Password should be of atleast 6 characters",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(RegisterActivity.this, "User with this Email ID already exists !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
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
