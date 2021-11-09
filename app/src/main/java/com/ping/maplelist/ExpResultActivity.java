package com.ping.maplelist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExpResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_res);

        TextView TxtExpRes = (TextView) findViewById(R.id.TxtExpRes);

        Intent ExpResIntent = getIntent();


        //캐릭터 이름, 현재 레벨, 현재 경험치, 목표 레벨, 고른 몬스터, 고른 몬스터 하얀 경험치, 목표 경험치량, 경험치 도핑, 마릿수

        String CharName = ExpResIntent.getStringExtra("CharName");
        int CharLv = ExpResIntent.getIntExtra("CharLv", 200);
        long CharExp = ExpResIntent.getLongExtra("CharExp", 0);
        int AimLv = ExpResIntent.getIntExtra("AimLv", 200);
        String SelectMobName = ExpResIntent.getStringExtra("SelectMobName");
        int SelectMobExp = ExpResIntent.getIntExtra("SelectMobExp", 0);
        long AimExp = ExpResIntent.getLongExtra("AimExp", 0);
        int ExpCount = ExpResIntent.getIntExtra("ExpCount", 100);
        int MobC = ExpResIntent.getIntExtra("MobC",1);

        //계산
        // 1시간
        long ExpHour = SelectMobExp/ 10000;

        ExpHour = ExpHour * MobC *12;

        ExpHour = ExpHour * ExpCount / 100;

        AimExp = AimExp / 10000;

        StringBuffer sb = new StringBuffer();

        String Mill = "만";
        String Mill2 = "조";

        String ExpH = Long.toString(ExpHour);
        String AimE = Long.toString(AimExp);

        if(ExpHour >= 10000 && AimExp >= 10000) {
            ExpHour = ExpHour / 10000;
            AimExp = AimExp / 10000;
            Mill = "억";

            ExpH = Long.toString(ExpHour);
            AimE = Long.toString(AimExp);

            if (Integer.parseInt(AimE) > 10000) {
                sb.append(AimE);
                sb.insert(AimE.length() - 4, Mill2);
                AimE = sb.toString();
                sb.delete(0, sb.length());
            }
        }

        // 남은 시간
        long ResHour = 0;
        if (ExpHour < AimExp && ExpHour > 0){
            ResHour = AimExp / ExpHour;
        }
        
        // 경험치 도핑
        ExpCount = ExpCount -100;

        // 몬스터 이름
        int idx = SelectMobName.indexOf("/");
        SelectMobName = SelectMobName.substring(idx + 1);

        //현재 경험치
        String CharM = "만";
        String CharM2 = "조";
        CharExp = CharExp / 10000;
        
        String CharE = Long.toString(CharExp);

        if(CharExp >= 10000){
            CharExp = CharExp / 10000;
            CharM = "억";
            CharE = Long.toString(CharExp);
            
            if(Integer.parseInt(CharE) >= 10000){
                sb.append(CharE);
                sb.insert(CharE.length() - 4, CharM2);
                CharE = sb.toString();
                sb.delete(0, sb.length());
            }
            
        }


        TxtExpRes.setText("" + CharName + " / Lv." + CharLv + "\n\n현재 경험치\n"+CharE + CharM+ "\n\n목표 레벨\nLv." + AimLv + "\n\n몬스터 이름\n" + SelectMobName + "\n\n몬스터 경험치\n" + SelectMobExp +
                "\n\n경험치 도핑\n"+ExpCount+"%\n\n목표 레벨까지 남은 경험치\n" + AimE + Mill +"\n\n시간당 경험치 량\n" + ExpH + Mill +"\n\n총 필요 시간\n" + ResHour +"시간");
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);

    }

}
