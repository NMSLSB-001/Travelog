package com.example.travelog.ui.DiscoverFragment.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.travelog.R;
import com.example.travelog.ui.Profile.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CommentActivity extends Activity {

    private EditText etComment;
    private RatingBar ratingBar;
    private ImageView ivSelect;
    private ImageView ivClose;
    private String articleId;
    private String RCId;
    private String comment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        articleId = intent.getStringExtra("Id");
        init();
    }

    private void init() {
        etComment = findViewById(R.id.et_comment);
        ratingBar = findViewById(R.id.ratingBar);
        ivClose = findViewById(R.id.iv_close);
        ivSelect = findViewById(R.id.iv_select);


        ivSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                comment();
                com.example.travelog.ui.DiscoverFragment.View.CommentActivity.this.finish();
                //startActivity(new Intent(CommentActivity.this, ViewActivity.class));
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.example.travelog.ui.DiscoverFragment.View.CommentActivity.this.finish();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });
    }

    private void comment() {

        DatabaseReference mCommentRef;
        mCommentRef = FirebaseDatabase.getInstance().getReference("ratingComment");
        long l;
        int i = 0;
        l = System.currentTimeMillis();
        i = (int) (l % 900000) + 100000;
        RCId =  articleId + String.valueOf(i);
        comment = etComment.getText().toString().trim();
        RatingComment ratingComment = new RatingComment(RCId,articleId,ratingBar.getRating(),comment, User.getName());
        mCommentRef.child(RCId).setValue(ratingComment);
    }
}