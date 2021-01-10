package com.example.travelog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Display_personal_detailActivity extends AppCompatActivity {

    private Button back;
    String name,address,gender,email,phone,username;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_personal_detail);

        listView=findViewById(R.id.listview);
        username= com.example.travelog.User1.getName();
        /*
        这些会全删
        */
        name= com.example.travelog.User1.getReal_Name();
        address= com.example.travelog.User1.getaddress();
        gender= com.example.travelog.User1.getgender();
        email= com.example.travelog.User1.getemail();
        phone= com.example.travelog.User1.getphone();

        /*
        这些会全删，结束
        */

        back=findViewById(R.id.btn_bcak_display);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(com.example.travelog.Display_personal_detailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //显示个人信息
        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.list_item,list);
        listView.setAdapter(adapter);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("personal_detail").child(username);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    list.add(snapshot.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}