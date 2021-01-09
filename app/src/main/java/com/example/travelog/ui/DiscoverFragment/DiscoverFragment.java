package com.example.travelog.ui.DiscoverFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.travelog.R;

import java.util.ArrayList;

public class DiscoverFragment extends Fragment {
    private ListView lv;
    private ImageView top_bar_image_1;
    private ArrayList<ViewBean> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        initData();
        //discover();
        return view;
    }

    private void initData() {
        mList = ViewUtils.getAllNews(getActivity());
    }

    private void discover() {
        lv = lv.findViewById(R.id.lv_listView);
        top_bar_image_1 = top_bar_image_1.findViewById(R.id.top_bar_image_1);
        top_bar_image_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        NewsAdapter adapter=new NewsAdapter(getActivity(), mList);
        lv.setAdapter(adapter);
    }
}