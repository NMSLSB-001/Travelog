package com.example.travelog.ui.DiscoverFragment.View.BeanModel;


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

public class ViewUtil {
    private static List<GetArticle> getArticle;
    private static List<GetImage> getImage;

    public static GridImageModel getData(Context context, String articleID) {
        getArticle = new ArrayList<>();
        getImage = new ArrayList<>();
        DatabaseReference mArticleRef;
        DatabaseReference mImageRef;
        mArticleRef = FirebaseDatabase.getInstance().getReference("articlesJson");
        mImageRef = FirebaseDatabase.getInstance().getReference("imagesJson");

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
            GetArticle GA = gson1.fromJson(sp1.getString(String.valueOf(i), ""), GetArticle.class);
            getArticle.add(GA);
        }

        //if(getArticleInfo.size() == 0)
        //news = String.valueOf(getArticleInfo.size());

        SharedPreferences sp2 = context.getSharedPreferences("GetUploadInfo", MODE_PRIVATE);
        SharedPreferences spU = context.getSharedPreferences("GetUploadInfoNumber", MODE_PRIVATE);
        Gson gson2 = new Gson();
        for (int i = 0; i < spU.getInt("NumOfImg", 0); i++) {
            GetImage GI = gson2.fromJson(sp2.getString(String.valueOf(i), ""), GetImage.class);
            getImage.add(GI);
        }

        // if(getUploadInfo.size() == 0)
        //  news = String.valueOf(getUploadInfo.size());
        ListItemModel lim = new ListItemModel();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < getArticle.size(); i++) {
            if (String.valueOf(getArticle.get(i).getId()).equals(articleID)) {
                lim.setId(getArticle.get(i).getId());
                lim.setUserName(getArticle.get(i).getUserId());
                lim.setTitle(getArticle.get(i).getTitle());
                lim.setContent(getArticle.get(i).getContent());
                lim.setLocation(getArticle.get(i).getLocation());
                lim.setTime(getArticle.get(i).getTime());
                lim.setHeadImg("https://firebasestorage.googleapis.com/v0/b/firetest2-6a047.appspot.com/o/%60(7%5DOR5U1L~~%5BFW%5DZ24YHGO.jpg?alt=media&token=bc39c1b1-ba20-4b7a-ac30-ebb9411a7a3d");
                for (int j = 0; j < getImage.size(); j++) {
                    for (int k = 1; k < 10; k++) {
                        if (getImage.get(j).getImageId().equals(String.valueOf(getArticle.get(i).getId()) + k)) {
                            list.add(getImage.get(j).getImageUrl());
                        }

                    }
                }
                lim.setUrls(list);
            }
        }


        ArrayList<ListItemModel> arl;
        arl = new ArrayList<>();
        arl.add(lim);
        GridImageModel gim = new GridImageModel();
        gim.setList(arl);
        gim.setImgUrl("");

        return gim;
    }
}