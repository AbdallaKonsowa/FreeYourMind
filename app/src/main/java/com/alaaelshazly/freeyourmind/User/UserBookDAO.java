package com.alaaelshazly.freeyourmind.User;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserBookDAO {
    @Query("SELECT * from Bookname")
    List<UserBook> SelectAllBooks();

    @Query("SELECT Name from Bookname")
    List<String> AllBooks();

    @Query("delete FROM Bookname WHERE Name=:kind")
    int deleteByKind(String kind);

    @Query("select id FROM Bookname WHERE name=:kind limit 1")
    byte exists(String kind);

    @Insert
    long insert(UserBook UserBook);

}
