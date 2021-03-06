package com.ping.maplelist;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class DiaryActivity extends AppCompatActivity {
    int SelectedYear, SelectedMonth, SelectedDay;
    int Mesoencome, coreGem,Mesocost,addtionalcube;
    String date="", Month = "";
    Dialog calenderDialog, AskDialog;
    String encome="",cost="",gem="",cube="",etc="";
    int Totalgem=0,Totalcube=0;
    float Totalencome=0, Totalcost=0;

    TextView TxtCal;
    private myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        CalendarView cal1;
        myDBHelper = new myDBHelper(this);

        cal1 = (CalendarView)findViewById(R.id.cal1);
        TxtCal = (TextView)findViewById(R.id.TxtCal);

        Calendar time = Calendar.getInstance();
        int year = time.get(Calendar.YEAR);
        int month = time.get(Calendar.MONTH) + 1;

        Month = year+"_"+month+"_";

        sqlDB = myDBHelper.getReadableDatabase();
        Cursor cus;
        cus = sqlDB.rawQuery("SELECT * FROM CalenderTB WHERE date LIKE 'a" + Month + "%' ",null);
        while (cus.moveToNext())
        {
            Totalencome += Float.parseFloat(cus.getString(cus.getColumnIndex("MesoEncome")));
            Totalgem += Integer.parseInt(cus.getString(cus.getColumnIndex("GemStone")));
            Totalcost += Float.parseFloat(cus.getString(cus.getColumnIndex("MesoCost")));
            Totalcube += Integer.parseInt(cus.getString(cus.getColumnIndex("addtionalCube")));
        }

        cus.close();
        sqlDB.close();
        float TotalEn = Totalencome / 10000;
        float TotalCo = Totalcost / 10000;
        float tmp = TotalEn - TotalCo;

        String Txt = ""+ year + "??? " + month + "??? ??? ??????\n\n";
        Txt += "??? ?????? ?????? : " + String.format("%.2f", TotalEn) + "??? ??????\n??? ?????? ?????? : " + String.format("%.2f", TotalCo) + "??? ??????\n?????? ?????? ????????? : " + Totalgem + "???\n?????? ????????? : " + Totalcube + "???\n??? ?????? ?????? : " + String.format("%.2f", tmp) + "??? ??????";

        TxtCal.setText(Txt);


        cal1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                SelectedYear = year;
                SelectedMonth = month + 1;
                SelectedDay = dayOfMonth;

                date = SelectedYear+"_"+SelectedMonth+"_"+SelectedDay;

                Month = SelectedYear+"_"+SelectedMonth+"_";

                calenderDialog = new Dialog(DiaryActivity.this);
                calenderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
                calenderDialog.setContentView(R.layout.activity_calendarform);
                calenderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                onRestart();
                setCalenderDialog(calenderDialog);


            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Totalencome = 0;
        Totalcost = 0;
        Totalgem = 0;
        Totalcube = 0;
        sqlDB = myDBHelper.getReadableDatabase();
        Cursor cus;
        cus = sqlDB.rawQuery("SELECT * FROM CalenderTB WHERE date LIKE 'a" + Month + "%' ",null);
        while (cus.moveToNext())
        {
            Totalencome += Float.parseFloat(cus.getString(cus.getColumnIndex("MesoEncome")));
            Totalgem += Integer.parseInt(cus.getString(cus.getColumnIndex("GemStone")));
            Totalcost += Float.parseFloat(cus.getString(cus.getColumnIndex("MesoCost")));
            Totalcube += Integer.parseInt(cus.getString(cus.getColumnIndex("addtionalCube")));
      }

        cus.close();
        sqlDB.close();
        float TotalEn = Totalencome / 10000;
        float TotalCo = Totalcost / 10000;
        float tmp = TotalEn - TotalCo;

        String Txt = ""+ SelectedYear + "??? " + SelectedMonth + "??? ??? ??????\n\n";
        Txt += "??? ?????? ?????? : " + String.format("%.2f", TotalEn) + "??? ??????\n??? ?????? ?????? : " + String.format("%.2f", TotalCo) + "??? ??????\n?????? ?????? ????????? : " + Totalgem + "???\n?????? ????????? : " + Totalcube + "???\n??? ?????? ?????? : " + String.format("%.2f", tmp) + "??? ??????";

        TxtCal.setText(Txt);

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);

    }

    public void setCalenderDialog(Dialog calenderDialog) {
        calenderDialog.show();

        Button calenderSave = (Button)calenderDialog.findViewById(R.id.calenderSave);
        Button calenderDelete = (Button)calenderDialog.findViewById(R.id.calenderDelete);
        Button calenderBack = (Button)calenderDialog.findViewById(R.id.calenderBack);

        EditText MesoEncome, coreGemStone, etcEncome, MesoCost,addtionalCube;
        MesoEncome = (EditText)calenderDialog.findViewById(R.id.MesoEncome);
        coreGemStone = (EditText)calenderDialog.findViewById(R.id.coreGemStone);
        etcEncome = (EditText)calenderDialog.findViewById(R.id.etcEncome);
        MesoCost = (EditText)calenderDialog.findViewById(R.id.MesoCost);
        addtionalCube = (EditText)calenderDialog.findViewById(R.id.addtionalCube);


        sqlDB = myDBHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM CalenderTB WHERE date = 'a"+date+"' ",null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            encome = cursor.getString(cursor.getColumnIndex("MesoEncome"));
            gem = cursor.getString(cursor.getColumnIndex("GemStone"));
            cost = cursor.getString(cursor.getColumnIndex("MesoCost"));
            cube = cursor.getString(cursor.getColumnIndex("addtionalCube"));
            etc = cursor.getString(cursor.getColumnIndex("etcEncome"));
            MesoEncome.setText(encome);
            coreGemStone.setText(gem);
            MesoCost.setText(cost);
            addtionalCube.setText(cube);
            etcEncome.setText(etc);
        }

        cursor.close();
        sqlDB.close();


        calenderSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MesoEncome.getText().toString().equals("")){
                    MesoEncome.setText("0");
                }
                if(coreGemStone.getText().toString().equals("")){
                    coreGemStone.setText("0");
                }
                if(MesoCost.getText().toString().equals("")){
                    MesoCost.setText("0");
                }
                if(addtionalCube.getText().toString().equals("")){
                    addtionalCube.setText("0");
                }

                Mesoencome = Integer.parseInt(MesoEncome.getText().toString());
                coreGem = Integer.parseInt(coreGemStone.getText().toString());
                Mesocost = Integer.parseInt(MesoCost.getText().toString());
                addtionalcube = Integer.parseInt(addtionalCube.getText().toString());

                sqlDB = myDBHelper.getReadableDatabase();
                Cursor cus;
                cus = sqlDB.rawQuery("SELECT * FROM CalenderTB WHERE date = 'a"+date+"' ",null);

                if(cus.getCount() > 0) {
                    sqlDB = myDBHelper.getWritableDatabase();
                    sqlDB.execSQL("UPDATE CalenderTB SET date = 'a" + date + "', MesoEncome = '" + Mesoencome + "', GemStone = '" + coreGem + "', MesoCost = '" + Mesocost +
                            "', addtionalCube = '" + addtionalcube + "', etcEncome = '" + etcEncome.getText().toString() + "'  WHERE date = 'a" + date +"';");
                }else {
                    sqlDB.execSQL("INSERT INTO calenderTB VALUES ('a" + date + "'," + Mesoencome + "," + coreGem + "," + Mesocost + "," + addtionalcube + ",'" + etcEncome.getText().toString() + "');");

                }
                cus.close();
                sqlDB.close();

                onRestart();
                calenderDialog.dismiss();
            }
        });

        calenderDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AskDialog = new Dialog(DiaryActivity.this);
                AskDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // ????????? ??????
                AskDialog.setContentView(R.layout.customdial);
                AskDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                AskDial();
            }
        });

        calenderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calenderDialog.dismiss();
            }
        });


    }

    // ?????? ?????? ?????? ??? ????????????
    public void AskDial() {
        AskDialog.show();

        TextView TxtAsk = (TextView) AskDialog.findViewById(R.id.TxtAsk);
        TxtAsk.setText("?????? ????????????????");

        Button BtnYes = (Button) AskDialog.findViewById(R.id.BtnYes);

        Button BtnNo = (Button) AskDialog.findViewById(R.id.BtnNo);

        BtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myDBHelper.getWritableDatabase();
                sqlDB.execSQL("DELETE FROM calenderTB WHERE date = 'a"+date+"'");
                sqlDB.close();
                calenderDialog.dismiss();
                AskDialog.dismiss();
            }
        });

        BtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AskDialog.dismiss();
            }
        });

    }
}
