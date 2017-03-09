package com.google.pramodbs.collegehelper;

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
import com.google.firebase.auth.FirebaseUser;

import java.util.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button loginbtn;
    private EditText email;
    private EditText passwd;
    private TextView regsignin;

    private ProgressDialog progress;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginbtn=(Button)findViewById(R.id.loginbutton);
        email=(EditText)findViewById(R.id.email);
        passwd=(EditText)findViewById(R.id.password);
        regsignin=(TextView)findViewById(R.id.txtview);

        progress=new ProgressDialog(this);

        firebaseAuth=FirebaseAuth.getInstance();

        loginbtn.setOnClickListener(this);
        regsignin.setOnClickListener(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Toast.makeText(getApplicationContext(),"onAuthStateChanged:signed_in:" + user.getUid(),Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    Toast.makeText(getApplicationContext(),"onAuthStateChanged:signed_out",Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };

    }


    private void loginuser(){
        String emailid=email.getText().toString();
        String passwdentered=passwd.getText().toString();

        if(TextUtils.isEmpty(emailid)){
            Toast.makeText(this,"Enter email ID to proceed !",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(passwdentered)){
           Toast.makeText(this,"Enter password to proceed !",Toast.LENGTH_SHORT).show();
            return;
        }

        progress.setMessage("Breathe In.. Breathe Out");
        progress.show();

        firebaseAuth.signInWithEmailAndPassword(emailid,passwdentered)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progress.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"LogIn Successful !",Toast.LENGTH_LONG).show();
                            Intent gotoonlogin=new Intent(getApplicationContext(),OnLoginActivity.class);

                            startActivity(gotoonlogin);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"LogIn Unsuccessful !",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if(v==loginbtn){
            loginuser();
        }
        if(v==regsignin){
            Toast.makeText(MainActivity.this,"Register here !",Toast.LENGTH_SHORT).show();
            Intent gototreg=new Intent(this,RegisterActivity.class);

            startActivity(gototreg);
        }
    }
}
