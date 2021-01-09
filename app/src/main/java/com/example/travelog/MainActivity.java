package com.example.travelog;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelog.fragement.Fragment_Activity1;
import com.example.travelog.fragement.Fragment_Activity2;
import com.example.travelog.fragement.Fragment_Activity3;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView bm_title;
    private TextView bm_back;
    private RelativeLayout bottom_title_bar;
    int Tag;

    private RelativeLayout main_body;
    private TextView bottome_bar_text_1;
    private TextView bottome_bar_text_2;
    private TextView bottome_bar_text_3;
    private ImageView bottom_bar_image_1;
    private ImageView bottom_bar_image_2;
    private ImageView bottom_bar_image_3;

    private LinearLayout main_body_bar;
    private RelativeLayout bottom_bar_1_btn;
    private RelativeLayout bottom_bar_2_btn;
    private RelativeLayout bottom_bar_3_btn;

    private Fragment_Activity1 fragment1;
    private Fragment_Activity2 fragment2;
    private Fragment_Activity3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        fragment1=new Fragment_Activity1();
        fragment2=new Fragment_Activity2();
        fragment3=new Fragment_Activity3();

    }

    private void initView(){
        main_body=findViewById(R.id.main_body);
        bottome_bar_text_1=findViewById(R.id.bottom_bar_text_1);
        bottome_bar_text_2=findViewById(R.id.bottom_bar_text_2);
        bottome_bar_text_3=findViewById(R.id.bottom_bar_text_3);
        bottom_bar_image_1=findViewById(R.id.bottom_bar_image_1);
        bottom_bar_image_2=findViewById(R.id.bottom_bar_image_2);
        bottom_bar_image_3=findViewById(R.id.bottom_bar_image_3);

        main_body_bar=findViewById(R.id.main_body_bar);
        bottom_bar_1_btn=findViewById(R.id.bottom_bar_1_btn);
        bottom_bar_2_btn=findViewById(R.id.bottom_bar_2_btn);
        bottom_bar_3_btn=findViewById(R.id.bottom_bar_3_btn);

        bottom_bar_1_btn.setOnClickListener(this);
        bottom_bar_2_btn.setOnClickListener(this);
        bottom_bar_3_btn.setOnClickListener(this);

    }
//导航栏三个按钮的点击事件
    @Override
    public void onClick(View v) {
        if (Tag == 0){
            Tag=1;
            switch (v.getId()){
                case R.id.bottom_bar_1_btn:
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_container,fragment1).commitAllowingStateLoss();
                    break;
                case R.id.bottom_bar_2_btn:
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_container,fragment2).commitAllowingStateLoss();
                    break;
                case R.id.bottom_bar_3_btn:
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_container,fragment3).commitAllowingStateLoss();
                    break;
            }
        }else if(Tag==1){
            switch (v.getId()){
                case R.id.bottom_bar_1_btn:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment1).commitAllowingStateLoss();
                    break;
                case R.id.bottom_bar_2_btn:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment2).commitAllowingStateLoss();
                    break;
                case R.id.bottom_bar_3_btn:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment3).commitAllowingStateLoss();
                    break;
            }
        }
    }
}
