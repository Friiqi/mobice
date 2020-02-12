package com.example.mobice;


import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class mainTab extends Fragment implements View.OnClickListener {
    private static final String REQUESTTAG = "Mobice" ;
    private RequestQueue MyRequestQueue;
    private JsonArrayRequest jarRequest;
    public String token2, uid;
    private RequestQueue requestQueue;
    public ArrayList<String> keywords = new ArrayList<>();
    public EditText teksti;
    public JSONArray jar = new JSONArray();

    public FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    public int keyWordCounter = 0;
    public static Context conte ;
    private String testi;
    public ActionBar actionBar;
    public mainTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_tab,
                container, false);
       conte = this.getContext();
        Button btnLogin = (Button) v.findViewById(R.id.btnLogin);
        Button btnAddKeywords = (Button) v.findViewById(R.id.btnAddKeywords);
        Button btnSendKeywords = (Button) v.findViewById(R.id.btnSendKeywords);
        teksti = (EditText) v.findViewById(R.id.txtInput);
        btnLogin.setOnClickListener(this);
        btnAddKeywords.setOnClickListener(this);
        btnSendKeywords.setOnClickListener(this);



        if (currentUser == null) {
            openLogin(v);
        }

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                token2 = instanceIdResult.getToken();

                Log.d("MyFirebaseToken", token);
                // volleyPost(token);
            }
        });


        return v;

    }

  public static Context getCont() {

        return conte;
  }



    public void openLogin(View v){
        Intent intent = new Intent(getActivity(),  LoginActivity.class);
        startActivity(intent);
    }

    public void addKeywords(View v) {
        String inputTxt = this.teksti.getText().toString();
        JSONObject obj = new JSONObject();
        try {
            uid = currentUser.getUid();
            obj.put(uid,inputTxt);
            Log.d(uid, inputTxt);
            teksti.getText().clear();
            jar.put(obj);

            Log.d("jsontesti: ", inputTxt  );


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    public void volleyPost(View v) throws JSONException {

        String url = "http://o202.nor.fi:8080/"; // <----enter your post url here

         MyRequestQueue = Volley.newRequestQueue(v.getContext());

        final String requestBody = jar.toString();

         jarRequest = new JsonArrayRequest(
                Request.Method.POST,
                url, jar,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d("jsontest resp", response.toString());
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        //Toast.makeText(getApplicationContext(),"error ",Toast.LENGTH_LONG).show();
                        Log.d("ERR: ", String.valueOf(error));
                        Log.d("jsontest resp err", error.toString());
                    }
                })
        {
            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    Log.d("jsontesti", "jotain hajos");
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            "UTF-8");
//HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET)


                    if (jsonString == null || jsonString.length() == 0)


                        return Response.success(null,

                                HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    Log.d("jsontest err1", e.toString());
                    return Response.error(new ParseError(e));
                }
                return super.parseNetworkResponse(response);
            }
        };

        //Log.d("body: ", MyStringRequest.getBody().toString());
         testi = new String(jarRequest.getBody());
       //Log.d("jsontesti lopussa", testi);
        //Log.d("jsontesti", )
        //Log.d("bodytesti2: ", testi);
        jarRequest.setTag(REQUESTTAG);
        MyRequestQueue.add(jarRequest);

        Toast.makeText(getActivity(), "Keywords sent!", Toast.LENGTH_LONG).show();
        jar = new JSONArray();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (MyRequestQueue != null){
                MyRequestQueue.cancelAll(REQUESTTAG);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                openLogin(v.getRootView());
                break;
            case R.id.btnAddKeywords:
                Log.d("jsontest add", "kek");
                addKeywords(v.getRootView());
                break;
            case R.id.btnSendKeywords:
                try {

                    volleyPost(v.getRootView());
                    Log.d("jsontest vol", testi);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                return;

        }
    }
}

