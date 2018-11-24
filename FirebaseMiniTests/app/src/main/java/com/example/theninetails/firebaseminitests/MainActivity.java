package com.example.theninetails.firebaseminitests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText etUser;
    EditText etPass;
    Button btnAdd;
    Button btnGet;
    TextView tvView;
    DatabaseReference mDatabase;
    ChildEventListener childListner;
    ValueEventListener usersListListner;
    public static final String TAG="UsersTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPass=findViewById(R.id.etPass);
        etUser=findViewById(R.id.etUser);
        btnAdd=findViewById(R.id.btnAdd);
        btnGet=findViewById(R.id.btnGet);
        tvView=findViewById(R.id.tvView);

        usersListListner = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users list = dataSnapshot.getValue(Users.class);
                Log.d(TAG, "onDataChange: "+list.getUsers());
                StringBuilder view = new StringBuilder();
                for(String key:list.getUsers().keySet()){
                    view.append(list.getUsers().get(key).getUsername()+"\n") ;
                }
                tvView.setText(view.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        childListner = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = (int)(Math.random()*10000000);
                Log.d(TAG, "onClick: "+id);
                writeNewUser(id+"pui",etUser.getText().toString(),etPass.getText().toString());
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(usersListListner);

    }
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }
}
