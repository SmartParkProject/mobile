package com.example.rommo_000.dungeondivers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class statPointAllocation extends AppCompatActivity {
    int classFromPrev;
    int raceFromPrev;
    int genderFromPrev;
    String nameFromPrev;
    charCreation newChar;
    TextView welcomeView;
    ImageView charView;
    ImageView flameViewLeft;
    ImageView flameViewRight;
    int strength;
    int intelligence;
    int defense;
    int resistance;
    int max;
    int currTotalStats;
    TextView strView;
    TextView intView;
    TextView defView;
    TextView resView;
    String filename = "characterSavedData";
    FileOutputStream outputStream;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/characterSaveData";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_point_allocation);
        Intent i = getIntent();
        classFromPrev = i.getExtras().getInt("class");
        raceFromPrev = i.getExtras().getInt("race");
        genderFromPrev = i.getExtras().getInt("gender");
        nameFromPrev = i.getExtras().getString("name");

        newChar = new charCreation(raceFromPrev, classFromPrev, genderFromPrev);
        welcomeView = (TextView) findViewById(R.id.welcomeToStats);
        welcomeView.setText("Welcome Mighty " + nameFromPrev + "\nAdjust Thy Stats");

       //animating Image
        charView = (ImageView) findViewById(R.id.charViewStat);
        String img = newChar.returnPicInfo();
        charView.setBackgroundResource(getResources().getIdentifier(img, "drawable", getPackageName()));
        AnimationDrawable frameAnimation = (AnimationDrawable) charView.getBackground();
        frameAnimation.start();
        //animating flame
        flameViewLeft = (ImageView) findViewById(R.id.sideFlameLeft);
        flameViewLeft.setBackgroundResource(R.drawable.campfire);
        AnimationDrawable frameAnimation1 = (AnimationDrawable) flameViewLeft.getBackground();
        frameAnimation1.start();

        flameViewRight = (ImageView) findViewById(R.id.sideFlameRight);
        flameViewRight.setBackgroundResource(R.drawable.campfire);
        AnimationDrawable frameAnimation2 = (AnimationDrawable) flameViewRight.getBackground();
        frameAnimation2.start();

        strength = 10;
        intelligence = 10;
        defense = 10;
        resistance = 10;
        max = 60;
        currTotalStats = strength + intelligence + defense + resistance;
        strView = (TextView) findViewById(R.id.strengthValue);
        intView = (TextView) findViewById(R.id.intValue);
        defView = (TextView) findViewById(R.id.defValue);
        resView = (TextView) findViewById(R.id.resValue);
        strView.setText("" + strength);
        intView.setText("" + intelligence);
        defView.setText("" + defense);
        resView.setText("" + resistance);

        new AlertDialog.Builder(this)
                .setTitle("Character Creation Screen")
                .setMessage("On this screen you can customize your stat points.  If you wish to dive right into the action, click 'continue' and Dungeon Divers will auto fill out your stats")
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();


    }

    public void incStr(View view)
    {
        if(currTotalStats == max)
        {
            strView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            return;
        }
        strength++;
        currTotalStats = strength + intelligence + defense + resistance;
        strView.setText("" + strength);
    }
    public void decStr(View view)
    {
        if(strength == 0)
        {
            strView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            return;
        }
        strength--;
        currTotalStats = strength + intelligence + defense + resistance;
        strView.setText("" + strength);
    }
    public void incInt(View view)
    {
        if(currTotalStats == max)
        {
            intView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            return;
        }
        intelligence++;
        currTotalStats = strength + intelligence + defense + resistance;
        intView.setText("" + intelligence);
    }
    public void decInt(View view)
    {
        if(intelligence == 0)
        {
            intView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            return;
        }
        intelligence--;
        currTotalStats = strength + intelligence + defense + resistance;
        intView.setText("" + intelligence);
    }
    public void incDefense(View view)
    {
        if(currTotalStats == max)
        {
            defView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            return;
        }
        defense++;
        currTotalStats = strength + intelligence + defense + resistance;
        defView.setText("" + defense);
    }
    public void decDefense(View view)
    {
        if(defense == 0)
        {
            defView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            return;
        }
        defense--;
        currTotalStats = strength + intelligence + defense + resistance;
        defView.setText("" + defense);
    }
    public void incResistance(View view)
    {
        if(currTotalStats == max)
        {
            resView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            return;
        }
        resistance++;
        currTotalStats = strength + intelligence + defense + resistance;
        resView.setText("" + resistance);
    }
    public void decResistance(View view)
    {
        if(resistance == 0)
        {
            resView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            return;
        }
        resistance--;
        currTotalStats = strength + intelligence + defense + resistance;
        resView.setText("" + resistance);
    }
    public void gotoConfirmation(View view)
    {
        if(currTotalStats == max)
        {
            String allData = "30\n30\n" + strView.getText() + "\n" + intView.getText() + "\n" + resView.getText() + "\n" + defView.getText()
                    + "\n0\n1\n" + nameFromPrev + "\n" + newChar.returnPicInfo();
            Player player = new Player();
            if(player.saveData(allData))
                Toast.makeText(getApplicationContext(),"Character Created", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(),"Create Failed", Toast.LENGTH_SHORT).show();

            ArrayList<String> a = new ArrayList<>();
            a.add("30");
            a.add("30");
            a.add(strView.getText().toString());
            a.add(intView.getText().toString());
            a.add(resView.getText().toString());
            a.add(defView.getText().toString());
            a.add("0");
            a.add("1");
            a.add(nameFromPrev);
            a.add(newChar.returnPicInfo());


            Intent intent = new Intent(this, navigationScreen.class);
            intent.putExtra("charInfo", a);
            startActivity(intent);
        }
        else
            Toast.makeText(this, "Please allocate remaining points", Toast.LENGTH_SHORT).show();


    }

}
