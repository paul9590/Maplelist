package com.ping.maplelist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class SymbolResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symbol_res);

        TextView TxtSymRes = (TextView) findViewById(R.id.TxtSymRes);

        Intent SymbolResIntent = getIntent();

        TableRow[] TbSymRes = {
                findViewById(R.id.TbSymRes1),
                findViewById(R.id.TbSymRes2),
                findViewById(R.id.TbSymRes3),
                findViewById(R.id.TbSymRes4),
                findViewById(R.id.TbSymRes5),
                findViewById(R.id.TbSymRes6),
                findViewById(R.id.TbSymRes7),
                findViewById(R.id.TbSymRes8),
        };
        TableRow[] TbSymTxt = {
                findViewById(R.id.TbSymTxt1),
                findViewById(R.id.TbSymTxt2),
                findViewById(R.id.TbSymTxt3),
                findViewById(R.id.TbSymTxt4),
                findViewById(R.id.TbSymTxt5),
                findViewById(R.id.TbSymTxt6),
                findViewById(R.id.TbSymTxt7),
                findViewById(R.id.TbSymTxt8),
        };

        TableRow[] TbSymMoney = {
                findViewById(R.id.TbSymMoney1),
                findViewById(R.id.TbSymMoney2),
                findViewById(R.id.TbSymMoney3),
                findViewById(R.id.TbSymMoney4),
                findViewById(R.id.TbSymMoney5),
                findViewById(R.id.TbSymMoney6),
                findViewById(R.id.TbSymMoney7),
                findViewById(R.id.TbSymMoney8),
        };

        ImageView[] ImgSym = {
                findViewById(R.id.ImgSym1),
                findViewById(R.id.ImgSym2),
                findViewById(R.id.ImgSym3),
                findViewById(R.id.ImgSym4),
                findViewById(R.id.ImgSym5),
                findViewById(R.id.ImgSym6),
                findViewById(R.id.ImgSym7),
                findViewById(R.id.ImgSym8),
        };

        TextView[] TxtSymDay = {
                findViewById(R.id.TxtSymDay1),
                findViewById(R.id.TxtSymDay2),
                findViewById(R.id.TxtSymDay3),
                findViewById(R.id.TxtSymDay4),
                findViewById(R.id.TxtSymDay5),
                findViewById(R.id.TxtSymDay6),
                findViewById(R.id.TxtSymDay7),
                findViewById(R.id.TxtSymDay8),
        };

        TextView[] TxtSymMoney = {
                findViewById(R.id.TxtSymMoney1),
                findViewById(R.id.TxtSymMoney2),
                findViewById(R.id.TxtSymMoney3),
                findViewById(R.id.TxtSymMoney4),
                findViewById(R.id.TxtSymMoney5),
                findViewById(R.id.TxtSymMoney6),
                findViewById(R.id.TxtSymMoney7),
                findViewById(R.id.TxtSymMoney8),
        };

        String[] ImgUrl = {
                "https://avatar.maplestory.nexon.com/ItemIcon/KEIDJHOA.png",
                "https://avatar.maplestory.nexon.com/ItemIcon/KEIDJHOD.png",
                "https://avatar.maplestory.nexon.com/ItemIcon/KEIDJHOC.png",
                "https://avatar.maplestory.nexon.com/ItemIcon/KEIDJHOF.png",
                "https://avatar.maplestory.nexon.com/ItemIcon/KEIDJHOE.png",
                "https://avatar.maplestory.nexon.com/ItemIcon/KEIDJHOH.png",
                "http://pingmo.co.kr/img/symbol6.png",
                "http://pingmo.co.kr/img/symbol7.png",
        };

        //캐릭터 이름, 레벨, 카운트, 심볼 현재 레벨, 심볼 하루 갯수, 만렙까지 필요 심볼, 만렙까지 필요한 돈

        String CharName = SymbolResIntent.getStringExtra("CharName");
        int CharLv = SymbolResIntent.getIntExtra("CharLv", 200);
        int Lv = SymbolResIntent.getIntExtra("Lv", 1);
        int[] Symbol = SymbolResIntent.getIntArrayExtra("Symbol");
        int[] Sym = SymbolResIntent.getIntArrayExtra("Sym");
        int[] NeedSym = SymbolResIntent.getIntArrayExtra("NeedSym");
        long[] SymMoney = SymbolResIntent.getLongArrayExtra("SymMoney");

        for(int i = 0; i < 8; i++){
            TbSymRes[i].setVisibility(View.GONE);
            TbSymTxt[i].setVisibility(View.GONE);
            TbSymMoney[i].setVisibility(View.GONE);

        }


        int max = 0;
        int [] Day = new int[Lv];
        for(int i = 0; i < Lv; i++){
            Day[i] = NeedSym[i] / Sym[i];
        }

        String Txt = CharName + " / Lv." + CharLv + "\n\n";
        long Money = 0;
        String[] SymName = {"여로", "츄츄", "레헬른", "아르카나", "모라스", "에스페라", "세르니움", "아르크스"};
        int tmp = 0;

        StringBuffer sb = new StringBuffer();

        String Mill = "만";
        String Mill2 = "억";
        int[] MaxSymLv = {20,20,20,20,20,20,11,11};


        for(int i = 0; i < Lv; i++){
            if(Symbol[i] == MaxSymLv[i]) {
                TbSymRes[i].setVisibility(View.GONE);
                TbSymTxt[i].setVisibility(View.GONE);
                TbSymMoney[i].setVisibility(View.GONE);
            }else {
                TbSymRes[i].setVisibility(View.VISIBLE);
                TbSymTxt[i].setVisibility(View.VISIBLE);
                TbSymMoney[i].setVisibility(View.VISIBLE);

                Glide.with(this).load(ImgUrl[i]).into(ImgSym[i]);
                Money += SymMoney[i];

                SymMoney[i] = SymMoney[i] / 10000;
                String SymMon = Long.toString(SymMoney[i]);

                if (Integer.parseInt(SymMon) > 10000) {
                    sb.append(SymMon);
                    sb.insert(SymMon.length() - 4, Mill2);
                    SymMon = sb.toString();
                    sb.delete(0, sb.length());
                }
                if(Day[i] <= 0){
                    Day[i] = 0;
                }
                TxtSymDay[i].setText("" + Day[i] + "일");
                TxtSymMoney[i].setText(SymMon + Mill);

                if (Day[i] > max) {
                    max = Day[i];
                    tmp = i;
                }
            }
        }


        Money = Money / 10000;
        String MoneyStr = Long.toString(Money);

        if (Integer.parseInt(MoneyStr) > 10000) {
            sb.append(MoneyStr);
            sb.insert(MoneyStr.length() - 4, Mill2);
            MoneyStr = sb.toString();
            sb.delete(0, sb.length());
        }


        Txt += "전체 만렙까지 총 필요한 돈\n" + MoneyStr + Mill;
        Txt += "\n\n제일 오래 걸리는 심볼\n"+SymName[tmp] + " 심볼\n\n전체 만렙까지 남은 날짜\n" + max + "일";

        TxtSymRes.setText(Txt);


    }


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);

    }
}
