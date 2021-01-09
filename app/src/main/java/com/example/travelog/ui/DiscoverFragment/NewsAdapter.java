package com.example.travelog.ui.DiscoverFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.travelog.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends BaseAdapter {

    private ArrayList<ViewBean> mList;
    Context context;

    public NewsAdapter(Context context, ArrayList<ViewBean> data){
        mList = data;
        this.context = context;
        }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public ViewBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.discover_listview_item, null);
            holder.tvArticleID = (TextView) convertView.findViewById(R.id.tv_articleid);
            holder.ivHeadImage = (ImageView) convertView.findViewById(R.id.iv_head);
            holder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ViewBean item = getItem(position);
        holder.tvArticleID.setText(item.articleId);
        holder.tvUserName.setText(item.userName);
        Glide.with(context).load(item.headImage).
                thumbnail(Glide.with(context).
                        load(R.drawable.load)).
                fitCenter().
                skipMemoryCache(true).
                diskCacheStrategy(DiskCacheStrategy.NONE).
                into(holder.ivHeadImage);
        holder.tvTitle.setText(item.title);
        Glide.with(context).load(item.icon).
                thumbnail(Glide.with(context).
                        load(R.drawable.load)).
                fitCenter().
                skipMemoryCache(true).
                diskCacheStrategy(DiskCacheStrategy.NONE).
                into(holder.ivIcon);
        return convertView;
    }

    private static class ViewHolder {
        TextView tvArticleID;
        TextView tvUserName;
        ImageView ivHeadImage;
        TextView tvTitle;
        ImageView ivIcon;

    }
}