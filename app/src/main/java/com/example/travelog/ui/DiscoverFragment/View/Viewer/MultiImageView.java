package com.example.travelog.ui.DiscoverFragment.View.Viewer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.travelog.R;

import java.util.List;

public class MultiImageView extends LinearLayout {
    private List<String> imagesList;
    private int pxOneWidth = DensityUtil.dip2px(getContext(), 400); // 单张图时候的宽
    private int pxOneHeight = DensityUtil.dip2px(getContext(), 400); // 单张图时候的高

    private LayoutParams onePicPara;
    private LayoutParams rowPara;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public MultiImageView(Context context) {
        super(context);
    }

    public MultiImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setList(List<String> lists) throws IllegalArgumentException {
        if (lists == null) {
            throw new IllegalArgumentException("imageList is null...");
        }
        imagesList = lists;
        initImageLayoutParams();
        initView();
    }

    private void initImageLayoutParams() {

        onePicPara = new LayoutParams(pxOneWidth, pxOneHeight);
        int wrap = LayoutParams.WRAP_CONTENT;
        int match = LayoutParams.MATCH_PARENT;
        rowPara = new LayoutParams(match, wrap);
        rowPara.setMargins(0, 0, 0, 0);
    }

    // 根据imageView的数量初始化不同的View布局,还要为每一个View作点击效果
    private void initView() {
        this.setOrientation(VERTICAL);
        this.removeAllViews();

        if (imagesList == null || imagesList.size() == 0) {
            return;
        }

        for (final String url : imagesList) {
            final ImageView imageView = new ImageView(getContext());
            imageView.setId(url.hashCode()); // 指定id
            imageView.setLayoutParams(onePicPara);
            imageView.setScaleType(ScaleType.CENTER_CROP);

            Glide.with(getContext()).load(url).
                    thumbnail(Glide.with(getContext()).
                            load(R.drawable.load)).
                    fitCenter().
                    skipMemoryCache(true).
                    diskCacheStrategy(DiskCacheStrategy.NONE).
                    into(imageView);
            imageView.setPadding(0, 0, 0, 50);
            imageView.setOnClickListener(mImageViewOnClickListener);
            addView(imageView);
        }
    }

    // 图片点击事件
    private OnClickListener mImageViewOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, view.getTag() + "");
            }
        }
    };

    public interface OnItemClickListener {
        void onItemClick(View view, String position);
    }

}