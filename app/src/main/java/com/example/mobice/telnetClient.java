package com.example.mobice;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class telnetClient extends AsyncTask<String,Void,String> {


    @Override
    protected String doInBackground(String... strings) {
        Socket s;
        InputStreamReader in;
        BufferedReader bufferedReader;
        String str;


        try {
            s = new Socket("95.216.243.162", 4000);
            s.close();
            return "connection estabilished";
//            PrintWriter pWrite = new PrintWriter(s.getOutputStream());
//            pWrite.println("ahab+ testi");
//            pWrite.flush();
//            in = new InputStreamReader(s.getInputStream());
//            bufferedReader = new BufferedReader(in);
//            str = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return null;
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
