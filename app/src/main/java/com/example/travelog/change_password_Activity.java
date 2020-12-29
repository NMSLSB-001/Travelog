package com.example.travelog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class change_password_Activity extends AppCompatActivity {

    private Button mBtnback;
    private Button mBtnlog_out;
    private SharedPreferences pref;
    private Button change;
    private EditText et_psw,et_new_psw,et_new_psw1;//edit
    private String userName,psw,spPsw,newpsw,newpsw1;//get username and password
    private Button mBtn_delete_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_);
        userName=User.getName();


/**
 * 这里开始
 */
        mBtnback=findViewById(R.id.btn_back);
        mBtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回上一界面
                Intent intent=new Intent(change_password_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        mBtnlog_out=findViewById(R.id.btn_logout);
        mBtnlog_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("isLogin", false);
                //User name when saving in login status
                editor.putString("loginUserName", User.getName());
                //Submit changes
                editor.commit();
                change_password_Activity.this.finish();
                startActivity(new Intent(change_password_Activity.this, RegisterActivity.class));
            }
        });
        mBtn_delete_account=findViewById(R.id.btn_delete);
        mBtn_delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
                sp.edit().clear().apply();
                Toast.makeText(change_password_Activity.this, "Account was deleted successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(change_password_Activity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        /**
         * 这里结束，都不是最核心的功能
         */
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        change();

    }

    private void change()
    {
        //拿用户输入的数据
        et_psw=findViewById(R.id.et_current_password);
        change=findViewById(R.id.btn_change);
        et_new_psw=findViewById(R.id.et_new_password);
        et_new_psw1=findViewById(R.id.et_psw_again);
        //点击change后的一系列动作
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psw=et_psw.getText().toString().trim();//获取当前sharedperference里的密码
                newpsw=et_new_psw.getText().toString().trim();
                newpsw1=et_new_psw1.getText().toString().trim();
                spPsw=readPsw(userName);
                if(TextUtils.isEmpty(psw)){
                    Toast.makeText(change_password_Activity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(newpsw)){
                    Toast.makeText(change_password_Activity.this, "Please enter New Password", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(newpsw1)){
                    Toast.makeText(change_password_Activity.this, "Please Confirm New Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newpsw.equals(newpsw1)){
                    if(psw.equals(spPsw)){
                        saveRegisterInfo(userName,newpsw);
                        Toast.makeText(change_password_Activity.this, "Successful Change ", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(change_password_Activity.this, RegisterActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(change_password_Activity.this, "Incorrect Password ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(change_password_Activity.this, "Please Confirm Your New Password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * 读取当前密码
     */
    private String readPsw(String userName){
        //"loginInfo",mode_private; MODE_PRIVATE表示可以继续写入
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //sp.getString() userName, "";
        return sp.getString(userName , "");
    }

    /**
     *放入新的密码
     */
    private void saveRegisterInfo(String userName,String psw){
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor=sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码）;
        editor.putString(userName, psw);
        //提交修改 editor.commit();
        editor.commit();
    }

}