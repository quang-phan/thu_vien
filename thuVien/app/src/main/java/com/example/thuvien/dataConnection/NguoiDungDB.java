package com.example.thuvien.dataConnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.thuvien.model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "thanh_vien.db";
    private static final int DATABASE_VERSION = 1;

    public NguoiDungDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE nguoi_dung(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "diaChi TEXT," +
                "soDienThoai TEXT," +
                "cmnd TEXT," +
                "username TEXT," +
                "password TETX," +
                "role TEXT)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        super.onOpen(db);
    }

    public long themNguoiDung(String ten, String diaChi, String soDienThoai,
                              String cmnd){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", ten);
        values.put("diaChi", diaChi);
        values.put("soDienThoai", soDienThoai);
        values.put("cmnd", cmnd);
        values.put("role", "TV");

        return db.insert("nguoi_dung", null, values);
    }

    public  long themNguoiDung(NguoiDung nguoiDung){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();

        values.put("name", nguoiDung.getName());
        values.put("diaChi", nguoiDung.getDiaChi());
        values.put("soDienThoai", nguoiDung.getSoDienThoai());
        values.put("cmnd", nguoiDung.getCmnd());
        values.put("username", nguoiDung.getUsername());
        values.put("password", nguoiDung.getPassword());
        values.put("role", nguoiDung.getRole());

        return db.insert("nguoi_dung", null, values);
    }

    public NguoiDung getNguoiDung(String ten, String diaChi, String soDienThoai,
                                  String cmnd){
        String whereClause = "name = ? AND diaChi = ? AND soDienThoai = ?" +
                " AND cmnd = ?";
        String whereArgs[] = {ten, diaChi, soDienThoai, cmnd};

        SQLiteDatabase db = getReadableDatabase();

        Cursor rs = db.query("nguoi_dung", null, whereClause, whereArgs,
                null, null, null);

        if(rs != null && rs.moveToNext()){
            int id2 = rs.getInt(0);
            String ten2 = rs.getString(1);
            String diaChi2 = rs.getString(2);
            String soDienThoai2 = rs.getString(3);
            String cmnd2 = rs.getString(4);
            String username2 = rs.getString(5);
            String password2 = rs.getString(6);
            String role2 = rs.getString(7);

            NguoiDung nguoiDung = new NguoiDung(id2, ten2, diaChi2, soDienThoai2,
                    cmnd2, username2, password2, role2);

            return nguoiDung;
        }

        return null;
    }

    public NguoiDung getById(int id){
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "id = ?";
        String whereArgs[] = {id+""};

        Cursor rs = db.query("nguoi_dung", null, whereClause, whereArgs,
                null, null, null);

        if(rs != null && rs.moveToNext()){
            int id2 = rs.getInt(0);
            String ten2 = rs.getString(1);
            String diaChi2 = rs.getString(2);
            String soDienThoai2 = rs.getString(3);
            String cmnd2 = rs.getString(4);
            String username2 = rs.getString(5);
            String password2 = rs.getString(6);
            String role2 = rs.getString(7);

            NguoiDung nguoiDung = new NguoiDung(id2, ten2, diaChi2, soDienThoai2,
                    cmnd2, username2, password2, role2);

            return nguoiDung;
        }

        return null;
    }

    public List<NguoiDung> timTheoTen(String ten){
        String whereClause = "name LIKE ?";
        String whereArgs[] = {"%"+ten+"%"};
        SQLiteDatabase db = getReadableDatabase();
        List<NguoiDung> list = new ArrayList<>();

        Cursor rs = db.query("nguoi_dung", null, whereClause,
                whereArgs, null, null, null);

        while(rs != null && rs.moveToNext()){
            int id2 = rs.getInt(0);
            String ten2 = rs.getString(1);
            String diaChi2 = rs.getString(2);
            String soDienThoai2 = rs.getString(3);
            String cmnd2 = rs.getString(4);
            String username2 = rs.getString(5);
            String password2 = rs.getString(6);
            String role2 = rs.getString(7);

            NguoiDung nguoiDung = new NguoiDung(id2, ten2, diaChi2, soDienThoai2,
                    cmnd2, username2, password2, role2);

            list.add(nguoiDung);
        }

        return list;
    }

    public int doiMK(NguoiDung nguoiDung){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id = ?";
        String whereArgs[] = {nguoiDung.getId()+""};

        ContentValues values = new ContentValues();

        values.put("password", nguoiDung.getPassword());

        return db.update("nguoi_dung", values, whereClause, whereArgs);
    }

    public int capNhat(NguoiDung nguoiDung){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id = ?";
        String whereArgs[] = {nguoiDung.getId()+""};

        ContentValues values = new ContentValues();

        values.put("diaChi", nguoiDung.getDiaChi());
        values.put("soDienThoai", nguoiDung.getSoDienThoai());
        values.put("username", nguoiDung.getUsername());
        values.put("password", nguoiDung.getPassword());

        return db.update("nguoi_dung", values, whereClause, whereArgs);
    }

    public int capNhat2(NguoiDung nguoiDung){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id = ?";
        String whereArgs[] = {nguoiDung.getId()+""};

        ContentValues values = new ContentValues();

        values.put("name", nguoiDung.getName());
        values.put("cmnd", nguoiDung.getCmnd());
        values.put("diaChi", nguoiDung.getDiaChi());
        values.put("soDienThoai", nguoiDung.getSoDienThoai());
        values.put("username", nguoiDung.getUsername());
        values.put("password", nguoiDung.getPassword());

        return db.update("nguoi_dung", values, whereClause, whereArgs);
    }

    public NguoiDung dangNhap(String userName, String passWord){
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "username = ? AND password = ?";
        String whereArgs[] = {userName, passWord};
        Cursor rs = db.query("nguoi_dung", null, whereClause,
                whereArgs, null, null, null);

        if(rs != null && rs.moveToNext()){
            int id2 = rs.getInt(0);
            String ten2 = rs.getString(1);
            String diaChi2 = rs.getString(2);
            String soDienThoai2 = rs.getString(3);
            String cmnd2 = rs.getString(4);
            String username2 = rs.getString(5);
            String password2 = rs.getString(6);
            String role2 = rs.getString(7);

            NguoiDung nguoiDung = new NguoiDung(id2, ten2, diaChi2, soDienThoai2,
                    cmnd2, username2, password2, role2);

            return nguoiDung;
        }

        return null;

    }
}
