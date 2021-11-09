package com.ping.maplelist;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




public class myDBHelper extends SQLiteOpenHelper {

    public myDBHelper(Context context) {
        super(context, "MaplelistDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MaplelistTB ( CharName VARCHAR(40) PRIMARY KEY, CharInfo1 VARCHAR(5), CharInfo2 VARCHAR(5), CharInfo3 VARCHAR(5),CharImg VARCHAR(300), CharExp VARCHAR(50));");
        db.execSQL("CREATE TABLE SymbolTB ( CharName VARCHAR(40) PRIMARY KEY, Symbol0 int default 1, Symbol0Info int default 0, Symbol1 int default 1, Symbol1Info int default 0, Symbol2 int default 1," +
                " Symbol2Info int default 0, Symbol3 int default 1, Symbol3Info int default 0, Symbol4 int default 1, Symbol4Info int default 0, Symbol5 int default 1, Symbol5Info int default 0," +
                " Symbol6 int default 1, Symbol6Info int default 0, Symbol7 int default 1, Symbol7Info int default 0);");
        db.execSQL("CREATE TABLE ExpTB (CharName VARCHAR(40) PRIMARY KEY, Link0 int default 0, Link1 int default 0, Link2 int default 0, Edit0 int default 0, Edit1 int default 0, Edit2 int default 0, " +
                "Edit3 int default 0, Edit4 int default 1, Exp0 int default 0, Exp1 int default 0, Exp2 int default 0, Exp3 int default 0, Exp4 int default 0, Exp5 int default 0);");
        db.execSQL("CREATE TABLE SelectCharTB (SelectChar int default 0);");
        db.execSQL("CREATE TABLE CalenderTB (date VARCHAR(10) PRIMARY KEY, MesoEncome INT, GemStone INT, MesoCost INT,addtionalCube INT ,etcEncome VARCHAR(200) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MaplelistTB");
        db.execSQL("DROP TABLE IF EXISTS SymbolTB");
        db.execSQL("DROP TABLE IF EXISTS ExpTB");
        db.execSQL("DROP TABLE IF EXISTS SelectCharTB");
        db.execSQL("DROP TABLE IF EXISTS CalenderTB");
        onCreate(db);

    }
}