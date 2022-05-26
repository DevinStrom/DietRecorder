package com.devin.project4_v5

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


val DATABASENAME = "MY DATABASE"
val TABLENAME = "UserFood"
val COL_NAME = "name"
val COL_ID = "id"
val created = "created_at"

class DataBaseHandler(var context:Context) : SQLiteOpenHelper(context, DATABASENAME, null,1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLENAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $created DATETIME Default CURRENT_TIMESTAMP, $COL_NAME VARCHAR(256))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLENAME")
        onCreate(db);
    }

    //add row data to sqlite db
    fun insertData(amountFood: Foods) {
        val contentValues = ContentValues()
        val database = this.writableDatabase

        contentValues.put(COL_NAME, amountFood.foodAmountName)

        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Item added", Toast.LENGTH_SHORT).show()
        }
    }

    //read data from sqlite db
    @SuppressLint("Range")
    fun readData(): String {
        var allFood = ""
        val db = this.readableDatabase

        val query = "Select name, id, datetime(created_at,'localtime') as created_at from $TABLENAME"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                var id = cursor.getString(cursor.getColumnIndex(COL_ID))
                var name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                var currentDate = cursor.getString(cursor.getColumnIndex(created))
                allFood = "$allFood\n$id $currentDate $name"
                    //^ can be returned as arraylist for recyclerview, undecided if i even want that
            }
            while (cursor.moveToNext())
        }
        return allFood
    }

    //delete row from sqlite db
    fun deleteData(name : String): Int {
        val db = this.writableDatabase
        return db.delete(TABLENAME, "name=?", arrayOf(name))
    }
}