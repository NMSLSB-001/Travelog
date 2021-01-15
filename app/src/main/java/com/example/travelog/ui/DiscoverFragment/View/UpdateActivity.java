package com.example.travelog.ui.DiscoverFragment.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.travelog.MainActivity;
import com.example.travelog.R;
import com.example.travelog.ui.DiscoverFragment.View.BeanModel.GetArticle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.TimeZone;

public class UpdateActivity extends Activity {

    private DatabaseReference mArticleRef;
    private DatabaseReference mImageRef;
    private DatabaseReference mImageRef2;
    private DatabaseReference mArticleRef2;

    private ImageView top_bar_image_1;
    private Button buttonSubmit;
    private EditText etTitle;
    private EditText etContent;
    private ImageView ivLocation;
    private EditText etLocation;

    private String articleId;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_update);
        Intent intent = getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        articleId = intent.getStringExtra("aId");
        Toast.makeText(com.example.travelog.ui.DiscoverFragment.View.UpdateActivity.this, articleId, Toast.LENGTH_LONG).show();

        init();
        initData();
        update();

    }

    private void init() {
        mArticleRef = FirebaseDatabase.getInstance().getReference("articles");
        mImageRef = FirebaseDatabase.getInstance().getReference("images");
        mImageRef2 = FirebaseDatabase.getInstance().getReference("imagesJson");
        mArticleRef2 = FirebaseDatabase.getInstance().getReference("articlesJson");

        top_bar_image_1 = findViewById(R.id.update_top_bar_image_1);
        etTitle = findViewById(R.id.update_et_title);
        etContent = findViewById(R.id.update_et_content);
        ivLocation = findViewById(R.id.update_iv_location);
        etLocation = findViewById(R.id.update_et_location);
        buttonSubmit = findViewById(R.id.update_bt_submit);

    }

    private void initData() {
        top_bar_image_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SeeCommentActivity.this, ViewActivity.class));
                com.example.travelog.ui.DiscoverFragment.View.UpdateActivity.this.finish();
            }
        });


    }

    private void update() {
        etTitle.setText("getArticle.getTitle()");
        etContent.setText("getArticle.getContent()");
        etLocation.setText("getArticle.getLocation()");

        mArticleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    GetArticle getArticle = postSnapshot.getValue(GetArticle.class);

                    if (String.valueOf(getArticle.getId()).equals(articleId)) {
                        etTitle.setText(getArticle.getTitle());
                        etContent.setText((getArticle.getContent()));
                        etLocation.setText(getArticle.getLocation());

                    buttonSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String unVerifyTitle = etTitle.getText().toString().trim();
                            String unVerifyContent = etContent.getText().toString().trim();
                            String unVerifyLocation = etLocation.getText().toString().trim();
                            if (TextUtils.isEmpty(unVerifyTitle)) {
                                Toast.makeText(com.example.travelog.ui.DiscoverFragment.View.UpdateActivity.this, "Please enter a title", Toast.LENGTH_SHORT).show();
                            } else if (TextUtils.isEmpty(unVerifyContent)) {
                                Toast.makeText(com.example.travelog.ui.DiscoverFragment.View.UpdateActivity.this, "Please enter some contents", Toast.LENGTH_SHORT).show();
                            } else if (TextUtils.isEmpty(unVerifyLocation)) {
                                Toast.makeText(com.example.travelog.ui.DiscoverFragment.View.UpdateActivity.this, "Please enter a location", Toast.LENGTH_SHORT).show();
                            } else {
                                //
                                GetArticle gA = new GetArticle(getArticle.getId(), getArticle.getUserId(), unVerifyLocation, unVerifyTitle, unVerifyContent, getDate(), getArticle.getUrlJson());
                                mArticleRef.child(String.valueOf(articleId)).setValue(gA);
                                Gson gson = new Gson();
                                String userJson = gson.toJson(gA);
                                mArticleRef2.child(String.valueOf(articleId)).setValue(userJson);
                                com.example.travelog.ui.DiscoverFragment.View.UpdateActivity.this.finish();
                                startActivity(new Intent(com.example.travelog.ui.DiscoverFragment.View.UpdateActivity.this, MainActivity.class));
                            }

                        }
                    });

                }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private String getDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf(cal.get(Calendar.MONTH)) + 1;
        String day = String.valueOf(cal.get(Calendar.DATE));
        String hour;
        String minute;
        String second;
        String my_time_1;
        String my_time_2;
        if (cal.get(Calendar.AM_PM) == Calendar.AM)
            hour = String.valueOf(cal.get(Calendar.HOUR));
        else
            hour = String.valueOf(cal.get(Calendar.HOUR) + 12);
        minute = String.valueOf(cal.get(Calendar.MINUTE));
        second = String.valueOf(cal.get(Calendar.SECOND));

        my_time_1 = year + "-" + month + "-" + day;
        my_time_2 = hour + ":" + minute + ":" + second;
        return my_time_1 + " " + my_time_2;
    }
}
