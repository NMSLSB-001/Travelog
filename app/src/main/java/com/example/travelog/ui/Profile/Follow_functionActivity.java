package com.example.travelog.ui.Profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelog.MainActivity;
import com.example.travelog.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.TimeZone;

public class Follow_functionActivity extends AppCompatActivity {

    private Button back;
    private EditText editText;
    private Button ok_button;
    private TextView follow_info;
    private Button follow_button;
    private Button follow_list_button;
    String username,current_username;
    String year,month,day,hour,minute,time;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_function);

        editText=findViewById(R.id.et_username_follow);
        follow_button=findViewById(R.id.btn_follow_follow);
        ok_button=findViewById(R.id.btn_ok);
        follow_info=findViewById(R.id.tv_follow);
        current_username= User1.getName();
        //返回页面
        back=findViewById(R.id.btn_back_follow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Follow_functionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //跳到产看关注者的页面
        follow_list_button=findViewById(R.id.btn_view_list);
        follow_list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Follow_functionActivity.this, Follow_listActivity.class);
                startActivity(intent);
            }
        });

        search();//开始执行获取和查找
    }
    private void search(){

        //点击确认，查找数据库有无目标用户
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=editText.getText().toString().trim();
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(Follow_functionActivity.this, "Please enter Target Username", Toast.LENGTH_SHORT).show();
                }else if(username.equals(current_username)){
                    Toast.makeText(Follow_functionActivity.this, "Do not enter your account number", Toast.LENGTH_SHORT).show();
                }else{
                    isUser(username);
                }
            }
        });
    }
    //查找数据库有无目标用户
    private boolean isUser(final String username){
        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = databaseUser.orderByChild("user").equalTo(username);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Toast.makeText(Follow_functionActivity.this, "发现用户", Toast.LENGTH_SHORT).show();
                    //显示当前用户
                    follow_info.setText(username);
                    follow_button.setText("Follow");
                    follow_button.setBackground(getResources().getDrawable(R.drawable.btn2dong));
                    //点击follow开始关注用户
                    follow_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isfollow(username);
                        }
                    });
                }else{
                    Toast.makeText(Follow_functionActivity.this, "没有这个用户", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return true;
    }
    //判断是否follow这个用户
    private boolean isfollow(final String star){
        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("follow");
        Query checkUser = databaseUser.orderByChild("star").equalTo(star);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //发现已经关注了改用户
                if(snapshot.exists()){
                    Toast.makeText(Follow_functionActivity.this, "You already followed this user", Toast.LENGTH_SHORT).show();
                    follow_button.setText("Unfollow");
                    follow_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            unfollow();
                            Toast.makeText(Follow_functionActivity.this, "Successfully unfollow", Toast.LENGTH_SHORT).show();
                            follow_button.setText("");
                            follow_button.setBackground(getResources().getDrawable(R.drawable.btn_signup_dong));
                            follow_info.setText("");
                        }
                    });
                }else{
                    //发现没有关注他
                    follow(star);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    return true;
    }
    //关注用户
    private void follow(String star){
        //获取时间
        Calendar data= Calendar.getInstance();
        data.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        year = String.valueOf(data.get(Calendar.YEAR));
        month = String.valueOf(data.get(Calendar.MONTH))+1;
        day = String.valueOf(data.get(Calendar.DATE));
        if (data.get(Calendar.AM_PM) == 0)
            hour = String.valueOf(data.get(Calendar.HOUR));
        else
            hour = String.valueOf(data.get(Calendar.HOUR)+12);
        minute = String.valueOf(data.get(Calendar.MINUTE));
        time = year + "-" + month + "-" + day + "-" + hour + "-" + minute;
        //获取时间，结束

        DatabaseReference databaseUser;
        databaseUser= FirebaseDatabase.getInstance().getReference("follow");
        String id= databaseUser.push().getKey();
        follow_detail user=new follow_detail(star,current_username,time);
        databaseUser.child(star).setValue(user);
        follow_button.setText("Unfollow");
        Toast.makeText(Follow_functionActivity.this, "Focus on success", Toast.LENGTH_SHORT).show();

    }

    //取消关注用户
    private void unfollow(){
        DatabaseReference delete_account=FirebaseDatabase.getInstance().getReference("follow").child(username);
        delete_account.removeValue();
    }
}