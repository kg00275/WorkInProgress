package com.example.android.bluetoothlegatt;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user")
    private String mUser;

    public User(@NonNull String user) {this.mUser = user;}

    public String getUser(){return this.mUser;}
}