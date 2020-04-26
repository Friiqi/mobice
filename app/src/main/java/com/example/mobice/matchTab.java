package com.example.mobice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class matchTab extends Fragment {
    public matchTab() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_match_tab,
                container, false);

        //infoText = (EditText) v.findViewById(R.id.txtInfo2);
        return v;
    }



}
