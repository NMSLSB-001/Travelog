package com.example.travelog.ui.DiscoverFragment;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DiscoverUtils {
    private static List<com.example.travelog.ui.DiscoverFragment.GetArticleInfo> getArticleInfo;
    private static List<com.example.travelog.ui.DiscoverFragment.GetUploadInfo> getUploadInfo;
    static DatabaseReference mArticleRef;
    static DatabaseReference mImageRef;

    public static ArrayList<com.example.travelog.ui.DiscoverFragment.DiscoverBean> getData(Context context) {
        mArticleRef = FirebaseDatabase.getInstance().getReference("articlesJson");
        mImageRef = FirebaseDatabase.getInstance().getReference("imagesJson");
        getArticleInfo = new ArrayList<>();
        getUploadInfo = new ArrayList<>();

        mArticleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SharedPreferences sp = context.getSharedPreferences("GetArticleInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                int a = 0;
                String b = "";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    b = (String) postSnapshot.getValue();
                    editor.putString(String.valueOf(a), b);
                    editor.apply();
                    a++;
                }
                SharedPreferences spG = context.getSharedPreferences("GetArticleInfoNumber", MODE_PRIVATE);
                SharedPreferences.Editor editorG = spG.edit();
                editorG.putInt("NumOfArt", a);
                editorG.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mImageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SharedPreferences sp = context.getSharedPreferences("GetUploadInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                int a = 0;
                String b = "";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    b = (String) postSnapshot.getValue();
                    editor.putString(String.valueOf(a), b);
                    editor.apply();
                    a++;
                }
                SharedPreferences spG = context.getSharedPreferences("GetUploadInfoNumber", MODE_PRIVATE);
                SharedPreferences.Editor editorG = spG.edit();
                editorG.putInt("NumOfImg", a);
                editorG.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        String news = "null";

        SharedPreferences sp1 = context.getSharedPreferences("GetArticleInfo", MODE_PRIVATE);
        SharedPreferences spA = context.getSharedPreferences("GetArticleInfoNumber", MODE_PRIVATE);
        Gson gson1 = new Gson();
        for (int i = 0; i < spA.getInt("NumOfArt", 0); i++) {
            com.example.travelog.ui.DiscoverFragment.GetArticleInfo GAI = gson1.fromJson(sp1.getString(String.valueOf(i), ""), com.example.travelog.ui.DiscoverFragment.GetArticleInfo.class);
            getArticleInfo.add(GAI);
        }

        //if(getArticleInfo.size() == 0)
        //news = String.valueOf(getArticleInfo.size());

        SharedPreferences sp2 = context.getSharedPreferences("GetUploadInfo", MODE_PRIVATE);
        SharedPreferences spU = context.getSharedPreferences("GetUploadInfoNumber", MODE_PRIVATE);
        Gson gson2 = new Gson();
        for (int i = 0; i < spU.getInt("NumOfImg", 0); i++) {
            com.example.travelog.ui.DiscoverFragment.GetUploadInfo GUI = gson2.fromJson(sp2.getString(String.valueOf(i), ""), com.example.travelog.ui.DiscoverFragment.GetUploadInfo.class);
            getUploadInfo.add(GUI);
        }

        // if(getUploadInfo.size() == 0)
        //  news = String.valueOf(getUploadInfo.size());

        ArrayList<com.example.travelog.ui.DiscoverFragment.DiscoverBean> arrayList = new ArrayList<>();
        for (int i = 0; i < getArticleInfo.size(); i++) {
            com.example.travelog.ui.DiscoverFragment.DiscoverBean newsBean = new com.example.travelog.ui.DiscoverFragment.DiscoverBean();
            int id = getArticleInfo.get(i).getId();
            newsBean.articleId = String.valueOf(id);
            newsBean.title = getArticleInfo.get(i).getTitle();
            newsBean.userName = getArticleInfo.get(i).getUserId();
            newsBean.headImage = "https://firebasestorage.googleapis.com/v0/b/firetest2-6a047.appspot.com/o/%60(7%5DOR5U1L~~%5BFW%5DZ24YHGO.jpg?alt=media&token=bc39c1b1-ba20-4b7a-ac30-ebb9411a7a3d";
            newsBean.icon = "";
            for (int j = 0; j < getUploadInfo.size(); j++) {
                if (getUploadInfo.get(j).getImageId().equals(String.valueOf(id) + 1)) {
                    newsBean.icon = getUploadInfo.get(j).getImageUrl();
                }
            }
            arrayList.add(newsBean);
        }
        return arrayList;
    }
}