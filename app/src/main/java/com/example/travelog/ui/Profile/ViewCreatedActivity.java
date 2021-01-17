package com.example.travelog.ui.Profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.travelog.R;
import com.example.travelog.ui.DiscoverFragment.DiscoverBean;
import com.example.travelog.ui.DiscoverFragment.DiscoverUtils;
import com.example.travelog.ui.DiscoverFragment.NewsAdapter;
import com.example.travelog.ui.DiscoverFragment.View.ViewActivity;

import java.util.ArrayList;

public class ViewCreatedActivity extends Activity {

    private ListView profileLv;
    private ImageView created_top_bar_image_1;
    private ArrayList<DiscoverBean> mList;
    private ArrayList<DiscoverBean> currentList;
    private ImageView delete;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view_created_article);

        created_top_bar_image_1 = (ImageView) findViewById(R.id.created_top_bar_image_1);
        profileLv = (ListView) findViewById(R.id.profile_listView);
        created_top_bar_image_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCreatedActivity.this.finish();
            }
        });

        setProfileList();
    }

    private void setProfileList() {
        mList = new ArrayList<>();
        mList =  DiscoverUtils.getData(this);
        currentList = new ArrayList<>();
        for (int i=0; i < mList.size(); i++) {
            if(mList.get(i).userName.equals(User.getName())) {
                currentList.add(mList.get(i));
            }
        }

        profileLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewCreatedActivity.this, ViewActivity.class);
                intent.putExtra("data", currentList.get(position).articleId);
                intent.putExtra("status", "1");
                startActivity(intent);
            }
        });
        NewsAdapter adapter = new NewsAdapter(this, currentList);
        profileLv.setAdapter(adapter);
    }
}
