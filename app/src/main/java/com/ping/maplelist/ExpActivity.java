package com.ping.maplelist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ExpActivity  extends AppCompatActivity {
    private myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;


    ExpArray ExpArr = new ExpArray();
    ExpSwitch ExpSw = new ExpSwitch();

    ArrayList<String> ArrayExp = ExpArr.ArrayExp();
    ArrayList<String> ArrayMobName = ExpArr.ArrayMobName();
    ArrayList<String> ArrayMap = ExpArr.ArrayMap();
    ArrayList<String> ArrayMobExp = ExpArr.ArrayMobExp();

    int MobCount = 0;

    int SelectMobExp = 0;
    String SelectMobName = "";
    int SelectMobLv = 0;
    int del = 0;
    int sav = 0;
    int ExpCount = 100;
    //경쿠용
    int Exp5 = 0;
    int[] ChkL = {0,0,0};
    int[] ChkE = {0,0,0,0,0,0};

    // 직업용
    int JobExp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp);

        myDBHelper = new myDBHelper(this);

        Spinner SpinMap = (Spinner) findViewById(R.id.SpinMap);
        Spinner SpinMob = (Spinner) findViewById(R.id.SpinMob);

        //Link 제로, 에반, 메르
        CheckBox[] ChkLink = {
                findViewById(R.id.ChkLink1),
                findViewById(R.id.ChkLink2),
                findViewById(R.id.ChkLink3),
        };

        //Edit 버닝, 하이퍼, 유니온, 쓸심, 5분당 마릿수
        EditText[] EditExp = {
                findViewById(R.id.EditBurn),
                findViewById(R.id.EditHyper),
                findViewById(R.id.EditUnion),
                findViewById(R.id.EditSkill),
                findViewById(R.id.EditMob),
        };

        //Check 경축비, 익골비, 정펜, 피시방, 경쿠, 경뿌
        CheckBox[] ChkExp = {
            findViewById(R.id.ChkExp1),
            findViewById(R.id.ChkExp2),
            findViewById(R.id.ChkExp3),
            findViewById(R.id.ChkExp4),
            findViewById(R.id.ChkExp5),
            findViewById(R.id.ChkExp6),
        };

        //Edit 목표 레벨
        EditText EditLv = (EditText) findViewById(R.id.EditLv);

        
        Button BtnExpRes = (Button) findViewById(R.id.BtnExpRes);

        //비숍 전용
        TableRow TableSkill = (TableRow) findViewById(R.id.TableSkill);

        Intent ExpIntent = getIntent();

        String CharName = ExpIntent.getStringExtra("CharName");
        int CharLv = ExpIntent.getIntExtra("CharLv", 200);
        long CharExp = ExpIntent.getLongExtra("CharExp",0);
        float Reboot = ExpIntent.getFloatExtra("Reboot", 1);
        int Job = ExpIntent.getIntExtra("Job", 0);


        sqlDB = myDBHelper.getReadableDatabase();
        Cursor cur;
        cur = sqlDB.rawQuery("SELECT * FROM ExpTB WHERE CharName = '" + CharName +"';",null);
        cur.moveToFirst();
        for(int i = 0; i < ChkLink.length; i++) {
            if (cur.getInt(cur.getColumnIndex("Link" + i)) >= 1) {
                ChkLink[i].setChecked(true);
                ChkL[i] = cur.getInt(cur.getColumnIndex("Link" + i));
            }
        }
        for(int i = 0; i < EditExp.length; i++) {
            EditExp[i].setText(cur.getString(cur.getColumnIndex("Edit" + i)));
        }
        for(int i = 0; i < ChkExp.length; i++) {
            if (cur.getInt(cur.getColumnIndex("Exp" + i)) >= 1) {
                ChkExp[i].setChecked(true);
            }
        }
        Exp5 = cur.getInt(cur.getColumnIndex("Exp4"));
        cur.close();
        sqlDB.close();


        TableSkill.setVisibility(View.VISIBLE);

        //데몬일 경우
        if(Job == 1){
            JobExp = 20;
        }

        //해적일 경우
        if(Job == 2){
            JobExp = 30;
        }

        //나로일 경우
        if(Job == 3){
            JobExp = 10;
        }

        //비숍일 경우
        if(Job == 4){
            EditExp[3].setText("70");
            TableSkill.setVisibility(View.GONE);
        }

        //리부트는 제로 못해요.
        if(Reboot > 1){
            ChkLink[0].setVisibility(View.GONE);
        }

        // 맵, 몬스터 정보 불러오기
        ArrayAdapter<String> MapAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.listlayout, ArrayMap);
        SpinMap.setAdapter(MapAdapter);

        ArrayAdapter<String> MobAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.listlayout, ArrayMobName);
        
        SpinMap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),ArrayMap.get(i)+"(이)가 선택되었습니다.", Toast.LENGTH_SHORT).show();
                ArrayMobName.clear();
                ArrayMobName = ExpArr.ArrayMobName();

                del = 0;
                sav = 0;

                switch (i) {
                    case 0:
                        sav = 0;
                        del = 12;
                        break;

                    case 1:
                        sav = 13;
                        del = 26;
                        break;

                    case 2:
                        sav = 27;
                        del = 42;
                        break;

                    case 3:
                        sav = 43;
                        del = 57;
                        break;

                    case 4:
                        sav = 58;
                        del = 70;
                        break;

                    case 5:
                        sav = 71;
                        del = 81;
                        break;

                    case 6:
                        sav = 82;
                        del = 96;
                        break;

                    case 7:
                        sav = 97;
                        del = 106;
                        break;

                    case 8:
                        sav = 107;
                        del = 113;
                        break;

                    case 9:
                        sav = 114;
                        del = 119;
                        break;

                    case 10:
                        sav = 120;
                        del = 132;
                        break;

                    case 11:
                        sav = 133;
                        del = 137;
                        break;

                    case 12:
                        sav = 138;
                        del = 144;
                        break;

                    case 13:
                        sav = 145;
                        del = 150;
                        break;

                    case 14:
                        sav = 151;
                        del = 156;
                        break;


                }

                for(int j = (ArrayMobName.size() - 1) ; j >= 0; j--) {
                    if(sav <= j && del >= j) {

                    }else {
                        ArrayMobName.remove(j);
                    }
                }
                MobAdapter.clear();
                MobAdapter.addAll(ArrayMobName);

                SpinMob.setAdapter(MobAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpinMob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),ArrayMobName.get(i)+"(이)가 선택되었습니다.", Toast.LENGTH_SHORT).show();
                SelectMobName = ArrayMobName.get(i);
                SelectMobLv = Integer.parseInt(ArrayMobName.get(i).replaceAll("[^0-9]", ""));
                MobCount = sav + i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 경쿠 체크시
        ChkExp[4].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ExpActivity.this);
                    builder.setTitle("쿠폰을 선택해주세요.");
                    builder.setPositiveButton("1.5배", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Exp5 = 100;
                        }
                    });
                    builder.setNegativeButton("2배", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Exp5 = 200;

                        }
                    });
                    builder.setNeutralButton("3배", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Exp5 = 300;
                        }
                    });
                    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            ChkExp[4].setChecked(false);
                            Exp5 = 0;
                        }
                    });
                    builder.show();

                }else{
                    Exp5 = 0;
                }
            }
        });

        // 제로 링크
        ChkLink[0].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ExpActivity.this);
                    builder.setTitle("제로 유니온 카드를 선택해 주세요.");
                    builder.setPositiveButton("S", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ChkL[0] = 8;
                        }
                    });
                    builder.setNegativeButton("SS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ChkL[0] = 10;

                        }
                    });
                    builder.setNeutralButton("SSS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ChkL[0] = 12;
                        }
                    });
                    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            ChkLink[0].setChecked(false);
                            ChkL[0] = 0;
                        }
                    });
                    builder.show();

                }else{
                    ChkL[0] = 0;
                }
            }
        });

        // 에반 링크
        ChkLink[1].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ExpActivity.this);
                    builder.setTitle("에반 링크 레벨을 선택해 주세요.");
                    builder.setPositiveButton("1레벨", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ChkL[1] = 6;
                        }
                    });
                    builder.setNegativeButton("2레벨", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ChkL[1] = 10;

                        }
                    });

                    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            ChkLink[1].setChecked(false);
                            ChkL[1] = 0;
                        }
                    });

                    builder.show();

                }else{
                    ChkL[1] = 0;
                }
            }
        });

        // 메르 링크
        ChkLink[2].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ExpActivity.this);
                    builder.setTitle("메르세데스 링크 레벨을 선택해 주세요.");
                    builder.setPositiveButton("1레벨", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ChkL[2] = 10;
                        }
                    });
                    builder.setNegativeButton("2레벨", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ChkL[2] = 15;

                        }
                    });
                    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            ChkLink[2].setChecked(false);
                            ChkL[2] = 0;
                        }
                    });

                    builder.show();

                }else{
                    ChkL[2] = 0;
                }
            }
        });

        BtnExpRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpCount = 100;

                if(EditExp[0].getText().toString().equals("")) {
                    EditExp[0].setText("0");
                }

                if(EditExp[1].getText().toString().equals("")) {
                    EditExp[1].setText("0");
                }

                if(EditExp[2].getText().toString().equals("")) {
                    EditExp[2].setText("0");
                }

                if(EditExp[3].getText().toString().equals("")) {
                    EditExp[3].setText("0");
                }

                if(EditLv.getText().toString().equals("")) {
                    EditLv.setText("0");
                }

                if(EditExp[4].getText().toString().equals("")) {
                    EditExp[4].setText("0");
                }

                if(Integer.parseInt(EditExp[0].getText().toString()) > 10 || Integer.parseInt(EditExp[1].getText().toString()) > 15 ||
                        Integer.parseInt(EditExp[2].getText().toString()) > 40 || (Integer.parseInt(EditExp[3].getText().toString()) > 35 && Job < 4)
                        || Integer.parseInt(EditLv.getText().toString()) < CharLv || Integer.parseInt(EditExp[4].getText().toString()) <= 0
                        || Integer.parseInt(EditLv.getText().toString()) > 300){

                        //버닝, 하이퍼, 유니온, 쓸심
                        if (Integer.parseInt(EditExp[0].getText().toString()) > 10) {
                            Toast.makeText(ExpActivity.this, "버닝 레벨은 10 보다 높을수 없습니다.", Toast.LENGTH_SHORT).show();
                            EditExp[0].setText("10");
                        }
                        if (Integer.parseInt(EditExp[1].getText().toString()) > 15) {
                            Toast.makeText(ExpActivity.this, "하이퍼 스텟 레벨은 15 보다 높을수 없습니다.", Toast.LENGTH_SHORT).show();
                            EditExp[1].setText("15");
                        }
                        if (Integer.parseInt(EditExp[2].getText().toString()) > 40) {
                            Toast.makeText(ExpActivity.this, "유니온 점령 칸은 40 보다 높을수 없습니다.", Toast.LENGTH_SHORT).show();
                            EditExp[2].setText("40");
                        }
                        if (Integer.parseInt(EditExp[3].getText().toString()) > 35 && Job < 4) {
                            Toast.makeText(ExpActivity.this, "쓸만한 심볼 스킬은 35% 보다 높을수 없습니다.", Toast.LENGTH_SHORT).show();
                            EditExp[3].setText("35");
                        }

                        if (Integer.parseInt(EditLv.getText().toString()) < CharLv){
                            Toast.makeText(ExpActivity.this, "목표 레벨은 현재 캐릭터 레벨 보다 낮을수 없습니다.", Toast.LENGTH_SHORT).show();
                            int TmpLv = CharLv +1;
                            EditLv.setText(""+TmpLv);
                        }
                        if (Integer.parseInt(EditLv.getText().toString()) > 300) {
                            Toast.makeText(ExpActivity.this, "목표 레벨은 300 레벨 보다 높을수 없습니다.", Toast.LENGTH_SHORT).show();
                            EditLv.setText("300");
                        }

                        if (Integer.parseInt(EditExp[4].getText().toString()) <= 0){
                            Toast.makeText(ExpActivity.this, "5분당 마릿수는 0보다 낮을수 없습니다.", Toast.LENGTH_SHORT).show();
                            EditExp[4].setText("0");
                        }

                }else {
                    //제로, 에반, 메르
                    for(int i = 0; i < ChkL.length; i++){
                        ExpCount += ChkL[i];
                    }

                    // 버닝, 하이퍼, 유니온, 심볼
                    ExpCount += (Integer.parseInt(EditExp[0].getText().toString()) * 10) + Integer.parseInt(EditExp[3].getText().toString());;

                    for(int i = 0; i <= Integer.parseInt(EditExp[1].getText().toString()); i++){
                        if(i <= 10 && i%2 == 0){
                            ExpCount += 0.5;

                        }else{
                            ExpCount += 1;
                        }
                    }

                    for(int i = 0; i < Integer.parseInt(EditExp[2].getText().toString()); i++){
                        if(i%4 == 0) {
                            ExpCount += 1;
                        }
                    }

                    //경축비, 익골비, 정펜, 피시방, 경뿌
                    if (ChkExp[0].isChecked()) {
                        ExpCount += 10;
                    }
                    if (ChkExp[1].isChecked()) {
                        ExpCount += 10;
                    }
                    if (ChkExp[2].isChecked()) {
                        ExpCount += 30;
                    }
                    if (ChkExp[3].isChecked()) {
                        ExpCount += 30;
                    }
                    if (ChkExp[5].isChecked()) {
                        ExpCount += 50;
                    }

                    //경쿠
                    ExpCount += Exp5;

                    //직업 경험치
                    ExpCount += JobExp;

                    // 하얀 경험치
                    int Lv = CharLv - SelectMobLv;

                    if(Lv < -40){
                        Toast.makeText(ExpActivity.this, "몬스터 레벨이 캐릭터 레벨보다 \n 40 이상 높아 계산할 수가 없습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        ExpSw.ExpSwitch(Lv);

                        SelectMobExp = Integer.parseInt(ArrayMobExp.get(MobCount));

                        SelectMobExp = (int) (SelectMobExp / 100 * ExpSw.Value * Reboot);

                        //마릿수
                        int MobC = Integer.parseInt(EditExp[4].getText().toString());

                        //목표 레벨
                        int AimLv = Integer.parseInt(EditLv.getText().toString());

                        //남은 경험치
                        long NowExp = Long.parseLong(ArrayExp.get(CharLv -200)) - CharExp;

                        long AimExp = NowExp;
                        for (int i = CharLv + 1; i < AimLv; i++) {
                            AimExp += Long.parseLong(ArrayExp.get(i - 200));
                        }
                        sqlDB = myDBHelper.getWritableDatabase();


                        for(int i = 0; i < ChkE.length; i++){
                            if(ChkExp[i].isChecked()){
                                ChkE[i] = 1;
                            }
                        }

                        sqlDB.execSQL("UPDATE ExpTB SET Link0 = '" + ChkL[0] + "', Link1 = '" + ChkL[1]  + "', Link2 = '" + ChkL[2] +
                                "', Edit0 = '" + Integer.parseInt(EditExp[0].getText().toString()) + "', Edit1 = '" + Integer.parseInt(EditExp[1].getText().toString()) +
                                "', Edit2 = '" + Integer.parseInt(EditExp[2].getText().toString()) + "', Edit3 = '" + Integer.parseInt(EditExp[3].getText().toString()) +
                                "', Edit4 = '" + Integer.parseInt(EditExp[4].getText().toString()) + "', Exp0 = '" + ChkE[0] + "', Exp1 = '" + ChkE[1] +
                                "', Exp2 = '" + ChkE[2] + "', Exp3 = '" + ChkE[3] + "', Exp4 = '" + Exp5 + "', Exp5 = '" + ChkE[5] + "' WHERE CharName = '" + CharName + "';");


                        Intent ExpResIntent = new Intent(getApplicationContext(), ExpResultActivity.class);
                        ExpResIntent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        //캐릭터 이름, 현재 레벨, 현재 경험치, 목표 레벨, 고른 몬스터, 고른 몬스터 하얀 경험치, 목표 경험치량, 경험치 도핑, 마릿수
                        ExpResIntent.putExtra("CharName", CharName);
                        ExpResIntent.putExtra("CharLv", CharLv);
                        ExpResIntent.putExtra("CharExp", CharExp);
                        ExpResIntent.putExtra("AimLv", AimLv);
                        ExpResIntent.putExtra("SelectMobName", SelectMobName);
                        ExpResIntent.putExtra("SelectMobExp", SelectMobExp);
                        ExpResIntent.putExtra("AimExp", AimExp);
                        ExpResIntent.putExtra("ExpCount", ExpCount);
                        ExpResIntent.putExtra("MobC", MobC);

                        startActivity(ExpResIntent);


                    }

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