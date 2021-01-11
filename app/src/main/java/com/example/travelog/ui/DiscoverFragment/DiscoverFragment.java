package com.example.travelog.ui.DiscoverFragment;

import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.travelog.R;
import com.example.travelog.ui.DiscoverFragment.View.BeanModel.ViewUtil;
import com.example.travelog.ui.DiscoverFragment.View.ViewActivity;
import com.example.travelog.ui.Trips.ArticleAdd.AddActivity;
import com.example.travelog.ui.Trips.Itinerary_create;

import java.util.ArrayList;
import java.util.Objects;

public class DiscoverFragment extends Fragment {
    private ListView lv;
    private ImageView top_bar_image_1;
    private ArrayList<DiscoverBean> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        mList = new ArrayList<>();
        mList =  DiscoverUtils.getData(this.requireContext());

        lv = view.findViewById(R.id.listView);
        top_bar_image_1 = view.findViewById(R.id.top_bar_image_1);

        top_bar_image_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ViewActivity.class);
                intent.putExtra("data", mList.get(position).articleId);
                startActivity(intent);
            }
        });
        NewsAdapter adapter = new NewsAdapter(getContext(), mList);
        lv.setAdapter(adapter);
        return view;
    }
}