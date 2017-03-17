package com.google.pramodbs.collegehelper;
//LOC=23
/**
 * Created by Pramod B.S on 11-03-2017.
 */

public class Student {
    public String name,rollno,regno,branch,year;
    //public int CORE1att=0,CORE2att=0,CORE3att=0,CORE4att=0,CORE5att=0,CORE6att=0,ELECTIVE1att=0,ELECTIVE2att=0;
    public String C1,C2,C3,C4,C5,C6,E1,E2;
    //public int attendance=0;

    public Student(){

    }

    public Student(String name,String rollno,String regno,String branch,String year){
        this.name=name;
        this.rollno=rollno;
        this.regno=regno;
        this.branch=branch;
        this.year=year;
    }

}
