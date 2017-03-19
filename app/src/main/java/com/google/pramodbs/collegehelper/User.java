package com.google.pramodbs.collegehelper;
//LOC=35
/**
 * Created by Pramod B.S on 25-02-2017.
 */

public class User {
    //must be public
    public String Name,Branch,Year,regnumber,rollnumber,C1,C2,C3,C4,C5,C6,E1,E2;

    public User(){

    }

    public User(String name,String branch,String year,String regnumber,String rollnumber,String C1,String C2,String C3,String C4,String C5,String C6,
                String E1,String E2){
        this.Branch=branch;
        this.Year=year;
        this.Name=name;
        this.regnumber=regnumber;
        this.rollnumber=rollnumber;
        this.C1=C1;
        this.C2=C2;
        this.C3=C3;
        this.C4=C4;
        this.C5=C5;
        this.C6=C6;
        this.E1=E1;
        this.E2=E2;
    }

}


