package com.example.mobice;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.RequestQueue;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public Button btnLogin, btn2;
    public String token2, uid;
    private RequestQueue requestQueue;
    public ArrayList<String> keywords = new ArrayList<>();
    public EditText teksti;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem infotab, maintab;
    public PageAdapter pagerAdapter;
    public static boolean background = false;


    public JSONArray jar = new JSONArray();

    public FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    public int keyWordCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        teksti = findViewById(R.id.txtInput);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        infotab = (TabItem) findViewById(R.id.infoTab);
        maintab = (TabItem) findViewById(R.id.mainTab);
        viewPager = findViewById(R.id.viewpager);

        pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 2) {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//
//        new Thread(new Runnable() {
//            public void run() {
//                while (true) {
//                    try {
//                        if (background) {
//                            Toast.makeText(mainTab.getCont(), "onnistuin!", Toast.LENGTH_LONG).show();
//                            Log.d("foreground", mainTab.getCont().toString());
//                        }
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//            }
//            //do something
//        }
//    }).start();
    }

    public static void toaster(Context mcont, String message) {
        Toast.makeText(mcont, message, Toast.LENGTH_SHORT).show();
    }

}















