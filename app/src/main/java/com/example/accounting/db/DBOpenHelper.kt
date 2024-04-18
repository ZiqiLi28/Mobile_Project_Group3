package com.example.accounting.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.accounting.R

class DBOpenHelper(context: Context) : SQLiteOpenHelper(context, "tally.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        var sql = "create table typetb(id integer primary key autoincrement,typename" +
                " varchar(10),imageId integer,sImageId integer,kind integer)"
        db.execSQL(sql)
        insertType(db)
        sql = "create table accounttb(id integer primary key autoincrement,typename " +
                "varchar(10),sImageId integer,note varchar(80),money float," +
                "time varchar(60),year integer,month integer,day integer,kind integer)"
        db.execSQL(sql)
    }

    private fun insertType(db: SQLiteDatabase) {
        val sql = "insert into typetb (typename,imageId,sImageId,kind) values (?,?,?,?)"
        db.execSQL(sql, arrayOf("Other", R.mipmap.ic_qita, R.mipmap.ic_qita_fs, 0))
        db.execSQL(sql, arrayOf("Food", R.mipmap.ic_canyin, R.mipmap.ic_canyin_fs, 0))
        db.execSQL(sql, arrayOf("Traffic", R.mipmap.ic_jiaotong, R.mipmap.ic_jiaotong_fs, 0))
        db.execSQL(sql, arrayOf("Shopping", R.mipmap.ic_fushi, R.mipmap.ic_fushi_fs, 0))
        db.execSQL(sql, arrayOf("Necessities", R.mipmap.ic_riyongpin, R.mipmap.ic_riyongpin_fs, 0))
        db.execSQL(sql, arrayOf("Leisure", R.mipmap.ic_yule, R.mipmap.ic_yule_fs, 0))
        db.execSQL(sql, arrayOf("Snacks", R.mipmap.ic_lingshi, R.mipmap.ic_lingshi_fs, 0))
        db.execSQL(sql, arrayOf("Social", R.mipmap.ic_yanjiu, R.mipmap.ic_yanjiu_fs, 0))
        db.execSQL(sql, arrayOf("Education", R.mipmap.ic_xuexi, R.mipmap.ic_xuexi_fs, 0))
        db.execSQL(sql, arrayOf("Medicals", R.mipmap.ic_yiliao, R.mipmap.ic_yiliao_fs, 0))
        db.execSQL(sql, arrayOf("Housing", R.mipmap.ic_zhufang, R.mipmap.ic_zhufang_fs, 0))
        db.execSQL(sql, arrayOf("Bills", R.mipmap.ic_shuidianfei, R.mipmap.ic_shuidianfei_fs, 0))
        db.execSQL(sql, arrayOf("Phone Bill", R.mipmap.ic_tongxun, R.mipmap.ic_tongxun_fs, 0))
        db.execSQL(sql, arrayOf("Gifts", R.mipmap.ic_renqingwanglai, R.mipmap.ic_renqingwanglai_fs, 0))
        db.execSQL(sql, arrayOf("Other", R.mipmap.in_qt, R.mipmap.in_qt_fs, 1))
        db.execSQL(sql, arrayOf("Salary", R.mipmap.in_xinzi, R.mipmap.in_xinzi_fs, 1))
        db.execSQL(sql, arrayOf("Bonus", R.mipmap.in_jiangjin, R.mipmap.in_jiangjin_fs, 1))
        db.execSQL(sql, arrayOf("Refund", R.mipmap.in_jieru, R.mipmap.in_jieru_fs, 1))
        db.execSQL(sql, arrayOf("Transfer", R.mipmap.in_shouzhai, R.mipmap.in_shouzhai_fs, 1))
        db.execSQL(sql, arrayOf("Stock", R.mipmap.in_touzi, R.mipmap.in_touzi_fs, 1))
        db.execSQL(sql, arrayOf("Idle sale", R.mipmap.in_ershoushebei, R.mipmap.in_ershoushebei_fs, 1))
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}