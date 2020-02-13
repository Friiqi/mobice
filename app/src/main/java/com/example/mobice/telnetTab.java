package com.example.mobice;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;


public class telnetTab extends Fragment {

    private EditText txtTelnet;
    public telnetClient tClient;

    public telnetTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tClient = new telnetClient();
        View v = inflater.inflate(R.layout.fragment_telnet_tab,
                container, false);
        txtTelnet = v.findViewById(R.id.txtTelnet);
    // ei pysty fragmentin sisällä avaamaan vaan pitäs olla oma classi joka näytetään vaan fragmentissa?
    //        tClient.doInBackground();
        // Inflate the layout for this fragment
        return v;
    }

}
