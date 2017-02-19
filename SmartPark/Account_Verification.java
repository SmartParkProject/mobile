package com.example.rommo_000.smartpark;

/**
 * Created by rommo_000 on 11/17/2016.
 */
public class Account_Verification {
    private String name;
    private String password;

    private String mySpotNum;
    private String myTime;

    public String getName() {return name;}
    public String getPassword() {return password;}

    public String getMySpotNum() {return mySpotNum;}
    public String getMyTime() {return myTime;}
    public void setSpotTime(String s, String t)
    {
        mySpotNum = s;
        myTime = t;
    }
}
