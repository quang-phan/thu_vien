package com.example.thuvien.dataConnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.thuvien.model.DauSach;

import java.util.ArrayList;
import java.util.List;

public class DauSachDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "thu_vien.db";
    private static final int DATABASE_VERSION = 2;

    public DauSachDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE dau_sach(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ten TEXT," +
                "tacGia TEXT," +
                "chuDe TEXT," +
                "gia TEXT," +
                "ghiChu TEXT)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        super.onOpen(db);

    }

    public List<DauSach> getAll(){
        SQLiteDatabase db = getReadableDatabase();

        List<DauSach> list = new ArrayList<>();

        String orderBy = "ten, tacGia";

        Cursor rs = db.query("dau_sach", null, null, null,
            null, null, orderBy);

        while(rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String ten = rs.getString(1);
            String tacGia = rs.getString(2);
            String chuDe = rs.getString(3);
            String gia = rs.getString(4);
            String ghiChu = rs.getString(5);

            DauSach dauSach;
            try {
                dauSach = new DauSach(id, ten, tacGia, chuDe,
                        Double.parseDouble(gia), ghiChu);
            }catch (NumberFormatException e){
                dauSach = new DauSach(id, ten, tacGia, chuDe,
                        0.0, ghiChu);
            }

            list.add(dauSach);
        }

        return list;
    }

    public List<DauSach> getDauSachGroup(String ten, String tacGia, String chuDe){
        List<DauSach> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String where = "ten = ? AND tacGia = ? AND chuDe = ?";
        String whereArgs[] = {ten, tacGia, chuDe};

        Cursor rs = db.query("dau_sach", null, where,
                whereArgs, null, null, null);

        while(rs != null && rs.moveToNext()){
            int id1 = rs.getInt(0);
            String ten1 = rs.getString(1);
            String tacGia1 = rs.getString(2);
            String chuDe1 = rs.getString(3);
            String gia1 = rs.getString(4);
            String ghiChu1 = rs.getString(5);

            DauSach dauSach = new DauSach(id1, ten1, tacGia1,
                    chuDe1, Double.parseDouble(gia1), ghiChu1);

            list.add(dauSach);
        }

        return list;
    }

    public long them(DauSach dauSach){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();

        values.put("ten", dauSach.getTen());
        values.put("tacGia", dauSach.getTacGia());
        values.put("chuDe", dauSach.getChuDe());
        values.put("gia", dauSach.getGia()+"");
        values.put("ghiChu", dauSach.getGhiChu());

        return db.insert("dau_sach", null, values);
    }

    public int xoa(DauSach dauSach){
        String where = "id = ?";
        String whereArgs[] = {""+dauSach.getId()};
        SQLiteDatabase db = getWritableDatabase();

        return db.delete("dau_sach", where, whereArgs);
    }

    public int capNhat(DauSach dauSach){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();

        values.put("ten", dauSach.getTen());
        values.put("tacGia", dauSach.getTacGia());
        values.put("chuDe", dauSach.getChuDe());
        values.put("gia", dauSach.getGia()+"");
        values.put("ghiChu", dauSach.getGhiChu());

        String whereClause = "id = ?";
        String whereArgs[] = {dauSach.getId()+""};

        return db.update("dau_sach", values, whereClause, whereArgs);
    }

    public List<DauSach> findDauSach(String ten, String tacGia, String chuDe){
        String whereClause = "ten LIKE ? AND tacGia LIKE ? AND chuDe LIKE ?";
        String whereArgs[] = {"%"+ten+"%", "%"+tacGia+"%", "%"+chuDe+"%"};
        List<DauSach> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor rs = db.query("dau_sach", null, whereClause,
                whereArgs, null, null, null);

        while(rs != null && rs.moveToNext()){
            int id1 = rs.getInt(0);
            String ten1 = rs.getString(1);
            String tacGia1 = rs.getString(2);
            String chuDe1 = rs.getString(3);
            String gia1 = rs.getString(4);
            String ghiChu1 = rs.getString(5);

            DauSach dauSach = new DauSach(id1, ten1, tacGia1,
                    chuDe1, Double.parseDouble(gia1), ghiChu1);

            list.add(dauSach);
        }

        return list;
    }

    public DauSach getById(int id){
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "id = ?";
        String whereArgs[] = {id+""};

        Cursor rs = db.query("dau_sach", null, whereClause, whereArgs,
                null, null, null);

        if(rs != null && rs.moveToNext()){
            int id1 = rs.getInt(0);
            String ten1 = rs.getString(1);
            String tacGia1 = rs.getString(2);
            String chuDe1 = rs.getString(3);
            String gia1 = rs.getString(4);
            String ghiChu1 = rs.getString(5);

            DauSach dauSach = new DauSach(id1, ten1, tacGia1,
                    chuDe1, Double.parseDouble(gia1), ghiChu1);

            return dauSach;
        }

        return null;
    }

    public List<DauSach> timTheoTen(String ten){
        String whereClause = "ten LIKE ?";
        String whereArgs[] = {"%"+ten+"%"};
        SQLiteDatabase db = getReadableDatabase();
        List<DauSach> list = new ArrayList<>();

        Cursor rs = db.query("dau_sach", null, whereClause,
                whereArgs, null, null, null);

        while(rs != null && rs.moveToNext()){
            int id1 = rs.getInt(0);
            String ten1 = rs.getString(1);
            String tacGia1 = rs.getString(2);
            String chuDe1 = rs.getString(3);
            String gia1 = rs.getString(4);
            String ghiChu1 = rs.getString(5);

            DauSach dauSach = new DauSach(id1, ten1, tacGia1,
                    chuDe1, Double.parseDouble(gia1), ghiChu1);

            list.add(dauSach);
        }

        return list;
    }

}
