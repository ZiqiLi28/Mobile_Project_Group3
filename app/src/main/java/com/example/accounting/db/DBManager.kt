package com.example.accounting.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DBManager {
    companion object {
        private lateinit var db: SQLiteDatabase

        fun initDB(context: Context) {
            val helper = DBOpenHelper(context)
            db = helper.writableDatabase
        }

        @SuppressLint("Range", "Recycle")
        fun getTypeList(kind: Int): List<TypeBean> {
            val list = ArrayList<TypeBean>()
            val sql = "select * from typetb where kind = $kind"
            val cursor: Cursor = db.rawQuery(sql, null)
            while (cursor.moveToNext()) {
                val typename: String = cursor.getString(cursor.getColumnIndex("typename"))
                val imageId: Int = cursor.getInt(cursor.getColumnIndex("imageId"))
                val sImageId: Int = cursor.getInt(cursor.getColumnIndex("sImageId"))
                val kind1: Int = cursor.getInt(cursor.getColumnIndex("kind"))
                val id: Int = cursor.getInt(cursor.getColumnIndex("id"))
                val typeBean = TypeBean(id, typename, imageId, sImageId, kind1)
                list.add(typeBean)
            }
            return list
        }

        fun insertItemToAccounttb(bean: AccountBean) {
            val values = ContentValues()
            values.put("typename", bean.typename)
            values.put("sImageId", bean.sImageId)
            values.put("note", bean.note)
            values.put("money", bean.money)
            values.put("time", bean.time)
            values.put("year", bean.year)
            values.put("month", bean.month)
            values.put("day", bean.day)
            values.put("kind", bean.kind)
            db.insert("accounttb", null, values)
        }

        @SuppressLint("Range", "Recycle")
        fun getAccountListOneDayFromAccounttb(year: Int, month: Int, day: Int): List<AccountBean> {
            val list = ArrayList<AccountBean>()
            val sql = "select * from accounttb where year=? and month=? and day=? order by id desc"
            val cursor: Cursor = db.rawQuery(sql, arrayOf(year.toString(), month.toString(), day.toString()))
            while (cursor.moveToNext()) {
                val id: Int = cursor.getInt(cursor.getColumnIndex("id"))
                val typename: String? = cursor.getString(cursor.getColumnIndex("typename"))
                val note: String? = cursor.getString(cursor.getColumnIndex("note"))
                val time: String? = cursor.getString(cursor.getColumnIndex("time"))
                val sImageId: Int = cursor.getInt(cursor.getColumnIndex("sImageId"))
                val kind: Int = cursor.getInt(cursor.getColumnIndex("kind"))
                val money: Float = cursor.getFloat(cursor.getColumnIndex("money"))
                val accountBean = AccountBean(id, typename, sImageId, note, money, time, year, month, day, kind)
                list.add(accountBean)
            }
            return list
        }

        @SuppressLint("Range", "Recycle")
        fun getSumMoneyOneDay(year: Int, month: Int, day: Int, kind: Int): Float {
            var total = 0.0f
            val sql = "select sum(money) from accounttb where year=? and month=? and day=? and kind=?"
            val cursor: Cursor = db.rawQuery(sql, arrayOf(year.toString(), month.toString(), day.toString(), kind.toString()))
            if (cursor.moveToFirst()) {
                val money: Float = cursor.getFloat(cursor.getColumnIndex("sum(money)"))
                total = money
            }
            return total
        }

        @SuppressLint("Recycle", "Range")
        fun getSumMoneyOneMonth(year: Int, month: Int, kind: Int): Float {
            var total = 0.0f
            val sql = "select sum(money) from accounttb where year=? and month=? and kind=?"
            val cursor: Cursor = db.rawQuery(sql, arrayOf(year.toString(), month.toString(), kind.toString()))
            if (cursor.moveToFirst()) {
                val money: Float = cursor.getFloat(cursor.getColumnIndex("sum(money)"))
                total = money
            }
            return total
        }

        @SuppressLint("Recycle", "Range")
        fun getCountItemOneMonth(year: Int, month: Int, kind: Int): Int {
            var total = 0
            val sql = "select count(money) from accounttb where year=? and month=? and kind=?"
            val cursor: Cursor = db.rawQuery(sql, arrayOf(year.toString(), month.toString(), kind.toString()))
            if (cursor.moveToFirst()) {
                val count: Int = cursor.getInt(cursor.getColumnIndex("count(money)"))
                total = count
            }
            return total
        }

        @SuppressLint("Recycle", "Range")
        fun getAccountListOneMonthFromAccounttb(year: Int, month: Int): List<AccountBean> {
            val list = ArrayList<AccountBean>()
            val sql = "select * from accounttb where year=? and month=? order by id desc"
            val cursor: Cursor = db.rawQuery(sql, arrayOf(year.toString(), month.toString()))
            while (cursor.moveToNext()) {
                val id: Int = cursor.getInt(cursor.getColumnIndex("id"))
                val typename: String = cursor.getString(cursor.getColumnIndex("typename")) ?: "DefaultTypename"
                val note: String = cursor.getString(cursor.getColumnIndex("note"))
                val time: String = cursor.getString(cursor.getColumnIndex("time"))
                val sImageId: Int = cursor.getInt(cursor.getColumnIndex("sImageId"))
                val kind: Int = cursor.getInt(cursor.getColumnIndex("kind"))
                val money: Float = cursor.getFloat(cursor.getColumnIndex("money"))
                val day: Int = cursor.getInt(cursor.getColumnIndex("day"))
                val accountBean = AccountBean(id, typename, sImageId, note, money, time, year, month, day, kind)
                list.add(accountBean)
            }
            return list
        }

        fun deleteItemFromAccounttbById(id: Int): Int {
            return db.delete("accounttb", "id=?", arrayOf(id.toString()))
        }

        @SuppressLint("Recycle", "Range")
        fun getAccountListByRemarkFromAccounttb(note: String): List<AccountBean> {
            val list = ArrayList<AccountBean>()
            val sql = "select * from accounttb where note like '%$note%'"
            val cursor: Cursor = db.rawQuery(sql, null)
            while (cursor.moveToNext()) {
                val id: Int = cursor.getInt(cursor.getColumnIndex("id"))
                val typename: String? = cursor.getString(cursor.getColumnIndex("typename"))
                val note: String? = cursor.getString(cursor.getColumnIndex("note"))
                val time: String? = cursor.getString(cursor.getColumnIndex("time"))
                val sImageId: Int = cursor.getInt(cursor.getColumnIndex("sImageId"))
                val kind: Int = cursor.getInt(cursor.getColumnIndex("kind"))
                val money: Float = cursor.getFloat(cursor.getColumnIndex("money"))
                val accountBean = AccountBean(id, typename ?: "", sImageId, note ?: "", money, time ?: "", 0, 0, 0, kind)
                list.add(accountBean)
            }
            return list
        }


        @SuppressLint("Recycle", "Range")
        fun getYearListFromAccounttb(): List<Int> {
            val list = ArrayList<Int>()
            val sql = "select distinct(year) from accounttb order by year asc"
            val cursor: Cursor = db.rawQuery(sql, null)
            while (cursor.moveToNext()) {
                val year: Int = cursor.getInt(cursor.getColumnIndex("year"))
                list.add(year)
            }
            return list
        }

        fun deleteAllAccount() {
            val sql = "delete from accounttb"
            db.execSQL(sql)
        }

        @SuppressLint("Recycle", "Range")
        fun getChartListFromAccounttb(year: Int, month: Int, kind: Int): List<ChartItemBean> {
            val list = ArrayList<ChartItemBean>()
            val sumMoneyOneMonth = getSumMoneyOneMonth(year, month, kind)
            val sql = "select typename,sImageId,sum(money)as total from accounttb where year=? and month=? and kind=? group by typename order by total desc"
            val cursor: Cursor = db.rawQuery(sql, arrayOf(year.toString(), month.toString(), kind.toString()))
            while (cursor.moveToNext()) {
                val sImageId: Int = cursor.getInt(cursor.getColumnIndex("sImageId"))
                val typename: String = cursor.getString(cursor.getColumnIndex("typename"))
                val total: Float = cursor.getFloat(cursor.getColumnIndex("total"))
                val ratio = FloatUtils.div(total, sumMoneyOneMonth)
                val bean = ChartItemBean(sImageId, typename, ratio, total)
                list.add(bean)
            }
            return list
        }

        @SuppressLint("Recycle", "Range")
        fun getMaxMoneyOneDayInMonth(year: Int, month: Int, kind: Int): Float {
            val sql = "select sum(money) from accounttb where year=? and month=? and kind=? group by day order by sum(money) desc"
            val cursor: Cursor = db.rawQuery(sql, arrayOf(year.toString(), month.toString(), kind.toString()))
            if (cursor.moveToFirst()) {
                return cursor.getFloat(cursor.getColumnIndex("sum(money)"))
            }
            return 0f
        }

        @SuppressLint("Recycle", "Range")
        fun getSumMoneyOneDayInMonth(year: Int, month: Int, kind: Int): List<BarChartItemBean> {
            val sql = "select day,sum(money) from accounttb where year=? and month=? and kind=? group by day"
            val cursor: Cursor = db.rawQuery(sql, arrayOf(year.toString(), month.toString(), kind.toString()))
            val list = ArrayList<BarChartItemBean>()
            while (cursor.moveToNext()) {
                val day: Int = cursor.getInt(cursor.getColumnIndex("day"))
                val smoney: Float = cursor.getFloat(cursor.getColumnIndex("sum(money)"))
                val itemBean = BarChartItemBean(year, month, day, smoney)
                list.add(itemBean)
            }
            return list
        }
    }
}