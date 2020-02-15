package com.example.mobice;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class telnetClient extends AsyncTask<String,Void,String> {
    Socket s;
    InputStreamReader in;
    BufferedReader bufferedReader;
    public String str = "mm";
    public EditText txtV ;

    @Override
    protected String doInBackground(String... strings) {

//        new Thread(new Runnable() {
//            public void run() {
//                //open socket
//            }
//        }).start();
       return null;
}


        public void openSocket() {
            //txtV= telnetTab.txtTelnet;
            new Thread(new Runnable() {
                public void run() {
                    try {
                        s = new Socket("icesus.org", 4000);
                        //s.close();
                        Log.d("socket", "success");

            PrintWriter pWrite = new PrintWriter(s.getOutputStream());
            pWrite.println("supasecret\r");
            pWrite.flush();
            in = new InputStreamReader(s.getInputStream());
            bufferedReader = new BufferedReader(in);
            while(s.isConnected()) {
                str = bufferedReader.readLine();

                //telnetTab.telString = str;
                Log.d("socket string", str);
            }

                    } catch (IOException e) {
                        Log.d("socket", "crash");
                        e.printStackTrace();
                    }
                }
            }).start();

        }




    @Override
    protected void onPostExecute(String result){
        if (result != null) {
            Log.d("telnet result ", result);
        } else {
            Log.d("telnet err ", result);
        }
    }


}
