package com.example.travelog.ui.DiscoverFragment.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.travelog.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SeeCommentActivity extends Activity {

    private ImageView see_top_bar_image_1;
    private ListView see_lv;
    private String articleId;
    private String RCId;
    private String comment;
    private List<RatingComment> list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_seecomment);
        Intent intent = getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        articleId = intent.getStringExtra("Id");
        init();
        com.example.travelog.ui.DiscoverFragment.View.CommentAdapter adapter = new com.example.travelog.ui.DiscoverFragment.View.CommentAdapter(getApplicationContext(), list);
        see_lv.setAdapter(adapter);
    }

    private void init() {
        see_lv = (ListView) findViewById(R.id.see_listView);
        see_top_bar_image_1 = findViewById(R.id.see_top_bar_image_1);
        see_top_bar_image_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SeeCommentActivity.this, ViewActivity.class));
                com.example.travelog.ui.DiscoverFragment.View.SeeCommentActivity.this.finish();
            }
        });

        DatabaseReference mCommentRef;
        mCommentRef = FirebaseDatabase.getInstance().getReference("ratingComment");
        list = new ArrayList<>();
        mCommentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    RatingComment ratingComment = postSnapshot.getValue(RatingComment.class);
                    list.add(ratingComment);
                }
                List<RatingComment> mList = new ArrayList<>();
                for (int i= 0;i<list.size();i++){
                    if(list.get(i).getArticleId().equals(articleId)){
                        mList.add(list.get(i));
                    }
                }
                com.example.travelog.ui.DiscoverFragment.View.CommentAdapter adapter = new com.example.travelog.ui.DiscoverFragment.View.CommentAdapter(getApplicationContext(), mList);
                see_lv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
