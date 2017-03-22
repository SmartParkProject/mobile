package com.example.rommo_000.dungeondivers;

/**
 * Created by rommo_000 on 3/9/2017.
 */



    //This was made before I planned on really expanding this app
    //Most of this is either redundant or already in the player class




public class charCreation {
    charCreation(int initRace, int initClass, int initGender)
    {
        raceType = initRace;
        classType = initClass;
        genderType = initGender;

        setInfo(initRace, initClass, initGender);
    }

    public void setInfo(int r, int c, int g)
    {
        switch (r)
        {
            case (0): currRace = "Human"; break;
            case (1): currRace = "Elf"; break;
            case (2): currRace = " Orc"; break;
            default: currRace = "NULL"; break;
        }
        switch (c)
        {
            case(0): currClass = "Warrior"; break;
            default: currClass = "NULL"; break;
        }
        if(g == 0)
        {
            currGender = "Male";
        }
        else
        {
            currGender = "Female";
        }
    }
    public void setRace(int r)
    {
        switch (r)
        {
            case (0): currRace = "Human"; break;
            case (1): currRace = "Elf"; break;
            case (2): currRace = "Orc"; break;
            default: currRace = "NULL"; break;
        }
    }
    public void setGender()
    {
        if(genderType == 0)
        {
            currGender = "Male";
        }
        else
        {
            currGender = "Female";
        }
    }
    public void setClass()
    {
        switch (classType)
        {
            case(0): currClass = "Warrior"; break;
            case(1): currClass = "Mage"; break;
            case(2): currClass = "Rogue"; break;
            default: currClass = "NULL"; break;
        }
    }

    public String getCharInfo()
    {
        return currClass + " " + currRace + " " + currGender;
    }

    public String returnPicInfo()
    {
        String classID;
        String raceID;
        String genderID;
        switch (classType)
        {
            case (0): classID = "w"; break;
            case (1): classID = "m"; break;
            case (2): classID = "r"; break;
            default : classID = " "; break;
        }
        switch (raceType)
        {
            case (0): raceID = "h"; break;
            case (1): raceID = "e"; break;
            case (2): raceID = "o"; break;
            default : raceID = " "; break;
        }
        switch (genderType)
        {
            case(0): genderID = "m"; break;
            case(1): genderID = "f"; break;
            default: genderID = " "; break;
        }
        return classID + raceID + genderID;
    }


    public int classType;
    public int raceType;
    public int genderType;
    public String currRace;
    public String currClass;
    public String currGender;
}
