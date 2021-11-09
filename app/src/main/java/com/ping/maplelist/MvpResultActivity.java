package com.ping.maplelist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MvpResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_res);

        TextView TxtMvpRes = (TextView) findViewById(R.id.TxtMvpRes);

        Intent MvpResIntent = getIntent();


        // 아이템 메소, 아이템 캐시, 메소 시세

        float[] Meso = MvpResIntent.getFloatArrayExtra("Meso");
        float[] Cash = MvpResIntent.getFloatArrayExtra("Cash");
        float MesoPrice = MvpResIntent.getFloatExtra("MesoPrice", 0);

        float Moi = 10000 / MesoPrice;
        float[] Res = new float[10];
        float[] Moy = new float[10];
        float Max = 0;
        int Count = 0;
        String Txt = "";

        for(int i = 0; i < Moy.length; i++){

            if(Meso[i] > 0 && Cash[i] > 0) {
                Moy[i] = Meso[i] / Cash[i];
                Res[i] = Moy[i] / Moi * 100;
                if (Res[i] > Max) {
                    Max = Res[i];
                    Count = i;
                }
            }
        }
        int Tmp  = Count + 1;

        Txt += "현재 메소 시세 : " + Math.round(MesoPrice) + "원\n\n";
        Txt += "가장 회수율이 높은 아이템 :\n" + Tmp + "번째 아이템\n\n";
        Txt += "회수율 : " + String.format("%.2f", Res[Count]) + "%\n\n";

        Tmp = 1;
        for(int i = 0; i < Res.length; i++){
            if(Meso[i] > 0 && Cash[i] > 0) {
                Txt += "" + Tmp + "번째 아이템의 회수율 : " + String.format("%.2f", Res[i]) + "%\n\n";
            }
            Tmp++;
        }
        
        TxtMvpRes.setText(Txt);

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);

    }

}
