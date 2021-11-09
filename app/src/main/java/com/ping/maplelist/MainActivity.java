package com.ping.maplelist;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity  extends AppCompatActivity {
    private myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

    CharSwitch CharSwitch = new CharSwitch();
    CharId CharId = new CharId();

    int CharMax = CharId.ImgChoose().length;
    int CharCount = 0;
    int Char = 0;

    String[] CharName = new String[CharMax];

    int[] CharLv = new int[CharMax];
    String[] CharJob = new String[CharMax];
    String[] CharServer = new String[CharMax];

    long[] CharExp = new long[CharMax];
    String[] CharImg = new String[CharMax];

    String[] CharTxt = new String[CharMax];

    float[] Reboot = new float[CharMax];

    int[] Job = new int[CharMax];

    int SelectChar = 0;

    //모든 월드, 리부트, 리부트2, 오로라, 레드, 이노시스, 유니온, 스카니아, 루나, 제니스, 크로아, 베라, 엘리시움, 아케인, 노바
    String[] ImgUrl = {
            "https://ssl.nx.com/s2/game/maplestory/renewal/common/world_icon/icon_1.png",
            "https://ssl.nx.com/s2/game/maplestory/renewal/common/world_icon/icon_2.png",
            "https://ssl.nx.com/s2/game/maplestory/renewal/common/world_icon/icon_3.png",
            "https://ssl.nx.com/s2/game/maplestory/renewal/common/world_icon/icon_4.png",
            "https://ssl.nx.com/s2/game/maplestory/renewal/common/world_icon/icon_5.png",
            "https://ssl.nx.com/s2/game/maplestory/renewal/common/world_icon/icon_6.png",
            "https://ssl.nx.com/s2/game/maplestory/renewal/common/world_icon/icon_7.png",
            "https://ssl.nx.com/s2/game/maplestory/renewal/common/world_icon/icon_8.png",
            "https://ssl.nx.com/s2/game/maplestory/renewal/common/world_icon/icon_9.png",
            "https://ssl.nx.com/s2/game/maplestory/renewal/common/world_icon/icon_10.png",
            "https://ssl.nx.com/s2/game/maplestory/renewal/common/world_icon/icon_11.png",
            "https://ssl.nx.com/s2/game/maplestory/renewal/common/world_icon/icon_12.png",
            "https://ssl.nx.com/s2/game/maplestory/renewal/common/world_icon/icon_13.png",
            "https://ssl.nx.com/s2/game/maplestory/renewal/common/world_icon/icon_14.png",
            "https://ssl.nx.com/s2/game/maplestory/renewal/common/world_icon/icon_15.png",
    };

    Dialog SetDialog, VersionDialog, CalDialog, CharDialog, DestroyDialog, AskDialog;
    Button BtnLogin;
    ImageView ImgChar, ImgCharServer;
    TextView TxtCharInfo, TxtChar;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImgChar = (ImageView) findViewById(R.id.ImgChar);
        ImgCharServer = (ImageView) findViewById(R.id.ImgCharServer);
        TxtCharInfo = (TextView) findViewById(R.id.TxtCharInfo);
        TxtChar = (TextView) findViewById(R.id.TxtChar);
        Button BtnCal = (Button) findViewById(R.id.BtnCal);
        Button BtnDiary = (Button) findViewById(R.id.BtnDiary);

        ImageButton ImgSet = (ImageButton) findViewById(R.id.ImgSet);

        BtnLogin = (Button) findViewById(R.id.BtnLogin);

        myDBHelper = new myDBHelper(this);

        ImgChar.setImageResource(0);
        ImgChar.setVisibility(View.INVISIBLE);
        TxtCharInfo.setText("로그인 해주세요.");

        ImgCharServer.setImageResource(0);
        ImgCharServer.setVisibility(View.INVISIBLE);
        TxtChar.setText("");

        // 초기 데이터 불러오기
        for(int i = 0; i < CharMax; i++){
            CharLv[i] = 0;
        }

        sqlDB = myDBHelper.getReadableDatabase();
        Cursor cur;
        cur = sqlDB.rawQuery("SELECT * FROM SelectCharTB",null);
        if(cur.getCount() > 0){
            cur.moveToFirst();
            SelectChar = cur.getInt(cur.getColumnIndex("SelectChar"));
        }

        cur.close();
        sqlDB.close();


        sqlDB = myDBHelper.getReadableDatabase();
        cur = sqlDB.rawQuery("SELECT * FROM MaplelistTB",null);

        if(cur.getCount() > 0) {
            BtnLogin.setVisibility(View.GONE);
            Char = 0;
            while(cur.moveToNext()) {

                CharName[Char] = cur.getString(cur.getColumnIndex("CharName"));
                CharExp[Char] = Long.parseLong(cur.getString(cur.getColumnIndex("CharExp")).replaceAll("[^0-9]", ""));
                CharLv[Char] = Integer.parseInt(cur.getString(cur.getColumnIndex("CharInfo1")).replaceAll("[^0-9]", ""));

                CharJob[Char] = cur.getString(cur.getColumnIndex("CharInfo2"));
                CharServer[Char] = cur.getString(cur.getColumnIndex("CharInfo3"));
                CharImg[Char] = cur.getString(cur.getColumnIndex("CharImg"));

                if (CharJob[Char].equals("데몬슬레이어")) {
                    Job[Char] = 1;
                }
                if (CharJob[Char].equals("메카닉") || CharJob[Char].equals("스트라이커") || CharJob[Char].equals("제논") || CharJob[Char].equals("바이퍼") || CharJob[Char].equals("아크") ||
                        CharJob[Char].equals("엔젤릭버스터") || CharJob[Char].equals("은월") || CharJob[Char].equals("캐논슈터") || CharJob[Char].equals("캡틴")) {
                    Job[Char] = 2;
                }
                if (CharJob[Char].equals("나이트로드")) {
                    Job[Char] = 3;
                }
                if (CharJob[Char].equals("비숍")) {
                    Job[Char] = 4;
                }

                if (CharServer[Char].equals("리부트") || CharServer[Char].equals("리부트2")) {
                    Reboot[Char] = 2.3f;
                }else{
                    Reboot[Char] = 1;
                }


                CharTxt[Char] = "Lv." + CharLv[Char];
                CharTxt[Char] += "\n" + CharJob[Char];
                Char++;
            }
            cur.close();
            sqlDB.close();

            int tmp = 0;

            switch (CharServer[SelectChar]){
                case "리부트" :
                    tmp = 1;
                    break;

                case "리부트2" :
                    tmp = 2;
                    break;

                case "오로라" :
                    tmp = 3;
                    break;

                case "레드" :
                    tmp = 4;
                    break;

                case "이노시스" :
                    tmp = 5;
                    break;

                case "유니온" :
                    tmp = 6;
                    break;

                case "스카니아" :
                    tmp = 7;
                    break;

                case "루나" :
                    tmp = 8;
                    break;

                case "제니스" :
                    tmp = 9;
                    break;

                case "크로아" :
                    tmp = 10;
                    break;

                case "베라" :
                    tmp = 11;
                    break;

                case "엘리시움" :
                    tmp = 12;
                    break;

                case "아케인" :
                    tmp = 13;
                    break;

                case "노바" :
                    tmp = 14;
                    break;

                default:
                    tmp = 0;
                    break;
            }

            TxtChar.setText(CharName[SelectChar]);
            TxtCharInfo.setText(CharTxt[SelectChar]);

            ImgChar.setVisibility(View.VISIBLE);
            ImgCharServer.setVisibility(View.VISIBLE);
            Glide.with(this).load(ImgUrl[tmp]).into(ImgCharServer);
            Glide.with(this).load(CharImg[SelectChar]).into(ImgChar);

        }

        // 앱 버전 불러오기
        new Thread() {
            @Override
            public void run() {
                //초기화
                String url = "https://play.google.com/store/apps/details?id=com.ping.maplelist";
                String versionName = BuildConfig.VERSION_NAME;
                String VersionCheck = "";

                try {
                    Document doc = Jsoup.connect(url).get();

                    Elements FindVersions = doc.select("span[class=htlgb]");

                    for (Element FindVersion : FindVersions) {
                        if (FindVersion.text().contains("1.")) {
                            VersionCheck = FindVersion.text();
                            if(Float.parseFloat(VersionCheck) > Float.parseFloat(versionName)) {
                                Message msg = handler.obtainMessage();
                                handler.sendMessage(msg);
                            }
                            break;
                        }
                    }
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //이미지 클릭시 캐릭터 추가, 삭제
        ImgChar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRestart();
                // Dialog 초기화
                CharDialog = new Dialog(MainActivity.this);
                CharDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                CharDialog.setContentView(R.layout.charchange);
                CharDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                CharDial();
            }
        });

        //계산기 버튼 클릭시
        BtnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                }
                // Dialog 초기화
                CalDialog = new Dialog(MainActivity.this);
                CalDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                CalDialog.setContentView(R.layout.calculate);
                CalDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                CalDial(); // 아래 InfoDial 함수 호출
            }
        });

        //다이어리 버튼 클릭시
        BtnDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CharLv[SelectChar] == 0){
                    Toast.makeText(MainActivity.this, "로그인을 해주세요." , Toast.LENGTH_SHORT).show();
                }else if(CharLv[SelectChar] >= 140){

                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(MainActivity.this);
                    }
                        Intent DiaryIntent = new Intent(getApplicationContext(), DiaryActivity.class);
                        DiaryIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        DiaryIntent.putExtra("CharName", CharName[SelectChar]);
                        DiaryIntent.putExtra("CharLv", CharLv[SelectChar]);
                        startActivity(DiaryIntent);

                }else{
                    Toast.makeText(MainActivity.this, "140레벨 미만 캐릭터는 재화 다이어리를 사용하실수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 로그인 버튼 클릭시 Login액티비티 띄우기
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(getApplicationContext(), LoginActivity.class);

                LoginIntent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(LoginIntent);
            }
        });
        
        // 톱니바퀴 클릭시 설정 창 띄우기
        ImgSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Dialog 초기화
                SetDialog = new Dialog(MainActivity.this);
                SetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                SetDialog.setContentView(R.layout.setting);
                SetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                SettingDial(); // 아래 InfoDial 함수 호출

            }
        });

        // 애드몹 광고
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-5026314667463298/1525722821", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error

                        mInterstitialAd = null;
                    }
                });

    }
    
    @Override
    protected void onRestart() {
        super.onRestart();

        ImgChar.setImageResource(0);
        ImgChar.setVisibility(View.INVISIBLE);
        TxtCharInfo.setText("로그인 해주세요.");

        ImgCharServer.setImageResource(0);
        ImgCharServer.setVisibility(View.INVISIBLE);
        TxtChar.setText("");


        myDBHelper = new myDBHelper(this);
        
        // 데이터 불러오기
        for(int i = 0; i < CharMax; i++){
            CharLv[i] = 0;
        }

        sqlDB = myDBHelper.getReadableDatabase();
        Cursor cur;
        cur = sqlDB.rawQuery("SELECT * FROM SelectCharTB",null);

        if(cur.getCount() > 0){
            cur.moveToFirst();
            SelectChar = cur.getInt(cur.getColumnIndex("SelectChar"));
        }

        cur.close();
        sqlDB.close();

        sqlDB = myDBHelper.getReadableDatabase();
        cur = sqlDB.rawQuery("SELECT * FROM MaplelistTB",null);

        if(cur.getCount() > 0) {
            BtnLogin.setVisibility(View.GONE);

            Char = 0;
            while(cur.moveToNext()) {

                CharName[Char] = cur.getString(cur.getColumnIndex("CharName"));
                CharExp[Char] = Long.parseLong(cur.getString(cur.getColumnIndex("CharExp")).replaceAll("[^0-9]", ""));
                CharLv[Char] = Integer.parseInt(cur.getString(cur.getColumnIndex("CharInfo1")).replaceAll("[^0-9]", ""));

                CharJob[Char] = cur.getString(cur.getColumnIndex("CharInfo2"));
                CharServer[Char] = cur.getString(cur.getColumnIndex("CharInfo3"));
                CharImg[Char] = cur.getString(cur.getColumnIndex("CharImg"));

                if (CharJob[Char].equals("데몬슬레이어")) {
                    Job[Char] = 1;
                }
                if (CharJob[Char].equals("메카닉") || CharJob[Char].equals("스트라이커") || CharJob[Char].equals("제논") || CharJob[Char].equals("바이퍼") || CharJob[Char].equals("아크") ||
                        CharJob[Char].equals("엔젤릭버스터") || CharJob[Char].equals("은월") || CharJob[Char].equals("캐논슈터") || CharJob[Char].equals("캡틴")) {
                    Job[Char] = 2;
                }
                if (CharJob[Char].equals("나이트로드")) {
                    Job[Char] = 3;
                }
                if (CharJob[Char].equals("비숍")) {
                    Job[Char] = 4;
                }

                if (CharServer[Char].equals("리부트") || CharServer[Char].equals("리부트2")) {
                    Reboot[Char] = 2.3f;
                }else {
                    Reboot[Char] = 1;
                }


                CharTxt[Char] = "Lv." + CharLv[Char];
                CharTxt[Char] += "\n" + CharJob[Char];
                Char++;
            }
            cur.close();
            sqlDB.close();

            int tmp = 0;

            switch (CharServer[SelectChar]){
                case "리부트" :
                    tmp = 1;
                    break;

                case "리부트2" :
                    tmp = 2;
                    break;

                case "오로라" :
                    tmp = 3;
                    break;

                case "레드" :
                    tmp = 4;
                    break;

                case "이노시스" :
                    tmp = 5;
                    break;

                case "유니온" :
                    tmp = 6;
                    break;

                case "스카니아" :
                    tmp = 7;
                    break;

                case "루나" :
                    tmp = 8;
                    break;

                case "제니스" :
                    tmp = 9;
                    break;

                case "크로아" :
                    tmp = 10;
                    break;

                case "베라" :
                    tmp = 11;
                    break;

                case "엘리시움" :
                    tmp = 12;
                    break;

                case "아케인" :
                    tmp = 13;
                    break;

                case "노바" :
                    tmp = 14;
                    break;

                default:
                    tmp = 0;
                    break;
            }

            TxtChar.setText(CharName[SelectChar]);
            TxtCharInfo.setText(CharTxt[SelectChar]);

            ImgChar.setVisibility(View.VISIBLE);
            ImgCharServer.setVisibility(View.VISIBLE);
            Glide.with(this).load(ImgUrl[tmp]).into(ImgCharServer);
            Glide.with(this).load(CharImg[SelectChar]).into(ImgChar);

        }

        // 애드몹 광고
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-5026314667463298/1525722821", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error

                        mInterstitialAd = null;
                    }
                });

    }

    //버전 체크
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // Dialog 초기화
            VersionDialog = new Dialog(MainActivity.this);
            VersionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
            VersionDialog.setContentView(R.layout.customdial);
            VersionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            VersionDial(); // 아래 InfoDial 함수 호출
        }

    };

    // 톱니 바퀴 버튼 클릭 시
    public void SettingDial(){
        SetDialog.show();

        // 뒤로가기 버튼
        Button BtnBack = (Button) SetDialog.findViewById(R.id.BtnBack);

        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDialog.dismiss();
            }
        });

        // 공지사항 버튼
        Button BtnInfo = (Button) SetDialog.findViewById(R.id.BtnInfo);

        BtnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent InfoIntent = new Intent(getApplicationContext(), InfoActivity.class);
                InfoIntent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(InfoIntent);
                SetDialog.dismiss();
            }
        });

        // 로그아웃 버튼, 로그인 정보 버리기
        Button BtnRes = (Button) SetDialog.findViewById(R.id.BtnRes);

        BtnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnLogin.setVisibility(View.VISIBLE);
                sqlDB = myDBHelper.getWritableDatabase();
                myDBHelper.onUpgrade(sqlDB, 0, 1);
                sqlDB.close();
                ImgChar.setImageResource(0);
                ImgChar.setVisibility(View.INVISIBLE);
                TxtCharInfo.setText("로그인 해주세요.");

                onRestart();
                SetDialog.dismiss();

            }
        });

    }

    // 버전이 있을 시
    public void VersionDial(){
        VersionDialog.show();

        TextView TxtAsk = (TextView) VersionDialog.findViewById(R.id.TxtAsk);
        TxtAsk.setText("새로운 업데이트 버전이 있습니다.\n다운로드 받을까요?");

        Button BtnYes = (Button) VersionDialog.findViewById(R.id.BtnYes);

        Button BtnNo = (Button) VersionDialog.findViewById(R.id.BtnNo);

        BtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(Intent.ACTION_VIEW);
                update.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.ping.maplelist"));
                startActivity(update);

                VersionDialog.dismiss();
            }
        });

        BtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VersionDialog.dismiss();
            }
        });

    }

    // 계산기 버튼 클릭시
    public void CalDial(){
        CalDialog.show();

        Button BtnExp = (Button) CalDialog.findViewById(R.id.BtnExp);
        Button BtnSym = (Button) CalDialog.findViewById(R.id.BtnSym);
        Button BtnMvp = (Button) CalDialog.findViewById(R.id.BtnMvp);

        // 경험치 계산기 버튼 클릭시 Exp액티비티 띄우기
        BtnExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CharLv[SelectChar] == 0){
                    Toast.makeText(MainActivity.this, "로그인을 해주세요." , Toast.LENGTH_SHORT).show();
                }else if(CharLv[SelectChar] >= 200){

                    sqlDB = myDBHelper.getReadableDatabase();
                    Cursor cur;
                    cur = sqlDB.rawQuery("SELECT * FROM MaplelistTB",null);

                    if(cur.getCount() > 0) {

                        Intent ExpIntent = new Intent(getApplicationContext(), ExpActivity.class);
                        ExpIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        ExpIntent.putExtra("CharName" , CharName[SelectChar]);
                        ExpIntent.putExtra("CharLv" , CharLv[SelectChar]);
                        ExpIntent.putExtra("CharExp" , CharExp[SelectChar]);
                        ExpIntent.putExtra("Reboot" , Reboot[SelectChar]);
                        ExpIntent.putExtra("Job" , Job[SelectChar]);
                        startActivity(ExpIntent);
                        CalDialog.dismiss();
                    }

                }else{
                    Toast.makeText(MainActivity.this, "200레벨 미만 캐릭터는 경험치 계산기를 사용하실수 없습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // 심볼 계산기 버튼 클릭시 Symbol액티비티 띄우기
        BtnSym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CharLv[SelectChar] == 0){
                    Toast.makeText(MainActivity.this, "로그인을 해주세요." , Toast.LENGTH_SHORT).show();
                }else if(CharLv[SelectChar] >= 200){

                    sqlDB = myDBHelper.getReadableDatabase();
                    Cursor cur;
                    cur = sqlDB.rawQuery("SELECT * FROM MaplelistTB",null);

                    if(cur.getCount() > 0) {

                        Intent SymbolIntent = new Intent(getApplicationContext(), SymbolActivity.class);
                        SymbolIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        SymbolIntent.putExtra("CharName" , CharName[SelectChar]);
                        SymbolIntent.putExtra("CharLv" , CharLv[SelectChar]);
                        startActivity(SymbolIntent);
                        CalDialog.dismiss();
                    }

                }else{
                    Toast.makeText(MainActivity.this, "200레벨 미만 캐릭터는 심볼 계산기를 사용하실수 없습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Mvp 계산기 버튼 클릭시 Mvp액티비티 띄우기
        BtnMvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CharLv[SelectChar] == 0){
                    Toast.makeText(MainActivity.this, "로그인을 해주세요." , Toast.LENGTH_SHORT).show();
                }else if(CharLv[SelectChar] >= 61){

                    sqlDB = myDBHelper.getReadableDatabase();
                    Cursor cur;
                    cur = sqlDB.rawQuery("SELECT * FROM MaplelistTB",null);

                    if(cur.getCount() > 0) {
                        Intent MvpIntent = new Intent(getApplicationContext(), MvpActivity.class);
                        MvpIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(MvpIntent);
                        CalDialog.dismiss();
                    }

                }else{
                    Toast.makeText(MainActivity.this, "61레벨 미만 캐릭터는 Mvp계산기를 사용하실수 없습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // 캐릭터 이미지 클릭시
    public void CharDial(){
        CharDialog.show();


        // 이미지, 캐릭터 정보 불러오기
        sqlDB = myDBHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM MaplelistTB",null);
        CharCount = cursor.getCount();
        sqlDB.close();
        cursor.close();

        //ImgChoose, TxtChoose 불러오기
        int ImgChooseId[] = CharId.ImgChoose();
        int TxtChooseId[] = CharId.TxtChoose();

        ImageView[] ImgChoose = new ImageView[ImgChooseId.length];
        TextView[] TxtChoose = new TextView[TxtChooseId.length];
        for(int i = 0; i < ImgChooseId.length; i++ ){
            TxtChoose[i] = CharDialog.findViewById(TxtChooseId[i]);
            ImgChoose[i] = CharDialog.findViewById(ImgChooseId[i]);
            TxtChoose[i].setText("");
            ImgChoose[i].setImageResource(0);
            TxtChoose[i].setVisibility(View.VISIBLE);
            ImgChoose[i].setVisibility(View.VISIBLE);
        }

        // 카운트 제외한 나머지 안보이게
        for(int i = CharCount; i < ImgChoose.length; i++) {
            ImgChoose[i].setVisibility(View.GONE);
            TxtChoose[i].setVisibility(View.GONE);
        }

        for(int i = 0; i < CharCount; i++){
            TxtChoose[i].setText("Lv. " + CharLv[i] +" \n " + CharName[i]);
            Glide.with(this).load(CharImg[i]).into(ImgChoose[i]);

            ImgChoose[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CharSwitch.CharSwitch(v.getId());
                    sqlDB = myDBHelper.getWritableDatabase();
                    sqlDB.execSQL("DROP TABLE IF EXISTS SelectCharTB");
                    sqlDB.execSQL("CREATE TABLE SelectCharTB (SelectChar int default 0);");
                    sqlDB.execSQL("INSERT INTO SelectCharTB (SelectChar) VALUES('"+CharSwitch.Select+"')");
                    Intent LoginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    LoginIntent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    SelectChar = CharSwitch.Select;
                    LoginIntent.putExtra("CharName", CharName[SelectChar]);
                    LoginIntent.putExtra("Reboot", Reboot[SelectChar]);

                    startActivity(LoginIntent);

                    sqlDB.close();
                    onRestart();
                    CharDialog.dismiss();
                }
            });

        }

        Button BtnCreate = (Button) CharDialog.findViewById(R.id.BtnCreate);

        if(CharCount >= CharMax){
            BtnCreate.setVisibility(View.GONE);
        }else{
            BtnCreate.setVisibility(View.VISIBLE);
        }
        BtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent LoginIntent2 = new Intent(getApplicationContext(), LoginActivity.class);
                LoginIntent2.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);

                SelectChar = CharCount;
                LoginIntent2.putExtra("SelectChar", SelectChar);
                startActivity(LoginIntent2);
                CharDialog.dismiss();

            }
        });

        // 캐릭터 삭제
        Button BtnDestroy = (Button) CharDialog.findViewById(R.id.BtnDestroy);

        BtnDestroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DestroyDialog = new Dialog(MainActivity.this);
                DestroyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                DestroyDialog.setContentView(R.layout.chardestroy);
                DestroyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                DestroyDial();
                CharDialog.dismiss();
            }
        });

        Button BtnBac = (Button) CharDialog.findViewById(R.id.BtnBac);

        BtnBac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharDialog.dismiss();
            }
        });

    }
    
    // 캐릭터 삭제 버튼 클릭시
    public void DestroyDial(){
        DestroyDialog.show();

        // 이미지, 캐릭터 정보 불러오기
        sqlDB = myDBHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM MaplelistTB",null);
        CharCount = cursor.getCount();
        sqlDB.close();
        cursor.close();

        //ImgDestroy, TxtDestroy 불러오기

        int ImgDestroyId[] = CharId.ImgDestroy();
        int TxtDestroyId[] = CharId.TxtDestroy();

        ImageView[] ImgDestroy = new ImageView[ImgDestroyId.length];
        TextView[] TxtDestroy = new TextView[TxtDestroyId.length];
        for(int i = 0; i < ImgDestroyId.length; i++ ){
            TxtDestroy[i] = DestroyDialog.findViewById(TxtDestroyId[i]);
            ImgDestroy[i] = DestroyDialog.findViewById(ImgDestroyId[i]);
            TxtDestroy[i].setText("");
            ImgDestroy[i].setImageResource(0);
            TxtDestroy[i].setVisibility(View.VISIBLE);
            ImgDestroy[i].setVisibility(View.VISIBLE);
        }

        // 카운트 제외한 나머지 안보이게
        for(int i = CharCount; i < ImgDestroy.length; i++){
            TxtDestroy[i].setVisibility(View.GONE);
            ImgDestroy[i].setVisibility(View.GONE);
        }

        for(int i = 0; i < CharCount; i++){
            TxtDestroy[i].setText("Lv. " + CharLv[i] +" \n " + CharName[i]);
            Glide.with(this).load(CharImg[i]).into(ImgDestroy[i]);

            ImgDestroy[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CharSwitch.CharDestroy(v.getId());
                    if(CharSwitch.Destroy >= 0){

                        AskDialog = new Dialog(MainActivity.this);
                        AskDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                        AskDialog.setContentView(R.layout.customdial);
                        AskDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        AskDial();
                        DestroyDialog.dismiss();
                    }

                }
            });
        }

        // 뒤로가기 버튼
        Button BtnBac2 = (Button) DestroyDialog.findViewById(R.id.BtnBac2);

        BtnBac2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharDial();
                DestroyDialog.dismiss();
            }
        });

    }

    // 삭제 버튼 한번 더 물어보기
    public void AskDial() {
        AskDialog.show();

        TextView TxtAsk = (TextView) AskDialog.findViewById(R.id.TxtAsk);
        TxtAsk.setText("정말 삭제할까요?");

        Button BtnYes = (Button) AskDialog.findViewById(R.id.BtnYes);

        Button BtnNo = (Button) AskDialog.findViewById(R.id.BtnNo);

        BtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myDBHelper.getWritableDatabase();
                sqlDB.execSQL("DELETE FROM MaplelistTB WHERE CharName = '"+ CharName[CharSwitch.Destroy]+"'");
                sqlDB.execSQL("DELETE FROM SymbolTB WHERE CharName = '"+ CharName[CharSwitch.Destroy]+"'");
                sqlDB.execSQL("DELETE FROM ExpTB WHERE CharName = '"+ CharName[CharSwitch.Destroy]+"'");
                sqlDB.close();
                if(SelectChar == 0){
                    BtnLogin.setVisibility(View.VISIBLE);
                    ImgChar.setImageResource(0);
                    ImgChar.setVisibility(View.INVISIBLE);
                }

                if(SelectChar > CharSwitch.Destroy){
                    SelectChar--;
                }else if(SelectChar < CharSwitch.Destroy){

                }else{
                    SelectChar = 0;
                }
                sqlDB = myDBHelper.getWritableDatabase();
                sqlDB.execSQL("DROP TABLE IF EXISTS SelectCharTB");
                sqlDB.execSQL("CREATE TABLE SelectCharTB (SelectChar int default 0);");
                sqlDB.execSQL("INSERT INTO SelectCharTB (SelectChar) VALUES('"+SelectChar+"')");
                sqlDB.close();

                sqlDB = myDBHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM MaplelistTB",null);
                CharCount = cursor.getCount();
                cursor.close();
                onRestart();

                if(CharCount != 0) {
                    DestroyDial();
                }

                AskDialog.dismiss();
            }
        });

        BtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DestroyDial();
                AskDialog.dismiss();
            }
        });

    }
}
