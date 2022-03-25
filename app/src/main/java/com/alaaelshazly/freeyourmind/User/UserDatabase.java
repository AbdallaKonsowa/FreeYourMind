package com.alaaelshazly.freeyourmind.User;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserBook.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase ourInstance;

    public static UserDatabase getInstance(Context context) {

        if (ourInstance == null) {

            ourInstance = Room.databaseBuilder(context,

                    UserDatabase.class, "UserData.db")
                    .createFromAsset("dataBase/UserData.db")
                    .allowMainThreadQueries()
                    .build();
        }

        return ourInstance;

    }

    public abstract UserBookDAO userBookDAO();
}
