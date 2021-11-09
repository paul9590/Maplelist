package com.ping.maplelist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {


    Dialog InfoDialog;
    String Title;
    String Body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_info);

        ListView ListInfo = (ListView) findViewById(R.id.ListInfo);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        ArrayList<String> InfoTitleList = new ArrayList<>();
        ArrayList<String> InfoBodyList = new ArrayList<>();
        ArrayAdapter<String> InfoAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.listlayout, InfoTitleList);
        ListInfo.setAdapter(InfoAdapter);


        Button BtnBack = (Button) findViewById(R.id.BtnBack);

        ListInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);

                // Dialog 초기화
                InfoDialog = new Dialog(InfoActivity.this);
                InfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                InfoDialog.setContentView(R.layout.infoform);
                InfoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Title = InfoTitleList.get(position);
                Body = InfoBodyList.get(position);

                InfoDial();



            }
        });

        myRef.child("Board").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                // InfoDb 가져오기
                InfoDb infoDb = snapshot.getValue(InfoDb.class);
                // adapter, 리스트 에 추가하기
                InfoAdapter.add(infoDb.getTitle());
                InfoBodyList.add(infoDb.getBody());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);

    }

    public void InfoDial(){
        InfoDialog.show();

        TextView TxtTitle = (TextView) InfoDialog.findViewById(R.id.TxtTitle);
        TextView TxtBody = (TextView) InfoDialog.findViewById(R.id.TxtBody);
        TxtTitle.setText(Title);
        TxtBody.setText(Body);

        // 뒤로가기 버튼
        Button BtnBac = (Button) InfoDialog.findViewById(R.id.BtnBac);

        BtnBac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoDialog.dismiss();
            }
        });

    }

}