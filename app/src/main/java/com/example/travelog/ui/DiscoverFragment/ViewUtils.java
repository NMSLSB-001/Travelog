package com.example.travelog.ui.DiscoverFragment;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.example.travelog.R;

import java.util.ArrayList;

/**
 * Created by My on 2016/11/8.
 */

public class ViewUtils {
    public static ArrayList<ViewBean> getAllNews(Context context) {
        ArrayList<ViewBean> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ViewBean newsBean = new ViewBean();
            newsBean.articleId = "1";
            newsBean.title = " 1";
            newsBean.userName = "1";
            newsBean.headImage = "https://firebasestorage.googleapis.com/v0/b/firetest2-abd4d.appspot.com/o/uploads%2Fphoto%2F766fa242-b437-49e1-8043-00686aee2642.png?alt=media&token=64f5d46d-afb9-4697-b303-ad76a3ed7119";
            newsBean.icon =  "https://firebasestorage.googleapis.com/v0/b/firetest2-abd4d.appspot.com/o/uploads%2Fphoto%2Fa0ceb591-6464-40e0-9f47-bbd10d1be877.png?alt=media&token=b77ebe62-8621-4fd6-b2ed-1bac5aa36586";
            arrayList.add(newsBean);
        }
        return arrayList;
    }
}