package com.example.travelog.fragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.travelog.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Fragment_Activity1 extends Fragment {

    private TextView tv_text;
    String currentUserID;
    private FirebaseUser firebaseUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment__activity1,container,false);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        currentUserID = firebaseUser.getUid(); //Do what you need to do with the id


        Toast.makeText(getActivity(), currentUserID, Toast.LENGTH_SHORT).show();

        return view;
    }

    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        tv_text=view.findViewById(R.id.tv_text);

    }
}