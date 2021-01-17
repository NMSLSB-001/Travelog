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

public class Itinerary_detail_hourUtils {
    public static List<String> getTitle(Context context, String itineraryID, String dayTitle) {
        String Username = User.getName();
        List<String> hourTitle = new ArrayList<>();

        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("itineraryDetails").child(Username).child(itineraryID).child(dayTitle);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //this is used to store data
                SharedPreferences sp = context.getSharedPreferences("GetTitleDetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                int a = 0;
                for (DataSnapshot dss: snapshot.getChildren()) {
                    String title = String.valueOf(dss.child("title").getValue());
                    //put data into GetItineraryDetails
                    editor.putString(String.valueOf(a), title);
                    editor.apply();
                    a++;
                }
                //this is used to store the number of data
                SharedPreferences spG = context.getSharedPreferences("GetTitleDetailsNumber", MODE_PRIVATE);
                SharedPreferences.Editor editorG = spG.edit();
                editorG.putInt("NumOfTitle", a);
                editorG.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Get data from this two sharedpreference
        SharedPreferences sp = context.getSharedPreferences("GetTitleDetails", MODE_PRIVATE);
        SharedPreferences spG = context.getSharedPreferences("GetTitleDetailsNumber", MODE_PRIVATE);
        for (int i = 0; i < spG.getInt("NumOfTitle", 0); i++) {
            hourTitle.add(sp.getString(String.valueOf(i), ""));
        }

        //return the dataTitle list
        return hourTitle;
    }

    public static List<String> getTime(Context context, String itineraryID, String dayTitle) {
        String Username = User.getName();
        List<String> hourTime = new ArrayList<>();

        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("itineraryDetails").child(Username).child(itineraryID).child(dayTitle);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //this is used to store data
                SharedPreferences sp = context.getSharedPreferences("GetTimeDetails", MODE_PRIVATE);
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
                SharedPreferences spG = context.getSharedPreferences("GetTimeDetailsNumber", MODE_PRIVATE);
                SharedPreferences.Editor editorG = spG.edit();
                editorG.putInt("NumOfTime", a);
                editorG.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Get data from this two sharedpreference
        SharedPreferences sp = context.getSharedPreferences("GetTimeDetails", MODE_PRIVATE);
        SharedPreferences spG = context.getSharedPreferences("GetTimeDetailsNumber", MODE_PRIVATE);
        for (int i = 0; i < spG.getInt("NumOfTime", 0); i++) {
            hourTime.add(sp.getString(String.valueOf(i), ""));
        }

        //return the dataTitle list
        return hourTime;
    }

    public static List<String> getDescription(Context context, String itineraryID, String dayTitle) {
        String Username = User.getName();
        List<String> hourDescription = new ArrayList<>();

        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("itineraryDetails").child(Username).child(itineraryID).child(dayTitle);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //this is used to store data
                SharedPreferences sp = context.getSharedPreferences("GetDescriptionDetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                int a = 0;
                for (DataSnapshot dss: snapshot.getChildren()) {
                    String title = String.valueOf(dss.child("description").getValue());
                    //put data into GetItineraryDetails
                    editor.putString(String.valueOf(a), title);
                    editor.apply();
                    a++;
                }
                //this is used to store the number of data
                SharedPreferences spG = context.getSharedPreferences("GetDescriptionDetailsNumber", MODE_PRIVATE);
                SharedPreferences.Editor editorG = spG.edit();
                editorG.putInt("NumOfDescription", a);
                editorG.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Get data from this two sharedpreference
        SharedPreferences sp = context.getSharedPreferences("GetDescriptionDetails", MODE_PRIVATE);
        SharedPreferences spG = context.getSharedPreferences("GetDescriptionDetailsNumber", MODE_PRIVATE);
        for (int i = 0; i < spG.getInt("NumOfDescription", 0); i++) {
            hourDescription.add(sp.getString(String.valueOf(i), ""));
        }

        //return the dataTitle list
        return hourDescription;
    }

    public static List<String> getLocation(Context context, String itineraryID, String dayTitle) {
        String Username = User.getName();
        List<String> hourLocation = new ArrayList<>();


        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("itineraryDetails").child(Username).child(itineraryID).child(dayTitle);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //this is used to store data
                SharedPreferences sp = context.getSharedPreferences("GetLocationDetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                int a = 0;
                for (DataSnapshot dss: snapshot.getChildren()) {
                    String title = String.valueOf(dss.child("location").getValue());
                    //put data into GetItineraryDetails
                    editor.putString(String.valueOf(a), title);
                    editor.apply();
                    a++;
                }
                //this is used to store the number of data
                SharedPreferences spG = context.getSharedPreferences("GetLocationDetailsNumber", MODE_PRIVATE);
                SharedPreferences.Editor editorG = spG.edit();
                editorG.putInt("NumOfLocation", a);
                editorG.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Get data from this two sharedpreference
        SharedPreferences sp = context.getSharedPreferences("GetLocationDetails", MODE_PRIVATE);
        SharedPreferences spG = context.getSharedPreferences("GetLocationDetailsNumber", MODE_PRIVATE);
        for (int i = 0; i < spG.getInt("NumOfLocation", 0); i++) {
            hourLocation.add(sp.getString(String.valueOf(i), ""));
        }

        //return the dataTitle list
        return hourLocation;
    }


}