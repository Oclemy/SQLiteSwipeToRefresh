package com.tutorials.hp.sqliteswipetorefresh.mDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Oclemmy on 5/2/2016 for ProgrammingWizards Channel and http://www.Camposha.com.
 */
public class DBAdapter {

    Context c;
    SQLiteDatabase db;
    DBHelper helper;

    public DBAdapter(Context c) {
        this.c = c;
        helper=new DBHelper(c);
    }

    //OPEN DB
    public void openDB()
    {
        try
        {
            db=helper.getWritableDatabase();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //CLOSING
    public void closeDB()
    {
        try
        {
            helper.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //SAVE OR INSERTING
    public boolean add(String name)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(Constants.NAME, name);

            db.insert(Constants.TB_NAME, Constants.ROW_ID, cv);

            return true;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    //SELECT OR RETRIEVE
    public Cursor retrieve()
    {
        String[] columns={Constants.ROW_ID,Constants.NAME};

        return db.query(Constants.TB_NAME,columns,null,null,null,null,null);
    }
}
