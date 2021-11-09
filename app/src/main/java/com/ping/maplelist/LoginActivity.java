package com.ping.maplelist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText EditChar = (EditText) findViewById(R.id.EditChar);
        Button BtnStart = (Button) findViewById(R.id.BtnStart);
        CheckBox ChkReboot = (CheckBox) findViewById(R.id.ChkReboot);
        LinearLayout LayLog = (LinearLayout) findViewById(R.id.LayLog);

        LayLog.setVisibility(View.VISIBLE);

        final Bundle bundle = new Bundle();

        myDBHelper = new myDBHelper(this);

        Intent LoginIntent = getIntent();

        EditChar.setText(LoginIntent.getStringExtra("CharName"));
        int SelectChar = LoginIntent.getIntExtra("SelectChar",0);
        float reb = LoginIntent.getFloatExtra("Reboot", 1);
        if(reb > 1){
            ChkReboot.setChecked(true);
        }

        if(EditChar.getText().length()> 0) {

            LayLog.setVisibility(View.GONE);

            new Thread() {
                @Override
                public void run() {
                    //초기화
                    String CharImg ="";
                    String [] CharInfo = new String[3];
                    String  urlAdd = "";
                    String  CharExp = "";
                    String Baseurl = "https://maplestory.nexon.com";
                    String CharName = EditChar.getText().toString();
                    String ChkReb = "0";
                    if(ChkReboot.isChecked()){
                        ChkReb = "254";
                    }
                    String url = "https://maplestory.nexon.com/Ranking/World/Total?c="+ CharName + "&w=" + ChkReb;
                    String InfoTmp = "";
                    int count = 0;

                    try {
                        Document doc = Jsoup.connect(url).get();

                        Elements FindChars = doc.select("a[href]");

                        for (Element FindChar : FindChars) {
                            if (FindChar.text().equals(CharName)) {
                                urlAdd = FindChar.attr("href");
                            }
                        }

                        if (urlAdd.equals("") || (urlAdd.contains("#a"))) {

                            Message msg = handler.obtainMessage();
                            handler.sendMessage(msg);

                        } else {

                            Baseurl += urlAdd;

                            doc = Jsoup.connect(Baseurl).get();

                            Elements FindCharImgs = doc.select("div[class=char_img] img");
                            for (Element FindCharImg : FindCharImgs) {
                                CharImg = FindCharImg.attr("src");
                            }

                            Elements FindInfos = doc.select("div[class=char_info] dl dd");
                            for (Element FindInfo : FindInfos){
                                InfoTmp = FindInfo.text();
                                if(count == 1) {
                                    int idx = InfoTmp.indexOf("/");
                                    InfoTmp = InfoTmp.substring(idx + 1);
                                }
                                CharInfo[count] = InfoTmp;
                                count++;
                            }

                            Elements FindExps = doc.select("div[class=level_data] span");
                            for (Element FindExp : FindExps){
                                CharExp = FindExp.text();
                                break;
                            }
                            if(CharName.equals("핑모") || CharName.equals("뿜푸큐")){
                                CharInfo[1] = "개발자";
                            }

                            sqlDB = myDBHelper.getReadableDatabase();
                            Cursor cursor;
                            cursor = sqlDB.rawQuery("SELECT * FROM MaplelistTB WHERE CharName = '"+ CharName+"'",null);
                            if(cursor.getCount() > 0)
                            {
                                sqlDB = myDBHelper.getWritableDatabase();
                                sqlDB.execSQL("UPDATE MaplelistTB SET CharName = '"+ CharName +"', CharInfo1 = '"+ CharInfo[0] +"', CharInfo2 = '" + CharInfo[1] +"', CharInfo3 = '" + CharInfo[2] + "' , CharImg = '"  + CharImg + "' , CharExp = '" + CharExp + "' WHERE CharName = '"+ CharName +"';");
                            }
                            else
                            {
                                sqlDB = myDBHelper.getWritableDatabase();
                                sqlDB.execSQL("INSERT INTO MaplelistTB (CharName, CharInfo1, CharInfo2, CharInfo3, CharImg , CharExp) VALUES ( '"+ CharName + "' , '" + CharInfo[0] + "', '" + CharInfo[1] + "', '" + CharInfo[2] + "', '"  + CharImg + "', '" +  CharExp + "');");
                                sqlDB.execSQL("INSERT INTO SymbolTB (CharName) VALUES ( '"+ CharName + "');");
                                sqlDB.execSQL("INSERT INTO ExpTB (CharName) VALUES('"+ CharName +"')");

                                sqlDB.execSQL("DROP TABLE IF EXISTS SelectCharTB");
                                sqlDB.execSQL("CREATE TABLE SelectCharTB (SelectChar int default 0);");
                                sqlDB.execSQL("INSERT INTO SelectCharTB (SelectChar) VALUES('"+SelectChar+"')");

                            }
                            cursor.close();
                            sqlDB.close();

                            finish();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }

        BtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EditChar.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    new Thread() {
                        @Override
                        public void run() {
                            //초기화
                            String CharImg ="";
                            String [] CharInfo = new String[3];
                            String  urlAdd = "";
                            String  CharExp = "";
                            String Baseurl = "https://maplestory.nexon.com";
                            String CharName = EditChar.getText().toString();
                            String ChkReb = "0";
                            if(ChkReboot.isChecked()){
                                ChkReb = "254";
                            }
                            String url = "https://maplestory.nexon.com/Ranking/World/Total?c="+ CharName + "&w=" + ChkReb;
                            String InfoTmp = "";
                            int count = 0;

                            try {
                                Document doc = Jsoup.connect(url).get();

                                Elements FindChars = doc.select("a[href]");

                                for (Element FindChar : FindChars) {
                                    if (FindChar.text().equals(CharName)) {
                                        urlAdd = FindChar.attr("href");
                                    }
                                }

                                if (urlAdd.equals("") || (urlAdd.contains("#a"))) {

                                    Message msg = handler.obtainMessage();
                                    handler.sendMessage(msg);

                                } else {
                                    Baseurl += urlAdd;

                                    doc = Jsoup.connect(Baseurl).get();

                                    Elements FindCharImgs = doc.select("div[class=char_img] img");
                                    for (Element FindCharImg : FindCharImgs) {
                                        CharImg = FindCharImg.attr("src");
                                    }

                                    Elements FindInfos = doc.select("div[class=char_info] dl dd");
                                    for (Element FindInfo : FindInfos){
                                        InfoTmp = FindInfo.text();
                                        if(count == 1) {
                                            int idx = InfoTmp.indexOf("/");
                                            InfoTmp = InfoTmp.substring(idx + 1);
                                        }
                                        CharInfo[count] = InfoTmp;
                                        count++;
                                    }

                                    Elements FindExps = doc.select("div[class=level_data] span");
                                    for (Element FindExp : FindExps){
                                        CharExp = FindExp.text();
                                        break;
                                    }
                                    if(CharName.equals("핑모") || CharName.equals("뿜푸큐")){
                                        CharInfo[1] = "개발자";
                                    }

                                    sqlDB = myDBHelper.getReadableDatabase();
                                    Cursor cursor;
                                    cursor = sqlDB.rawQuery("SELECT * FROM MaplelistTB WHERE CharName = '"+ CharName+"'",null);
                                    if(cursor.getCount() > 0)
                                    {
                                        sqlDB = myDBHelper.getWritableDatabase();
                                        sqlDB.execSQL("UPDATE MaplelistTB SET CharName = '"+ CharName +"', CharInfo1 = '"+ CharInfo[0] +"', CharInfo2 = '" + CharInfo[1] +"', CharInfo3 = '" + CharInfo[2] + "' , CharImg = '"  + CharImg + "' , CharExp = '" + CharExp + "' WHERE CharName = '"+ CharName +"';");
                                    }
                                    else
                                    {
                                        sqlDB = myDBHelper.getWritableDatabase();
                                        sqlDB.execSQL("INSERT INTO MaplelistTB (CharName, CharInfo1, CharInfo2, CharInfo3, CharImg , CharExp) VALUES ( '"+ CharName + "' , '" + CharInfo[0] + "', '" + CharInfo[1] + "', '" + CharInfo[2] + "', '"  + CharImg + "', '" +  CharExp + "');");
                                        sqlDB.execSQL("INSERT INTO SymbolTB (CharName) VALUES ( '"+ CharName + "');");
                                        sqlDB.execSQL("INSERT INTO ExpTB (CharName) VALUES('"+ CharName +"')");

                                        Intent Login2Intent = getIntent();
                                        int tmp = Login2Intent.getIntExtra("SelectChar", 0);

                                        sqlDB.execSQL("DROP TABLE IF EXISTS SelectCharTB");
                                        sqlDB.execSQL("CREATE TABLE SelectCharTB (SelectChar int default 0);");
                                        sqlDB.execSQL("INSERT INTO SelectCharTB (SelectChar) VALUES('"+tmp+"')");


                                    }
                                    cursor.close();
                                    sqlDB.close();

                                    finish();
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                }
            }
        });

    }



    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(LoginActivity.this, "캐릭터 이름을 확인해 주세요.", Toast.LENGTH_SHORT).show();
            //이런식으로 View를 메인 쓰레드에서 뿌려줘야한다.

        }


    };

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);

    }
}
