package com.example.travelog.ui.Trips;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.travelog.ui.Profile.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Itinerary_detail_dayUtils {
    public static List<String> getItineraryData(Context context, String itineraryID) {
        String Username = User.getName();
        List<String> dayTitle = new ArrayList<>();

        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("itineraryDetails").child(Username).child(itineraryID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //this is used to store data
                SharedPreferences sp = context.getSharedPreferences("GetItineraryDetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                int a = 0;
                for (DataSnapshot dss: snapshot.getChildren()) {
                    String title = dss.getKey();
                    //put data into GetItineraryDetails
                    editor.putString(String.valueOf(a), title);
                    editor.apply();
                    a++;
                }
                //this is used to store the number of data
                SharedPreferences spG = context.getSharedPreferences("GetItineraryDetailsNumber", MODE_PRIVATE);
                SharedPreferences.Editor editorG = spG.edit();
                editorG.putInt("NumOfItineraryDetails", a);
                editorG.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Get data from this two sharedpreference
        SharedPreferences sp = context.getSharedPreferences("GetItineraryDetails", MODE_PRIVATE);
        SharedPreferences spG = context.getSharedPreferences("GetItineraryDetailsNumber", MODE_PRIVATE);
        for (int i = 0; i < spG.getInt("NumOfItineraryDetails", 0); i++) {
            dayTitle.add(sp.getString(String.valueOf(i), ""));
        }

        //return the dataTitle list
        return dayTitle;
    }
}
