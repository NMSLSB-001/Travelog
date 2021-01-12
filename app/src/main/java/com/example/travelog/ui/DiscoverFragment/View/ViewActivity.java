package com.example.travelog.ui.DiscoverFragment.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelog.R;
import com.example.travelog.ui.DiscoverFragment.View.BeanModel.GridImageModel;
import com.example.travelog.ui.DiscoverFragment.View.BeanModel.ViewUtil;
import com.example.travelog.ui.DiscoverFragment.View.ViewAdapter.MyRecycleViewAdapter;

public class ViewActivity extends Activity {

    private RecyclerView recyclerView;
    private MyRecycleViewAdapter recycleViewAdapter;
    private ImageView topBarImage_1;
    private ImageView ivComment;
    private ImageView ivSeeComment;

    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view);
        init();
        social();
        view();
    }

    private void init() {
        topBarImage_1 = findViewById(R.id.top_bar_image_1);
        ivComment = findViewById(R.id.iv_comment);
        ivSeeComment = findViewById(R.id.iv_seecomment);

        Intent intent = getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        msg = intent.getStringExtra("data");
        Toast.makeText(com.example.travelog.ui.DiscoverFragment.View.ViewActivity.this, msg, Toast.LENGTH_SHORT).show();

    }

    private void social() {
        topBarImage_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.example.travelog.ui.DiscoverFragment.View.ViewActivity.this.finish();
            }
        });

        ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.travelog.ui.DiscoverFragment.View.ViewActivity.this, CommentActivity.class);
                intent.putExtra("Id", msg);
                startActivity(intent);
            }
        });

        ivSeeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.travelog.ui.DiscoverFragment.View.ViewActivity.this, com.example.travelog.ui.DiscoverFragment.View.SeeCommentActivity.class);
                intent.putExtra("Id", msg);
                startActivity(intent);
            }
        });
    }

    private void view() {
        GridImageModel itemModle = ViewUtil.getData(getApplicationContext(), msg);
        recyclerView = findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(com.example.travelog.ui.DiscoverFragment.View.ViewActivity.this));
        //recyclerView.addItemDecoration(new RecycleViewDivider(ViewActivity.this, LinearLayoutManager.HORIZONTAL));
        recycleViewAdapter = new MyRecycleViewAdapter(com.example.travelog.ui.DiscoverFragment.View.ViewActivity.this);
        recyclerView.setAdapter(recycleViewAdapter);
        recycleViewAdapter.setList(itemModle.getList());

    }
}
