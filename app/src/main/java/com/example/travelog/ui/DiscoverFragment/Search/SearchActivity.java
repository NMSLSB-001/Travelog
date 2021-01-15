package com.example.travelog.ui.DiscoverFragment.Search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.travelog.MainActivity;
import com.example.travelog.ui.DiscoverFragment.DiscoverBean;
import com.example.travelog.ui.DiscoverFragment.DiscoverUtils;
import com.example.travelog.ui.DiscoverFragment.NewsAdapter;
import com.example.travelog.R;
import com.example.travelog.ui.DiscoverFragment.View.ViewActivity;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    private ListView searchLv;
    private SearchView searchView;
    private ImageView search_top_bar_image_1;
    private ArrayList<DiscoverBean> mList;
    private ArrayList<DiscoverBean> filterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mList = new ArrayList<>();
        mList =  DiscoverUtils.getData(getApplicationContext());// data from database
        //can use directly

        searchLv = (ListView) findViewById(R.id.search_listView);
        searchView = (SearchView) findViewById(R.id.searchView);
        search_top_bar_image_1 = (ImageView) findViewById(R.id.search_top_bar_image_1);
        discover();
    }


    private void discover() {
        search_top_bar_image_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SearchActivity.this.finish();
               startActivity(new Intent(SearchActivity.this, MainActivity.class));
            }
        });
        //search bar
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList = new ArrayList<>();
                if (!TextUtils.isEmpty(newText)) {
                    for(int i = 0; i < mList.size(); i++) {
                        if (mList.get(i).title.toLowerCase().contains(newText.toLowerCase())) {
                            filterList.add(mList.get(i));
                        }
                    }
                    initList(filterList);//put filter list into the adapter
                } else {
                    //lListView.clearTextFilter();
                }
                return false;
            }
        });
    }




    private void initList(ArrayList<DiscoverBean> resultList) {
        searchLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, ViewActivity.class);
                intent.putExtra("data", resultList.get(position).articleId);
                intent.putExtra("status", "1");
                startActivity(intent);
            }
        });
        NewsAdapter adapter = new NewsAdapter(getApplicationContext(), resultList);
        searchLv.setAdapter(adapter);
    }
}