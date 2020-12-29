package com.example.travelog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class signup_page_Activity extends AppCompatActivity {


    private Button btn_register;//注册按钮
    //用户名，密码，再次输入的密码的控件
    private EditText et_user_name,et_psw,et_psw_again;
    //用户名，密码，再次输入的密码的控件的获取值
    private String userName,psw,pswAgain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page_);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();

    }

    private void init() {
        //从activity_register.xml 页面中获取对应的UI控件
        btn_register= (Button) findViewById(R.id.btn_register);
        et_user_name= (EditText) findViewById(R.id.et_user_name);
        et_psw= (EditText) findViewById(R.id.et_psw);
        et_psw_again= (EditText) findViewById(R.id.et_psw_again);
         //注册按钮
        btn_register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //获取输入在相应控件中的字符串
            getEditString();
            //判断输入框内容
            if(TextUtils.isEmpty(userName)){
                Toast.makeText(signup_page_Activity.this, "Please enter Username", Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(psw)){
                Toast.makeText(signup_page_Activity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(pswAgain)){
                Toast.makeText(signup_page_Activity.this, "Please enter the password again", Toast.LENGTH_SHORT).show();
            }else if(!psw.equals(pswAgain)){
                Toast.makeText(signup_page_Activity.this, "The password entered twice is different", Toast.LENGTH_SHORT).show();
                /**
                 *从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
                 */
            } else if(psw.length()<8){
                Toast.makeText(signup_page_Activity.this, "Password must be greater than 8 digits", Toast.LENGTH_SHORT).show();
            }
            else if(userName.length()<6){
                Toast.makeText(signup_page_Activity.this, "Username must be greater than 6 digits", Toast.LENGTH_SHORT).show();
            }
            else if(userName.matches("[0-9]+")){
                Toast.makeText(signup_page_Activity.this, "Username must contain letters", Toast.LENGTH_SHORT).show();
            }
            else if(isExistUserName(userName)){
                Toast.makeText(signup_page_Activity.this, "This Username already exists", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(signup_page_Activity.this, "Register successfully", Toast.LENGTH_SHORT).show();
                //把账号、密码和账号标识保存到sp里面
                /**
                 * 保存账号和密码到SharedPreferences中
                 */
                saveRegisterInfo(userName, psw);
                //注册成功后把账号传递到RegisterActivity.java中
                // 返回值到RegisterActivity显示
                Intent data = new Intent();
                data.putExtra("userName", userName);
                setResult(RESULT_OK, data);
                //RESULT_OK为Activity系统常量，状态码为-1，
                // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                signup_page_Activity.this.finish();
            }
        }
    });
}
    /**
     获取控件中的字符串
     */
    private void getEditString(){
        userName=et_user_name.getText().toString().trim();
        psw=et_psw.getText().toString().trim();
        pswAgain=et_psw_again.getText().toString().trim();
    }
    /**
     从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
     */
    private boolean isExistUserName(String userName){
        boolean has_userName=false;
        //mode_private SharedPreferences sp = getSharedPreferences( );
        // "loginInfo", MODE_PRIVATE
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取密码
        String spPsw=sp.getString(userName, "");//传入用户名获取密码
        //如果密码不为空则确实保存过这个用户名
        if(!TextUtils.isEmpty(spPsw)) {
            has_userName=true;
        }
        return has_userName;
    }
    /**
     保存账号和密码到SharedPreferences中SharedPreferences
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