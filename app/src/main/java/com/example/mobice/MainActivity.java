package com.example.mobice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class MainActivity extends AppCompatActivity {
    private Button btnLogin,btn2;
    public String token2;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                 token2 = instanceIdResult.getToken();

                Log.d("MyFirebaseToken", token);
               // volleyPost(token);
            }});
        btnLogin = findViewById(R.id.btnLogin);
        btn2 =findViewById(R.id.btn2);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();


            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sendMessage(token2);
                String data = "{"+
                        "\"token\"" + "\"" + token2 + "\","+

                        "}";
                //Submit(data);
                //Submit(token2);


            }
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null){
            //btnLogin.setActivated(false);
            openLogin();
        }
        else {
            Toast.makeText(getApplicationContext(),"user uid: " +currentUser.getUid(),Toast.LENGTH_LONG).show();
        }
    }

    public void openLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }




//    private void Submit(String data)
//    {
//        final String savedata= data;
//        String URL="http://o202.nor.fi:3000/"+data;
//
//        requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject objres=new JSONObject(response);
//                    Toast.makeText(getApplicationContext(),objres.toString(),Toast.LENGTH_LONG).show();
//
//
//                } catch (JSONException e) {
//                    Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();
//
//                }
//                //Log.i("VOLLEY", response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                //Log.v("VOLLEY", error.toString());
//            }
//        }) {
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                try {
//                    return savedata == null ? null : savedata.getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
//                    return null;
//                }
//            }
//
//        };
//        requestQueue.add(stringRequest);
//    }


}












