package com.google.pramodbs.collegehelper;

/**
 * Created by Pramod B.S on 25-02-2017.
 */

public class User {
    //must be public
    public String Name,Branch,Year,CORE1,CORE2,CORE3,CORE4,CORE5,CORE6,ELECTIVE1,ELECTIVE2,regnumber,rollnumber;

    public User(){

    }

    public User(String name,String branch,String year,String regnumber,String rollnumber,String c1, String c2,
                String c3,String c4,String c5,String c6){
        this.Branch=branch;
        this.Year=year;
        this.Name=name;
        this.regnumber=regnumber;
        this.rollnumber=rollnumber;
        this.CORE1=c1;
        this.CORE2=c2;
        this.CORE3=c3;
        this.CORE4=c4;
        this.CORE5=c5;
        this.CORE6=c6;
    }

    public User(String name,String branch,String year,String regnumber,String rollnumber,String c1, String c2,
                String c3,String c4,String c5,String c6,String ce1){
        this.Branch=branch;
        this.Year=year;
        this.Name=name;
        this.regnumber=regnumber;
        this.rollnumber=rollnumber;
        this.CORE1=c1;
        this.CORE2=c2;
        this.CORE3=c3;
        this.CORE4=c4;
        this.CORE5=c5;
        this.CORE6=c6;
        this.ELECTIVE1=ce1;
    }

    public User(String name,String branch,String year,String regnumber,String rollnumber,String c1, String c2,
                String c3,String c4,String c5,String c6,String ce1,String ce2){
        this.Branch=branch;
        this.Year=year;
        this.Name=name;
        this.regnumber=regnumber;
        this.rollnumber=rollnumber;
        this.CORE1=c1;
        this.CORE2=c2;
        this.CORE3=c3;
        this.CORE4=c4;
        this.CORE5=c5;
        this.CORE6=c6;
        this.ELECTIVE1=ce1;
        this.ELECTIVE2=ce2;
    }
}


