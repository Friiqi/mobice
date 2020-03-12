package com.example.mobice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "regtoken";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        createSignInIntent();

    }

    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]

    }
    public void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void getTokens() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                       final String token = task.getResult().getToken();

                        // Log and toast
                       // String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, token);
                        Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();

                        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                        mUser.getIdToken(true)
                                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                                        if (task.isSuccessful()) {
                                            String idToken = task.getResult().getToken();
                                            //Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();

                                            try {
                                                volleyPost(token, idToken);
                                                openMain();

                                            } catch (AuthFailureError authFailureError) {
                                                authFailureError.printStackTrace();
                                                Toast.makeText(getApplicationContext(),"error sending userIdToken",Toast.LENGTH_LONG).show();
                                            }

                                            // ...
                                        } else {
                                            Toast.makeText(getApplicationContext(),"error sending userIdToken",Toast.LENGTH_LONG).show();
                                        }
                                    }

                                });

                    }
                });
    }

    public void volleyPost(String regtoken, final String idToken) throws AuthFailureError {

        String url = "http://o202.nor.fi:3000/" + regtoken; // <----enter your post url here

        final StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.d("appErr", error.toString());
            }

        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {

                return idToken.getBytes();

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/text");
                headers.put("charset", "utf-8");
                return headers;
            }
        };


        JSONObject obj = new JSONObject();
        try {
            obj.put("idToken", idToken);
            //if i want i could send register token in body as well, but then i would need to handle it in backend (they end up in same file)
            //obj.put("regToken", regtoken);
            Log.d("idToken", obj.toString());
            Log.d("regToken: ", regtoken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = obj.toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        //Toast.makeText(getApplicationContext(),"error ",Toast.LENGTH_LONG).show();
                        Log.d("ERR: ", String.valueOf(error));
                    }
                }) {
            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };
        Log.d("body: ", MyStringRequest.getBody().toString());
        String testi = new String(jsonObjectRequest.getBody());
        Log.d("bodytesti: ", testi);
        Log.d("bodytesti2: ",testi );
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        MyRequestQueue.add(jsonObjectRequest);

    }

//    public void idTokenToBackEnd() {
//        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
//        mUser.getIdToken(true)
//                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
//                    public void onComplete(@NonNull Task<GetTokenResult> task) {
//                        if (task.isSuccessful()) {
//                            String idToken = task.getResult().getToken();
//                           volleyPost(idToken);
//                            Log.d("idToken: ",idToken);
//                            // ...
//                        } else {
//                            Toast.makeText(getApplicationContext(),"error sending userIdToken",Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                });
//    }


    // [START auth_fui_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
               // idTokenToBackEnd();
                getTokens();


                // ...
            } else {
                if (data == null){
                    Toast.makeText(getApplicationContext(),"Sign in failed.",Toast.LENGTH_SHORT).show();
                    setContentView(R.layout.loginactivity);
                    createSignInIntent();
                }
                else{
                    Toast.makeText(getApplicationContext(), response.getError().toString(),Toast.LENGTH_SHORT).show();
                }
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
    // [END auth_fui_result]

    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_signout]
    }

    public void delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_delete]
    }

    public void themeAndLogo() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();

        // [START auth_fui_theme_logo]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.icesus_logo)      // Set logo drawable
                        //.setTheme(R.style.MySuperAppTheme)      // Set theme
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_theme_logo]
    }

    public void privacyAndTerms() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();
        // [START auth_fui_pp_tos]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTosAndPrivacyPolicyUrls(
                                "https://example.com/terms.html",
                                "https://example.com/privacy.html")
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_pp_tos]
    }
}
