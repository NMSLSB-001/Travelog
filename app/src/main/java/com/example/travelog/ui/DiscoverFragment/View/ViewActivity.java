package com.example.travelog.ui.DiscoverFragment.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelog.MainActivity;
import com.example.travelog.ui.Profile.User;
import com.example.travelog.R;
import com.example.travelog.ui.DiscoverFragment.View.BeanModel.GridImageModel;
import com.example.travelog.ui.DiscoverFragment.View.BeanModel.ViewUtil;
import com.example.travelog.ui.DiscoverFragment.View.ViewAdapter.MyRecycleViewAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewActivity extends Activity {

    private RecyclerView recyclerView;
    private MyRecycleViewAdapter recycleViewAdapter;
    private ImageView topBarImage_1;
    private ImageView topBarImage_2;
    private ImageView ivComment;
    private ImageView ivSeeComment;
    private PopupWindow mPoppop;

    DatabaseReference mArticleRef;
    DatabaseReference mArticleRef2;
    DatabaseReference mImageRef;
    DatabaseReference mImageRef2;

    private String articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view);

        Intent intent = getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        articleId = intent.getStringExtra("data");

        init();
        social();
        view();

        topBarImage_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.layout_pop_view, null);
                mPoppop = new PopupWindow(view, topBarImage_2.getWidth() * 3, ViewGroup.LayoutParams.WRAP_CONTENT);
                mPoppop.showAsDropDown(topBarImage_2);
                mPoppop.setOutsideTouchable(true);
                mPoppop.setFocusable(true);
                mPoppop.update();
                //配置点击事件，因为pop会出现2个选择，所以我们设置2个事件
                Button btUpdate = view.findViewById(R.id.btn_update);
                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent(ViewActivity.this, UpdateActivity.class);
                        intent.putExtra("aId", articleId);
                        startActivity(intent);
                        mPoppop.dismiss();
                        //这里可以进行设置，比如把性别写进txt文件
                    }
                });
                Button btDelete = view.findViewById(R.id.btn_delete);
                btDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //Intent intent = new Intent(ViewActivity.this, ViewActivity.class);
                        //intent.putExtra("aId", articleId);
                        //startActivity(intent);
                        //set a new 弹窗
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewActivity.this);
                        builder.setMessage("Are you sure to remove this article?");
                        builder.setTitle("Notice");
                        builder.setPositiveButton("YES", (dialog, which) -> {
                            dialog.dismiss();
                            //delete article and images
                            mArticleRef.child(String.valueOf(articleId)).removeValue();
                            mArticleRef2.child(String.valueOf(articleId)).removeValue();
                            for (int i = 1; i < 10; i++) {
                                mImageRef.child(String.valueOf(articleId) + i).removeValue();
                                mImageRef2.child(String.valueOf(articleId) + i).removeValue();
                            }
                            Toast.makeText(ViewActivity.this, "Delete", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(ViewActivity.this, MainActivity.class));
                            ViewActivity.this.finish();
                        });
                        builder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());
                        builder.create().show();

                        mPoppop.dismiss();

                    }
                });
            }
        });
    }

    private void init() {
        topBarImage_1 = findViewById(R.id.top_bar_image_1);
        topBarImage_2 = findViewById(R.id.top_bar_image_2);
        ivComment = findViewById(R.id.iv_comment);
        ivSeeComment = findViewById(R.id.iv_seecomment);

        mArticleRef = FirebaseDatabase.getInstance().getReference("articles");
        mArticleRef2 = FirebaseDatabase.getInstance().getReference("articlesJson");
        mImageRef = FirebaseDatabase.getInstance().getReference("images");
        mImageRef2 = FirebaseDatabase.getInstance().getReference("imagesJson");

        Toast.makeText(ViewActivity.this, articleId, Toast.LENGTH_SHORT).show();

    }

    private void social() {
        topBarImage_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewActivity.this.finish();
                startActivity(new Intent(ViewActivity.this, MainActivity.class));
            }
        });

        ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.travelog.ui.DiscoverFragment.View.ViewActivity.this, CommentActivity.class);
                intent.putExtra("Id", articleId);
                startActivity(intent);
            }
        });

        ivSeeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.travelog.ui.DiscoverFragment.View.ViewActivity.this, com.example.travelog.ui.DiscoverFragment.View.SeeCommentActivity.class);
                intent.putExtra("Id", articleId);
                startActivity(intent);
            }
        });
    }

    private void view() {
        GridImageModel itemModle = ViewUtil.getData(getApplicationContext(), articleId);
        //get current login user name to decide to show pop=up windows or not
        //User.getName() 是获得当前用户名的接口
        if (!itemModle.getList().get(0).getUserName().equals(User.getName())) {
            topBarImage_2.setVisibility(View.INVISIBLE);
        }

        //recyclerview
        //use to display details
        recyclerView = findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(com.example.travelog.ui.DiscoverFragment.View.ViewActivity.this));
        //recyclerView.addItemDecoration(new RecycleViewDivider(ViewActivity.this, LinearLayoutManager.HORIZONTAL));
        recycleViewAdapter = new MyRecycleViewAdapter(com.example.travelog.ui.DiscoverFragment.View.ViewActivity.this);
        recyclerView.setAdapter(recycleViewAdapter);
        recycleViewAdapter.setList(itemModle.getList());

    }
}
