package com.google.pramodbs.collegehelper;

/**
 * Created by Pramod B.S on 25-02-2017.
 */

public class User {
    //must be public
    public String name,branch,year;

    public User(){

    }

    public User(String name,String branch,String year){
        this.branch=branch;
        this.year=year;
        this.name=name;
    }
}


