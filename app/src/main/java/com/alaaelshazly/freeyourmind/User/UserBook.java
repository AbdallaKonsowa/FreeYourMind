package com.alaaelshazly.freeyourmind.User;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Bookname")
public class UserBook {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String Name;

}
