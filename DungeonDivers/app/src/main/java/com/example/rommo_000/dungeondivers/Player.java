package com.example.rommo_000.dungeondivers;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by rommo_000 on 3/10/2017.
 */

public class Player {
    private int hp;
    private int mana;
    private int strength;
    private int intelligence;
    private int resistance;
    private int defense;
    private int exp;
    private int expForNextLevel;
    private int level;
    private String name;
    private String classRaceGender;


    public Player()
    {
        hp = 0;
        mana = 0;
        strength = 0;
        intelligence = 0;
        resistance = 0;
        defense = 0;
        exp = 0;
        level = 0;
        name = "";
        classRaceGender = "";

    }

    public Player(ArrayList<String> stats)
    {
        hp = Integer.parseInt(stats.get(0));
        mana = Integer.parseInt(stats.get(1));
        strength = Integer.parseInt(stats.get(2));
        intelligence = Integer.parseInt(stats.get(3));
        resistance = Integer.parseInt(stats.get(4));
        defense = Integer.parseInt(stats.get(5));
        exp = Integer.parseInt(stats.get(6));
        level = Integer.parseInt(stats.get(7));
        name = stats.get(8);
        classRaceGender = stats.get(9);
    }

    public Player(int h_in, int m_in, int s_in, int i_in, int r_in, int d_in, int e_in, int l_in, String n_in, String crgIn)
    {
        hp = h_in;
        mana = m_in;
        strength = s_in;
        intelligence = i_in;
        resistance = r_in;
        defense = d_in;
        exp = e_in;
        level = l_in;
        name = n_in;
        classRaceGender = crgIn;

    }

    public String getAllCharInfo()
    {
        return hp + "/n" + mana + "/n" + strength + "/n" + intelligence + "/n" + resistance + "/n" + defense + "/n"
                + exp + "/n" + level + "/n" + name + "/n" + classRaceGender;
    }
    public String getRaceClassGender()
    {
        return classRaceGender;
    }
    public String getName()
    {
        return name;
    }
    public int getHp()
    {
        return hp;
    }
    public int getMana()
    {
        return mana;
    }
    public int getStrength()
    {
        return strength;
    }
    public int getIntelligence()
    {
        return intelligence;
    }
    public int getResistance()
    {
        return resistance;
    }
    public int getDefense()
    {
        return defense;
    }
    public int getExp()
    {
        return exp;
    }
    public int getLevel()
    {
        return level;
    }

    public String getClassRaceGender()
    {
        char tmpClass = classRaceGender.charAt(0);
        char tmpRace = classRaceGender.charAt(1);
        char tmpGender = classRaceGender.charAt(2);
        String classToReturn;
        String raceToReturn;
        String genderToReturn;
        switch (tmpClass)
        {
            case 'w': classToReturn = "Warrior"; break;
            case 'm': classToReturn = "Mage"; break;
            case 'r': classToReturn = "Rogue"; break;
            default: classToReturn = "classError"; break;
        }
        switch (tmpRace)
        {
            case 'h': raceToReturn = "Human"; break;
            case 'e': raceToReturn = "Elf"; break;
            case 'o': raceToReturn = "Orc"; break;
            default: raceToReturn = "raceError"; break;
        }
        switch (tmpGender)
        {
            case 'm': genderToReturn = "Male"; break;
            case 'f': genderToReturn = "Female"; break;
            default: genderToReturn = "genderError"; break;
        }
        return classToReturn + " " + raceToReturn + " " + genderToReturn;
    }

    public Boolean saveData(String input)
    {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state))
        {
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/savedData");
            if(!dir.exists())
            {
                dir.mkdir();
            }
            File file = new File(dir, "gamedata.txt");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(input.getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }
        else
        {
            return false;

        }
    }

    public ArrayList loadData()
    {
        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/savedData");
        File file = new File(dir, "gamedata.txt");

        ArrayList<String> loaded_data = new ArrayList<String>();
        String in;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
         //   StringBuffer stringBuffer = new StringBuffer();

            while((in=bufferedReader.readLine()) != null)
            {
                loaded_data.add(in);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //return "NULL";
            loaded_data.add("Error fnf");
        } catch (IOException e) {
            e.printStackTrace();
            loaded_data.add("Error ioe");
        }

        return loaded_data;
    }

    public void deleteSave()
    {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state))
        {
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/savedData");

            File file = new File(dir, "gamedata.txt");

        }

    }

}