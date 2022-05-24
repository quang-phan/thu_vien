package com.example.thuvien.dataConnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.thuvien.model.SachChoMuon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SachChoMuonDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sach_muon.db";
    private static final int DATABASE_VERSION = 1;

    public SachChoMuonDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE sach_cho_muon(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ngayMuon TEXT," +
                "ngayTra TEXT," +
                "idThanhVien INTEGER," +
                "idDauSach INTEGER)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        super.onOpen(db);
    }

    public List<SachChoMuon> getByThanhVienId(int id) throws ParseException {
        String whereClause = "idThanhVien = ?";
        String whereArgs[] = {""+id};
        List<SachChoMuon> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor rs = db.query("sach_cho_muon", null, whereClause, whereArgs, null,
                null, null);

        while(rs != null && rs.moveToNext()){
            int id1 = rs.getInt(0);
            String ngayMuon1 = rs.getString(1);
            String ngayTra1 = rs.getString(2);
            String idThanhVien = rs.getString(3);
            String idDauSach = rs.getString(4);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            SachChoMuon sachChoMuon = new SachChoMuon(id1, dateFormat.parse(ngayMuon1),
                    dateFormat.parse(ngayTra1), Integer.parseInt(idThanhVien),
                    Integer.parseInt(idDauSach));

            list.add(sachChoMuon);
        }

        return list;
    }

    //chuc nang: kiem tra xem dau sach co ton tai khong
    //dau vao: id dau sach
    //dau ra: gia tri true khi sach ton tai hoac false khi sach khong ton tai
    public boolean kiemTraDauSach(int id){
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "idDauSach = ?";
        String whereArgs[] = {id+""};

        Cursor rs = db.query("sach_cho_muon", null, whereClause,
                whereArgs, null, null, null);
        if(rs == null){
            return false;
        }

        if(!rs.moveToNext()){
            return  false;
        }

        return true;
    }

    public long themSachMuon(int idThanhVien, int idDauSach){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Date date = new Date();

        long temp = date.getTime() + (30L*24L*60L*60L*1000L);

        Date dateTra = new Date(temp);

        System.out.println(dateFormat.format(dateTra));

        values.put("idThanhVien", idThanhVien);
        values.put("idDauSach", idDauSach);
        values.put("ngayMuon", dateFormat.format(date));
        values.put("ngayTra", dateFormat.format(dateTra));

        return db.insert("sach_cho_muon", null, values);
    }

    public int traSach(int idThanhVien, int idDauSach){
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = "idThanhVien = ? AND idDauSach = ?";
        String whereArgs[] = {idThanhVien+"", idDauSach+""};

        return db.delete("sach_cho_muon", whereClause, whereArgs);
    }

    public SachChoMuon getByThanhVienAndDauSach(int idThanhVien, int idDauSach) throws ParseException {
        String whereClause = "idThanhVien = ? AND idDauSach = ?";
        String whereArgs[] = {idThanhVien+"", idDauSach+""};
        SQLiteDatabase db = getReadableDatabase();

        Cursor rs = db.query("sach_cho_muon", null, whereClause,
                whereArgs, null, null, null);

        if(rs != null && rs.moveToNext()){
            int id1 = rs.getInt(0);
            String ngayMuon1 = rs.getString(1);
            String ngayTra1 = rs.getString(2);
            String idThanhVien1 = rs.getString(3);
            String idDauSach1 = rs.getString(4);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            SachChoMuon sachChoMuon = new SachChoMuon(id1, dateFormat.parse(ngayMuon1),
                    dateFormat.parse(ngayTra1), Integer.parseInt(idThanhVien1),
                    Integer.parseInt(idDauSach1));

            return sachChoMuon;
        }

        return null;
    }

}
