package com.example.appstock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "Appstock.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Appstock.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
     MyDatabase.execSQL("CREATE TABLE allusers(email TEXT primary key, password TEXT)");
     MyDatabase.execSQL("CREATE TABLE barang(id INTEGER primary key, gambar TEXT, name TEXT, harga_product INTEGER, harga_jual INTEGER, stock_product INTEGER, desc_product TEXT)");
     MyDatabase.execSQL("CREATE TABLE transaksi_barang_masuk(id INTEGER primary key, nama_product TEXT, harga_product INTEGER, qty_barang_masuk INTEGER, income INTEGER)");
     MyDatabase.execSQL("CREATE TABLE transaksi_barang_keluar(id INTEGER primary key, nama_product TEXT, harga_jual INTEGER, qty_barang_keluar INTEGER, outcome INTEGER)");
     MyDatabase.execSQL("CREATE TABLE suplier(id INTEGER primary key, nama_suplier TEXT, no_telpon TEXT, email TEXT, alamat TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("drop Table if exists allusers");
        MyDatabase.execSQL("drop table if exists barang");
        MyDatabase.execSQL("drop table if exists transaksi_barang_masuk");
        MyDatabase.execSQL("drop table if exists transaksi_barang_keluar");
        MyDatabase.execSQL("drop table if exists suplier");
    }

    public Boolean insertData(String email, String password ){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("allusers", null, contentValues);

        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("select * from allusers where email = ?", new String[]{email});

        if (cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }

    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("select * from allusers where email = ? and password = ?", new String[]{email, password});

        if(cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }


    public boolean insertDataProduct(byte[] imageBytes, String name, int harga_product, int harga_jual,int stock_product, String desc_product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("gambar", imageBytes);
        values.put("name", name);
        values.put("harga_product", harga_product);
        values.put("harga_jual", harga_jual);
        values.put("stock_product", stock_product);
        values.put("desc_product", desc_product);
        long result = db.insert("barang", null, values);
        return result != -1;
    }

    public boolean insertDataSuplier(String nama_suplier, String no_telpon, String email, String alamat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_suplier", nama_suplier);
        contentValues.put("no_telpon", no_telpon);
        contentValues.put("email", email);
        contentValues.put("alamat", alamat);
        long result = db.insert("suplier", null, contentValues);
        return result != -1;
    }

    public boolean insertTransaksiMasuk(String nama_product, int harga_product, int qty_barang_masuk, int income){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama_product", nama_product);
        values.put("harga_product", harga_product);
        values.put("qty_barang_masuk", qty_barang_masuk);
        values.put("income", income);
        long result = db.insert("transaksi_barang_masuk", null, values);
        return result != -1;
    }

    public boolean insertTransaksiKeluar(String nama_product, int harga_jual, int qty_barang_keluar, int outcome){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama_product", nama_product);
        values.put("harga_jual", harga_jual);
        values.put("qty_barang_keluar", qty_barang_keluar);
        values.put("outcome", outcome);
        long result = db.insert("transaksi_barang_keluar", null, values);
        return result != -1;
    }



}
