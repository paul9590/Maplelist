package com.ping.maplelist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MvpActivity extends AppCompatActivity {

    int Charge = 5;
    int Count = 2;
    int MaxCount = 10;
    float MesoPrice = 0;
    float [] Meso = new float[MaxCount];
    float [] Cash = new float[MaxCount];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);

        Button BtnMvpAdd = (Button) findViewById(R.id.BtnMvpAdd);
        Button BtnMvpDel = (Button) findViewById(R.id.BtnMvpDel);
        Button BtnMvpRes = (Button) findViewById(R.id.BtnMvpRes);
        CheckBox ChkMvp = (CheckBox) findViewById(R.id.ChkMvp);
        EditText EditMesoPrice = (EditText) findViewById(R.id.EditMesoPrice);

        EditText[] EditMeso = new EditText[]{
                findViewById(R.id.EditMeso1),
                findViewById(R.id.EditMeso2),
                findViewById(R.id.EditMeso3),
                findViewById(R.id.EditMeso4),
                findViewById(R.id.EditMeso5),
                findViewById(R.id.EditMeso6),
                findViewById(R.id.EditMeso7),
                findViewById(R.id.EditMeso8),
                findViewById(R.id.EditMeso9),
                findViewById(R.id.EditMeso10),
        };

        EditText[] EditCash = new EditText[]{
                findViewById(R.id.EditCash1),
                findViewById(R.id.EditCash2),
                findViewById(R.id.EditCash3),
                findViewById(R.id.EditCash4),
                findViewById(R.id.EditCash5),
                findViewById(R.id.EditCash6),
                findViewById(R.id.EditCash7),
                findViewById(R.id.EditCash8),
                findViewById(R.id.EditCash9),
                findViewById(R.id.EditCash10),
        };

        TableRow[] TbMvp = new TableRow[]{
                findViewById(R.id.TbMvp1),
                findViewById(R.id.TbMvp2),
                findViewById(R.id.TbMvp3),
                findViewById(R.id.TbMvp4),
                findViewById(R.id.TbMvp5),
                findViewById(R.id.TbMvp6),
                findViewById(R.id.TbMvp7),
                findViewById(R.id.TbMvp8),
                findViewById(R.id.TbMvp9),
                findViewById(R.id.TbMvp10),
        };

        for(int i = 0; i < MaxCount; i++){
            TbMvp[i].setVisibility(View.GONE);
            Meso[i] = 0;
            Cash[i] = 0;
        }

        for(int i = 0; i < Count; i++){
            TbMvp[i].setVisibility(View.VISIBLE);
        }
        
        if(Charge == 3){
            ChkMvp.setChecked(true);
        }

        BtnMvpAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Count < MaxCount){
                    Count++;
                    TbMvp[Count - 1].setVisibility(View.VISIBLE);
                    EditCash[Count - 1].setText("0");
                    EditMeso[Count - 1].setText("0");
                }
            }
        });

        BtnMvpDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Count > 1){
                    TbMvp[Count - 1].setVisibility(View.INVISIBLE);
                    EditCash[Count - 1].setText("0");
                    EditMeso[Count - 1].setText("0");
                    Count--;
                    if(Count == 0){
                        Count = 1;
                    }
                }
            }
        });
        

        BtnMvpRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean Nxt = true;
                if(EditMesoPrice.getText().toString().equals("")){
                    EditMesoPrice.setText("0");
                }
                for(int i = 0; i < Count; i++){
                    if(EditCash[i].getText().toString().equals("")){
                        EditCash[i].setText("0");
                    }
                    if(EditMeso[i].getText().toString().equals("")){
                        EditMeso[i].setText("0");
                    }
                }
                if(Integer.parseInt(EditMesoPrice.getText().toString()) <= 0){
                    Toast.makeText(MvpActivity.this, "현재 메소 시세를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    Nxt = false;
                }
                for(int i = 0; i < Count; i++){
                    if(Integer.parseInt(EditCash[i].getText().toString()) <= 0){
                        int j = i + 1;
                        Toast.makeText(MvpActivity.this, ""+j+"번째 아이템의 캐시 가격을\n입력해 주세요.", Toast.LENGTH_SHORT).show();
                        Nxt = false;
                    }
                    if(Integer.parseInt(EditMeso[i].getText().toString()) <= 0){
                        int j = i + 1;
                        Toast.makeText(MvpActivity.this, ""+j+"번째 아이템의 메소 가격을\n입력해 주세요.", Toast.LENGTH_SHORT).show();
                        Nxt = false;
                    }
                }
                
                if(Nxt == true) {

                    if (ChkMvp.isChecked()) {
                        Charge = 3;
                    }

                    for(int i = 0; i < Count; i++){
                        Cash[i] = Integer.parseInt(EditCash[i].getText().toString());
                        Meso[i] = Integer.parseInt(EditMeso[i].getText().toString()) * (100 - Charge) / 100;
                    }
                    MesoPrice = Integer.parseInt(EditMesoPrice.getText().toString());
                    Intent MvpResIntent = new Intent(getApplicationContext(), MvpResultActivity.class);
                    MvpResIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    MvpResIntent.putExtra("Meso" , Meso);
                    MvpResIntent.putExtra("Cash" , Cash);
                    MvpResIntent.putExtra("MesoPrice" , MesoPrice);
                    startActivity(MvpResIntent);
                }
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);

    }

}
