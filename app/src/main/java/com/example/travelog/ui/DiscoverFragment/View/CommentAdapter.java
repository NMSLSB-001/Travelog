package com.example.travelog.ui.DiscoverFragment.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.travelog.R;

import java.util.List;

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private List<RatingComment> list;

    public CommentAdapter(Context context, List<RatingComment> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.seecomment_list_item, null);
            vh.tvName = (TextView) view.findViewById(R.id.tv_commentusername);
            vh.tvComment = (TextView) view.findViewById(R.id.tv_comment);
            vh.tvRating = (TextView) view.findViewById(R.id.tv_rating);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        RatingComment ratingComment = list.get(i);
        vh.tvName.setText(ratingComment.getUserName());
        vh.tvComment.setText(ratingComment.getComment());
        vh.tvRating.setText(String.valueOf(ratingComment.getRating()));
        return view;
    }

    public class ViewHolder {
       TextView tvName;
        TextView tvComment;
        TextView tvRating;
    }
}
