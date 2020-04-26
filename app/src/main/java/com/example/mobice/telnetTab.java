package com.example.mobice;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;


public class telnetTab extends Fragment {
    private String tempString;
    public  EditText txtTelnet;
    //public telnetClient tClient;
    public static String telString;
    public telnetTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //tClient = new telnetClient();
        View v = inflater.inflate(R.layout.fragment_telnet_tab,
                container, false);
        txtTelnet = v.findViewById(R.id.txtTelnet);
        txtTelnet.setText("under construction");
        //mmmmmmm
       // tClient.openSocket();

        //txtTelnet.setText(telString, EditText.BufferType.EDITABLE);
    // ei pysty fragmentin sisällä avaamaan vaan pitäs olla oma classi joka näytetään vaan fragmentissa?
           //tClient.doInBackground();


        //telnetClient.openSocket() on tänhetkinen toimiva tapa yhistää mudiin

        //tClient.openSocket();
        // Inflate the layout for this fragment
        return v;
    }

//    public void mud(){
//     new Thread(new Runnable() {
//         public void run() {
//             telString = tClient.str;
//
//             txtTelnet.setText( telString, EditText.BufferType.EDITABLE);
//             if (!telString.equals(tempString)) {
//                 txtTelnet.append(telString+"\n");
//             }
//             tempString = telString;
//             telString = tClient.str;
//             txtTelnet.setText("testi: "+ telString, EditText.BufferType.EDITABLE);
//         }
//         });
//     }
//
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            if(getActivity()!=null) {
//                mHandler.postDelayed(runnable, 1000);
//            }else{
//                mHandler.removeCallbacks(runnable);
//                return;
//            }
//            telString = tClient.str;
//
//            //txtTelnet.setText( telString, EditText.BufferType.EDITABLE);
//            if (!telString.equals(tempString)) {
//                txtTelnet.append(telString+"\n");
//            }
//            tempString = telString;
//        }
//    };
//    Handler mHandler = new Handler();
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mHandler.postDelayed(runnable,1000);
//    }
}
