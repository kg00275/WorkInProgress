package com.example.android.bluetoothlegatt;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao

public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User data);

    @Query("SELECT * from user_table LIMIT 1")
    User[] getAnyUser();

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * from user_table ORDER BY user ASC")
    LiveData<List<User>> getAllUsers();

    @Delete
    void deleteUser(User user);
}