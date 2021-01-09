package com.example.travelog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.travelog.ui.DiscoverFragment.DiscoverFragment;
import com.example.travelog.ui.NotificationsFragment.NotificationsFragment;
import com.example.travelog.ui.Profile.ProfileFragment;
import com.example.travelog.ui.Trips.TripsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottom_nav_menu =findViewById(R.id.bottom_nav_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DiscoverFragment()).commit();
        bottom_nav_menu.setOnNavigationItemSelectedListener(navListener);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.nav_discover:
                        selectedFragment = new DiscoverFragment();
                        break;
                    case R.id.nav_trips:
                        selectedFragment = new TripsFragment();
                        break;
                    case R.id.nav_notifications:
                        selectedFragment = new NotificationsFragment();
                        break;
                    case R.id.nav_profile:
                        selectedFragment = new ProfileFragment();
                        break;
                }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

                return true;
            }

    };

//    private void initView(){
//        main_body=findViewById(R.id.main_body);
//        bottom_bar_text_1=findViewById(R.id.bottom_bar_text_1);
//        bottom_bar_text_2=findViewById(R.id.bottom_bar_text_2);
//        bottom_bar_text_3=findViewById(R.id.bottom_bar_text_3);
//        bottom_bar_image_1=findViewById(R.id.bottom_bar_image_1);
//        bottom_bar_image_2=findViewById(R.id.bottom_bar_image_2);
//        bottom_bar_image_3=findViewById(R.id.bottom_bar_image_3);
//
//        main_body_bar=findViewById(R.id.main_body_bar);
//        bottom_bar_1_btn=findViewById(R.id.bottom_bar_1_btn);
//        bottom_bar_2_btn=findViewById(R.id.bottom_bar_2_btn);
//        bottom_bar_3_btn=findViewById(R.id.bottom_bar_3_btn);
//
//        bottom_bar_1_btn.setOnClickListener(this);
//        bottom_bar_2_btn.setOnClickListener(this);
//        bottom_bar_3_btn.setOnClickListener(this);
//
//    }
//导航栏三个按钮的点击事件
//    @Override
//    public void onClick(View v) {
//        if (Tag == 0){
//            Tag=1;
//            switch (v.getId()){
//                case R.id.bottom_bar_1_btn:
//                    getSupportFragmentManager().beginTransaction().add(R.id.fl_container,fragment1).commitAllowingStateLoss();
//                    break;
//                case R.id.bottom_bar_2_btn:
//                    getSupportFragmentManager().beginTransaction().add(R.id.fl_container,fragment2).commitAllowingStateLoss();
//                    break;
//                case R.id.bottom_bar_3_btn:
//                    getSupportFragmentManager().beginTransaction().add(R.id.fl_container,fragment3).commitAllowingStateLoss();
//                    break;
//            }
//        }else if(Tag==1){
//            switch (v.getId()){
//                case R.id.bottom_bar_1_btn:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment1).commitAllowingStateLoss();
//                    break;
//                case R.id.bottom_bar_2_btn:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment2).commitAllowingStateLoss();
//                    break;
//                case R.id.bottom_bar_3_btn:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment3).commitAllowingStateLoss();
//                    break;
//            }
//        }
//    }
}
