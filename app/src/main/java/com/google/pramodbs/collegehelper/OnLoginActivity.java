package com.google.pramodbs.collegehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class OnLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button nreg,editdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_login);

        nreg=(Button)findViewById(R.id.newreg);
        editdetails=(Button)findViewById(R.id.edit);

        nreg.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v==nreg){
            Toast.makeText(OnLoginActivity.this,"Enter Your Details !",Toast.LENGTH_SHORT).show();
            Intent newint=new Intent(this,ProfileActivity.class);
            startActivity(newint);
        }
        if(v==editdetails){
            Toast.makeText(OnLoginActivity.this,"Edit Your Details !",Toast.LENGTH_SHORT).show();
            Intent newintedit=new Intent(this,EditDetails.class);
            startActivity(newintedit);
        }
    }
}
