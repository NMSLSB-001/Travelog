package com.example.travelog.ui.DiscoverFragment.View.ViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.travelog.R;
import com.example.travelog.ui.DiscoverFragment.DiscoverBean;
import com.example.travelog.ui.DiscoverFragment.View.BeanModel.ListItemModel;
import com.example.travelog.ui.DiscoverFragment.View.ViewGalleryActivity;
import com.example.travelog.ui.DiscoverFragment.View.Viewer.MultiImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyRecycleViewAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<ListItemModel> list;
    private LayoutInflater mInflater;
    private ArrayList<String> images;
//    List<ListItemModel> listAll;

    public MyRecycleViewAdapter(Context mContext) {

        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
//        this.listAll = new ArrayList<>(list);
    }

    public void setList(List<ListItemModel> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.view_list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int pos) {

        final ViewHolder viewHolder = (ViewHolder) holder;
        final ListItemModel itemModle = list.get(pos);

        holder.itemView.setTag(pos);
        viewHolder.tvName.setText(itemModle.getUserName());
        viewHolder.tvLocation.setText(itemModle.getLocation());
        viewHolder.tvTitle.setText(itemModle.getTitle());
        viewHolder.tvContent.setText(itemModle.getContent());
        viewHolder.tvTime.setText(itemModle.getTime());
        Glide.with(mContext).asBitmap().load(itemModle.getHeadImg()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(new BitmapImageViewTarget(viewHolder.ivHead) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable rounde = RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                rounde.setCircular(true);
                //要实现圆角，只需要加上这句
                rounde.setCornerRadius(100L);
                viewHolder.ivHead.setImageDrawable(rounde);
            }
        });


        if (itemModle != null && itemModle.getUrls().size() > 0) {
            viewHolder.gridView.setVisibility(View.VISIBLE);
            viewHolder.gridView.setList(itemModle.getUrls());

            viewHolder.gridView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                @Override
                public void onItemClick(View view, String position) {
                    addPic(itemModle);
                    Intent intent = new Intent(mContext, ViewGalleryActivity.class);
                    intent.putStringArrayListExtra("imagePath", images);
                    intent.putExtra("position", position);
                    mContext.startActivity(intent);
                }
            });
        } else {
            viewHolder.gridView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    @Override
//    public Filter getFilter() {
//
//        return filter;
//    }
//
//    Filter filter = new Filter() {
//
//        //run on background
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//
//            List<ListItemModel> filteredList = new ArrayList<>();
//            if(constraint.toString().isEmpty()){
//                filteredList.addAll(listAll);
//            }else{
//                for(ListItemModel item: listAll){
//                    if(item.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())){
//                        filteredList.add(item);
//                    }
//                }
//            }
//
//            FilterResults filterResults = new FilterResults();
//            filterResults.values = filteredList;
//            return filterResults;
//        }
//
//        //run on ui thread
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//
//            list.clear();
//            list.addAll((Collection<? extends ListItemModel>) results.values);
//            notifyDataSetChanged();
//        }
//
//    };

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView ivHead;
        TextView tvLocation;
        TextView tvTitle;
        TextView tvContent;
        TextView tvTime;
        MultiImageView gridView;

        public ViewHolder(View itemView) {
            super(itemView);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_username);
            tvLocation = (TextView) itemView.findViewById(R.id.tv_location);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            gridView = (MultiImageView) itemView.findViewById(R.id.gridview);
        }
    }

    private void addPic(final ListItemModel item) {
        if (images == null) {
            images = new ArrayList<>();
        } else {
            images.clear();
        }
        images.addAll(item.getUrls());
    }

}
