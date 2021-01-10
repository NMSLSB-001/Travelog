package com.example.travelog.ui.DiscoverFragment.View;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelog.R;
import com.example.travelog.ui.DiscoverFragment.View.BeanModel.GridImageModel;
import com.example.travelog.ui.DiscoverFragment.View.ViewAdapter.MyRecycleViewAdapter;
import com.google.gson.Gson;

public class ViewActivity extends Activity {

    private RecyclerView recyclerView;
    private MyRecycleViewAdapter recycleViewAdapter;
    private ImageView topBarImage_1;
    private ImageView ivFavorite;
    private ImageView ivComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view);
        init();
        social();
        new LoadTask().execute();
    }

    private void init() {
        topBarImage_1 = findViewById(R.id.top_bar_image_1);
        ivFavorite = findViewById(R.id.iv_favorite);
        ivComment = findViewById(R.id.iv_comment);
    }

    public void social() {
        topBarImage_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return main
                ViewActivity.this.finish();
            }
        });

        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewActivity.this, "NMSL", Toast.LENGTH_LONG).show();
            }
        });

        ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewActivity.this, "NMHL", Toast.LENGTH_LONG).show();
            }
        });
    }

    //异步加载图片
    class LoadTask extends AsyncTask<Void, Void, GridImageModel> {

        @Override
        protected GridImageModel doInBackground(Void... voids) {
            Gson gson = new Gson();
            GridImageModel itemModle = gson.fromJson(com.example.travelog.ui.DiscoverFragment.View.StringData.getJsonStr(), GridImageModel.class);

            return itemModle;
        }

        @Override
        protected void onPostExecute(GridImageModel itemModle) {
            super.onPostExecute(itemModle);
            recyclerView = findViewById(R.id.listview);
            recyclerView.setLayoutManager(new LinearLayoutManager(ViewActivity.this));
            //recyclerView.addItemDecoration(new RecycleViewDivider(ViewActivity.this, LinearLayoutManager.HORIZONTAL));
            recycleViewAdapter = new MyRecycleViewAdapter(ViewActivity.this);
            recyclerView.setAdapter(recycleViewAdapter);
            recycleViewAdapter.setList(itemModle.getList());

        }
    }

}
