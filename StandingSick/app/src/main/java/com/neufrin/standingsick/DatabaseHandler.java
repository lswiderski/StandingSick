package com.neufrin.standingsick;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by neufrin on 13.11.2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context)
    {
        super(context,"StandingSick.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table Questions (Id integer primary key autoincrement," +
                "                       Content text);" +
                "Create table Answers (Id integer primary key autoincrement," +
                "                      Content text," +
                "                      QId integer," +
                "                     FOREIGN KEY(QId) REFERENCES Questions(Id));" +
                "Create table Sessions (Id integer primary key autoincrement," +
                "                      Date date);" +
                "Create table UserAnswers (Id integer primary key autoincrement," +
                "                          Session integer," +
                "                          QId integer," +
                "                          AId integer," +
                "                          FOREIGN KEY(Session) REFERENCES Sessions(Id)," +
                "                          FOREIGN KEY(QId) REFERENCES Questions(Id)," +
                "                          FOREIGN KEY(AId) REFERENCES Answers(Id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
