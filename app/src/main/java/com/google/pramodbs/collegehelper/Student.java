package com.google.pramodbs.collegehelper;
//LOC=23
/**
 * Created by Pramod B.S on 11-03-2017.
 */

public class Student {
    public String name,rollno,regno;
    public int attendance=0;

    public Student(){

    }

    public Student(String name,String rollno,String regno){
        this.name=name;
        this.rollno=rollno;
        this.regno=regno;
        this.attendance=0;
    }

}
