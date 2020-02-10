package com.example.mobice;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class infoTab extends Fragment {
    public EditText infoText;
    public infoTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_info_tab,
                container, false);

        infoText = (EditText) v.findViewById(R.id.txtInfo2);
        return v;
    }



}
