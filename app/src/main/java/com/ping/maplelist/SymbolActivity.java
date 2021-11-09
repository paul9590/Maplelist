package com.ping.maplelist;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class SymbolActivity extends AppCompatActivity {
    private myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

    Dialog RaeDialog, ArcaDialog;

    String CharName = "";
    int CharLv = 200;
    int SymCount = 8;
    int Lv = 0;

    int Symbol[] = new int[SymCount];
    int SymbolInfo[] = new int[SymCount];
    int NeedSym[] = new int[SymCount];

    long SymMoney[] = new long[SymCount];

    int Sym[] = {22, 23, 12, 8, 8, 8, 5, 5};


    SymArray SymArray = new SymArray();


    ArrayList<Integer> ArcMoney = SymArray.ArrayArcMoney();
    ArrayList<Integer> ArcMoney1 = SymArray.ArrayArcMoney1();

    ArrayList<Integer> SaeMoney = SymArray.ArraySaeMoney();
    ArrayList<Integer> HoMoney = SymArray.ArrayHoMoeny();

    ArrayList<Integer> ArcNeed = SymArray.ArrayArcNeed();
    ArrayList<Integer> TicNeed = SymArray.ArrayTicNeed();

    EditText[] EditSymbol, EditSymbolInfo;
    TableRow[] TbSym;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_symbol);

        EditSymbol = new EditText[]{
                findViewById(R.id.EditSym1),
                findViewById(R.id.EditSym2),
                findViewById(R.id.EditSym3),
                findViewById(R.id.EditSym4),
                findViewById(R.id.EditSym5),
                findViewById(R.id.EditSym6),
                findViewById(R.id.EditSym7),
                findViewById(R.id.EditSym8),
        };
        EditSymbolInfo = new EditText[]{
                findViewById(R.id.EditSym1Info),
                findViewById(R.id.EditSym2Info),
                findViewById(R.id.EditSym3Info),
                findViewById(R.id.EditSym4Info),
                findViewById(R.id.EditSym5Info),
                findViewById(R.id.EditSym6Info),
                findViewById(R.id.EditSym7Info),
                findViewById(R.id.EditSym8Info),
        };

        TbSym = new TableRow[]{
                findViewById(R.id.TbSym1),
                findViewById(R.id.TbSym2),
                findViewById(R.id.TbSym3),
                findViewById(R.id.TbSym4),
                findViewById(R.id.TbSym5),
                findViewById(R.id.TbSym6),
                findViewById(R.id.TbSym7),
                findViewById(R.id.TbSym8),
        };

        myDBHelper = new myDBHelper(this);

        Intent SymbolIntent = getIntent();

        CharName = SymbolIntent.getStringExtra("CharName");
        CharLv = SymbolIntent.getIntExtra("CharLv", 200);


        sqlDB = myDBHelper.getReadableDatabase();
        Cursor cur;
        cur = sqlDB.rawQuery("SELECT * FROM SymbolTB WHERE CharName = '" + CharName +"';",null);
        cur.moveToFirst();
        for(int i = 0; i < 8; i++){
            Symbol[i] = cur.getInt(cur.getColumnIndex("Symbol"+i));
            SymbolInfo[i] = cur.getInt(cur.getColumnIndex("Symbol" + i + "Info"));
            EditSymbol[i].setText("" + Symbol[i]);
            EditSymbolInfo[i].setText("" + SymbolInfo[i]);
            TbSym[i].setVisibility(View.INVISIBLE);
        }
        cur.close();
        sqlDB.close();

        if(CharLv >= 200){
            TbSym[0].setVisibility(View.VISIBLE);
            Lv++;
        }
        if(CharLv >= 210){
            TbSym[1].setVisibility(View.VISIBLE);
            Lv++;
        }
        if(CharLv >= 220){
            TbSym[2].setVisibility(View.VISIBLE);
            Lv++;
        }
        if(CharLv >= 225){
            TbSym[3].setVisibility(View.VISIBLE);
            Lv++;
        }
        if(CharLv >= 230){
            TbSym[4].setVisibility(View.VISIBLE);
            Lv++;
        }
        if(CharLv >= 235){
            TbSym[5].setVisibility(View.VISIBLE);
            Lv++;
        }
        if(CharLv >= 260){
            TbSym[6].setVisibility(View.VISIBLE);
            Lv++;
        }
        if(CharLv >= 270){
            TbSym[7].setVisibility(View.VISIBLE);
            Lv++;
        }



        Button btnSymRes = (Button) findViewById(R.id.BtnSymRes);

        btnSymRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] SymName = {"여로", "츄츄", "레헬른", "아르카나", "모라스", "에스페라", "세르니움", "아르크스"};
                Boolean Nxt = true;
                for(int i = 0; i < 8; i++){
                    if(EditSymbol[i].getText().toString().equals("")){
                        EditSymbol[i].setText("1");
                    }
                    if(EditSymbolInfo[i].getText().toString().equals("")){
                        EditSymbolInfo[i].setText("0");
                    }
                }
                for(int i = 0; i < 6; i++){
                    if(Integer.parseInt(EditSymbol[i].getText().toString()) <= 0 || Integer.parseInt(EditSymbol[i].getText().toString()) > 20) {
                        Toast.makeText(SymbolActivity.this, SymName[i] + "심볼의 레벨은 '" + EditSymbol[i].getText().toString() +"'이(가) 될수 없습니다." , Toast.LENGTH_SHORT).show();
                        if(Integer.parseInt(EditSymbol[i].getText().toString()) > 20) {
                            EditSymbol[i].setText("20");
                        }else{
                            EditSymbol[i].setText("1");
                        }
                        EditSymbolInfo[i].setText("0");
                        Nxt = false;
                    }
                }

                for(int i = 6; i < 8; i++){
                    if(Integer.parseInt(EditSymbol[i].getText().toString()) <= 0 || Integer.parseInt(EditSymbol[i].getText().toString()) > 11) {
                        Toast.makeText(SymbolActivity.this, SymName[i] + "심볼의 레벨은 '" + EditSymbol[i].getText().toString() +"'이(가) 될수 없습니다." , Toast.LENGTH_SHORT).show();
                        if(Integer.parseInt(EditSymbol[i].getText().toString()) > 11) {
                            EditSymbol[i].setText("11");
                        }else{
                            EditSymbol[i].setText("1");
                        }
                        EditSymbolInfo[i].setText("0");
                        Nxt = false;
                    }
                }
                if(Nxt){

                    for(int i = 0; i < 6; i++) {
                        for (int j = Symbol[i]; j < ArcNeed.size(); j++) {
                            NeedSym[i] += ArcNeed.get(j);
                        }
                        NeedSym[i] -= Integer.parseInt(EditSymbolInfo[i].getText().toString());
                    }

                    for(int i = 6; i < 8; i++) {
                        for (int j = Symbol[i]; j < TicNeed.size(); j++) {
                            NeedSym[i] += TicNeed.get(j);
                        }
                        NeedSym[i] -= Integer.parseInt(EditSymbolInfo[i].getText().toString());
                    }


                    for(int i = Symbol[0]; i < ArcMoney1.size(); i++){
                        SymMoney[0] += ArcMoney1.get(i);
                    }

                    for(int i = 1; i < 6; i++) {
                        for (int j = Symbol[i]; j < ArcMoney.size(); j++) {
                            SymMoney[i] += ArcMoney.get(j);
                        }
                    }

                    for(int i = Symbol[6]; i < SaeMoney.size(); i++){
                        SymMoney[6] += SaeMoney.get(i);
                    }
                    for(int i = Symbol[7]; i < HoMoney.size(); i++){
                        SymMoney[7] += HoMoney.get(i);
                    }

                    if (CharLv >= 265) {
                        Sym[6] = 10;
                    }

                    sqlDB = myDBHelper.getWritableDatabase();
                    sqlDB.execSQL("UPDATE SymbolTB SET Symbol0 = '" + Integer.parseInt(EditSymbol[0].getText().toString()) + "', Symbol0Info = '" + Integer.parseInt(EditSymbolInfo[0].getText().toString()) +
                            "', Symbol1 = '" + Integer.parseInt(EditSymbol[1].getText().toString()) + "', Symbol1Info = '" + Integer.parseInt(EditSymbolInfo[1].getText().toString()) +
                            "', Symbol2 = '" + Integer.parseInt(EditSymbol[2].getText().toString()) + "', Symbol2Info = '" + Integer.parseInt(EditSymbolInfo[2].getText().toString()) +
                            "', Symbol3 = '" + Integer.parseInt(EditSymbol[3].getText().toString()) + "', Symbol3Info = '" + Integer.parseInt(EditSymbolInfo[3].getText().toString()) +
                            "', Symbol4 = '" + Integer.parseInt(EditSymbol[4].getText().toString()) + "', Symbol4Info = '" + Integer.parseInt(EditSymbolInfo[4].getText().toString()) +
                            "', Symbol5 = '" + Integer.parseInt(EditSymbol[5].getText().toString()) + "', Symbol5Info = '" + Integer.parseInt(EditSymbolInfo[5].getText().toString()) +
                            "', Symbol6 = '" + Integer.parseInt(EditSymbol[6].getText().toString()) + "', Symbol6Info = '" + Integer.parseInt(EditSymbolInfo[6].getText().toString()) +
                            "', Symbol7 = '" + Integer.parseInt(EditSymbol[7].getText().toString()) + "', Symbol7Info = '" + Integer.parseInt(EditSymbolInfo[7].getText().toString()) + "' WHERE CharName = '" + CharName + "';");

                    sqlDB.close();

                    if(CharLv >= 220 && Integer.parseInt(EditSymbol[2].getText().toString()) < 20){
                        RaeDialog = new Dialog(SymbolActivity.this);
                        RaeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                        RaeDialog.setContentView(R.layout.symdial);
                        RaeDial();

                    }else if(CharLv >= 225 && Integer.parseInt(EditSymbol[3].getText().toString()) < 20 && Integer.parseInt(EditSymbol[2].getText().toString()) == 20) {
                        ArcaDialog = new Dialog(SymbolActivity.this);
                        ArcaDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                        ArcaDialog.setContentView(R.layout.symdial);

                        ArcaDial();
                    }else{
                        Intent SymbolResIntent = new Intent(getApplicationContext(), SymbolResultActivity.class);
                        SymbolResIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        SymbolResIntent.putExtra("CharName" , CharName);
                        SymbolResIntent.putExtra("CharLv" , CharLv);
                        SymbolResIntent.putExtra("Sym" , Sym);
                        SymbolResIntent.putExtra("Symbol" , Symbol);
                        SymbolResIntent.putExtra("NeedSym" , NeedSym);
                        SymbolResIntent.putExtra("SymMoney" , SymMoney);
                        SymbolResIntent.putExtra("Lv", Lv);
                        startActivity(SymbolResIntent);
                    }
                }

            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        for(int i =0; i < SymCount; i++){
            NeedSym[i] = 0;
            SymMoney[i] = 0;
        }
        Sym[2] = 12;
        Sym[3] = 8;

    }


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);

    }

    // Rae 다이얼로그
    public void RaeDial() {
        RaeDialog.show();

        TextView TxtSymAsk = (TextView) RaeDialog.findViewById(R.id.TxtSymAsk);
        TxtSymAsk.setText("드림브레이커 층수를 입력해 주세요.");

        Button BtnOk = (Button) RaeDialog.findViewById(R.id.BtnOk);
        EditText EditSymCount = (EditText) RaeDialog.findViewById(R.id.EditSymCount);

        BtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(EditSymCount.getText().toString()) <= 100) {
                    Sym[2] += Integer.parseInt(EditSymCount.getText().toString()) / 10;
                    EditSymCount.setText("");
                    TxtSymAsk.setText("");
                    if(CharLv >= 225 && Integer.parseInt(EditSymbol[3].getText().toString()) < 20){
                        ArcaDialog = new Dialog(SymbolActivity.this);
                        ArcaDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                        ArcaDialog.setContentView(R.layout.symdial);
                        ArcaDial();
                    }else{
                        Intent SymbolResIntent = new Intent(getApplicationContext(), SymbolResultActivity.class);
                        SymbolResIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        SymbolResIntent.putExtra("CharName" , CharName);
                        SymbolResIntent.putExtra("CharLv" , CharLv);
                        SymbolResIntent.putExtra("Sym" , Sym);
                        SymbolResIntent.putExtra("Symbol" , Symbol);
                        SymbolResIntent.putExtra("NeedSym" , NeedSym);
                        SymbolResIntent.putExtra("SymMoney" , SymMoney);
                        SymbolResIntent.putExtra("Lv", Lv);
                        startActivity(SymbolResIntent);
                    }
                    RaeDialog.dismiss();
                }else{
                    Toast.makeText(SymbolActivity.this, "드림 브레이커 층수는 100층 밑으로 입력해 주세요. " , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // Arca 다이얼로그
    public void ArcaDial() {
        ArcaDialog.show();

        TextView TxtSymAsk = (TextView) ArcaDialog.findViewById(R.id.TxtSymAsk);
        TxtSymAsk.setText("스피릿 세이비어 3판 전체 점수를 입력해 주세요.");

        Button BtnOk = (Button) ArcaDialog.findViewById(R.id.BtnOk);
        EditText EditSymCount = (EditText) ArcaDialog.findViewById(R.id.EditSymCount);

        BtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(EditSymCount.getText().toString()) <= 30000) {
                    int Tmp = 0;
                    if(Integer.parseInt(EditSymCount.getText().toString()) >= 3000) {
                        Tmp = Integer.parseInt(EditSymCount.getText().toString()) / 3000;
                    }
                    Sym[3] += Tmp;
                    EditSymCount.setText("");
                    TxtSymAsk.setText("");
                    Intent SymbolResIntent = new Intent(getApplicationContext(), SymbolResultActivity.class);
                    SymbolResIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    SymbolResIntent.putExtra("CharName" , CharName);
                    SymbolResIntent.putExtra("CharLv" , CharLv);
                    SymbolResIntent.putExtra("Sym" , Sym);
                    SymbolResIntent.putExtra("Symbol" , Symbol);
                    SymbolResIntent.putExtra("NeedSym" , NeedSym);
                    SymbolResIntent.putExtra("SymMoney" , SymMoney);
                    SymbolResIntent.putExtra("Lv", Lv);
                    startActivity(SymbolResIntent);
                    ArcaDialog.dismiss();

                }else{
                    Toast.makeText(SymbolActivity.this, "스피릿 세이비어 점수는 30000점 밑으로 입력해 주세요. " , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
