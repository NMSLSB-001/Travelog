package com.example.travelog.ui.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelog.MainActivity;
import com.example.travelog.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class fill_info_Activity extends AppCompatActivity {

    private Button mBtnback;//返回键
    private PopupWindow mPop;//弹出窗口
    private Button mBtnPop;
    //个人信息输入
    private Button btn_save;//保存信息的按钮
    //姓名，地址，邮件，电话，性别的控件
    private EditText et_name,et_address,et_email,et_phone;
    //姓名，地址，邮件，电话，性别的控件的获取值
    private String name,address,email,phone,gender,Username;//Username是用户的唯一ID,name是姓名

    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_info_);

        Username= User1.getName();
        databaseUser= FirebaseDatabase.getInstance().getReference("personal_detail");

        mBtnback=findViewById(R.id.btn_fill_back);
        mBtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回上一界面
                Intent intent=new Intent(fill_info_Activity.this, change_password_Activity.class);
                startActivity(intent);
            }
        });
        //弹出窗口,选择性别，开始
        mBtnPop= findViewById(R.id.btn_pop);
        mBtnPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=getLayoutInflater().inflate(R.layout.layout_pop_fill_info,null);
                mPop=new PopupWindow(view,mBtnPop.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
                mPop.showAsDropDown(mBtnPop);
                mPop.setOutsideTouchable(true);
                mPop.setFocusable(true);
                mPop.update();
                //配置点击事件，因为pop会出现3个选择（男女和不透露），所以我们设置3个事件
                TextView textView = view.findViewById(R.id.tv_male);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(fill_info_Activity.this, "Male", Toast.LENGTH_SHORT).show();
                        gender="Male";
                        mPop.dismiss();
                        //这里可以进行设置，比如把性别写进txt文件
                    }
                });
                TextView textView1 = view.findViewById(R.id.tv_female);
                textView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(fill_info_Activity.this, "Female", Toast.LENGTH_SHORT).show();
                        gender="Female";
                        mPop.dismiss();
                        //这里可以进行设置，比如把性别写进txt文件
                    }
                });
                TextView textView2 = view.findViewById(R.id.tv_hidden);
                textView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(fill_info_Activity.this, "Hidden gender", Toast.LENGTH_SHORT).show();
                        gender="Hidden";
                        mPop.dismiss();
                        //这里可以进行设置，比如把性别写进txt文件
                    }
                });
            }
        });
        //弹出窗口,选择性别，结束
        //获取用户的输入的个人信息并且保存
        init();

        //initialize places API
        Places.initialize(getApplicationContext(), "AIzaSyDG7fly7vSrxUUFggJA9ZBzMNeuoQeoyiA");

        //Set edittext non focusable
        et_address.setFocusable(false);
        et_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize place field list
                List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);

                //create intent
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(fill_info_Activity.this);

                //start activity
                startActivityForResult(intent, 100);
            }
        });


    }
    private void init()
    {
        btn_save=findViewById(R.id.btn_save_info);
        et_name=findViewById(R.id.et_info_name);
        et_address=findViewById(R.id.et_info_address);
        et_email=findViewById(R.id.et_email_address);
        et_phone=findViewById(R.id.et_phone_number);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入在相应控件中的字符串
                getEditString();
                //判断输入框内容是否为空
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(fill_info_Activity.this, "Please enter Your Real Name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(address)){
                    Toast.makeText(fill_info_Activity.this, "Please enter Address", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(email)){
                    Toast.makeText(fill_info_Activity.this, "Please enter the E-Mail ", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(phone)){
                    Toast.makeText(fill_info_Activity.this, "Please enter the Phone Number ", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(gender)){
                    Toast.makeText(fill_info_Activity.this, "Please select the Gender ", Toast.LENGTH_SHORT).show();
                }else if(!email.contains("@")){
                    Toast.makeText(fill_info_Activity.this, "Please enter Correct Email Address ", Toast.LENGTH_SHORT).show();
                }else if(!email.contains(".com")){
                    Toast.makeText(fill_info_Activity.this, "Please enter Correct Email Address ", Toast.LENGTH_SHORT).show();
                }else{
                    /**
                     * 保存个人信息到SharedPreferences中
                     */
                    /**saveRegisterInfo(name, address, email, phone, gender);
                    // 返回值到profileActivity显示，下面有问题，要修改，会覆盖原密码
                    Intent data = new Intent();
                    data.putExtra("name", name);
                    setResult(RESULT_OK, data);
                    Toast.makeText(fill_info_Activity.this, "Information added successfully ", Toast.LENGTH_SHORT).show();
                    fill_info_Activity.this.finish();
                     */
                    add(Username,name,address,email,phone,gender);
                    //暂存个人数据用于显示给用户看
                    setdata(name,address,email,phone,gender);
                    Intent intent=new Intent(fill_info_Activity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    /**
     暂存用户信息用于显示
     */
    private void setdata(String name, String address, String email, String phone, String gender) {
        User1.setaddress(address);
        User1.setemail(email);
        User1.setgender(gender);
        User1.setReal_Name(name);
        User1.setphone(phone);
    }

    /**
     用户信息传入数据库
     */
    private void add(String username,String name,String address,String email,String phone,String gender){
        String id= databaseUser.push().getKey();
        personal_detail user=new personal_detail(id,name,address,email,phone,gender);
        databaseUser.child(username).setValue(user);
        Toast.makeText(fill_info_Activity.this, "Information added successfully", Toast.LENGTH_SHORT).show();
    }

    /**
     获取控件中的字符串
     */
    private void getEditString()
    {
        //获取用户姓名等基础信息,放入对应的变量里
        name=et_name.getText().toString().trim();
        address=et_address.getText().toString().trim();
        email=et_email.getText().toString().trim();
        phone=et_phone.getText().toString().trim();
    }
    /**
     保存账号和密码到SharedPreferences中SharedPreferences
     */
    private void saveRegisterInfo(String name,String address,String email,String phone,String gender){
        Username= User1.getName();
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor=sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //!!!!!这里还有大问题，会覆盖原密码，需要重写
        editor.putString(Username,name);
        editor.putString(Username,address);
        editor.putString(Username,email);
        editor.putString(Username,phone);
        editor.putString(Username,gender);
        //提交修改 editor.commit();
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==RESULT_OK){
            //when success
            //initialize places
            Place place = Autocomplete.getPlaceFromIntent(data);
            //Set address in edittext
            et_address.setText(place.getAddress());
        }else if(resultCode== AutocompleteActivity.RESULT_ERROR){
            //when error
            //initialize status
            Status status = Autocomplete.getStatusFromIntent(data);
            //toast message
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}