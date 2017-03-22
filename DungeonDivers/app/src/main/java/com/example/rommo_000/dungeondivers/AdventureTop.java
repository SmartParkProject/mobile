package com.example.rommo_000.dungeondivers;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdventureTop extends Fragment{

    TextView adventureTopNameView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.adventure_top, container, false);


        adventureTopNameView = (TextView) view.findViewById(R.id.adventureTopName);

        return view;

    }


}
