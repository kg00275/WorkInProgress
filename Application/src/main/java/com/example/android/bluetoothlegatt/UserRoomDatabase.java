package com.example.android.bluetoothlegatt;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {User.class}, version = 1,  exportSchema = false)
public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    private static UserRoomDatabase INSTANCE;

    static UserRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserRoomDatabase.class, "user_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDao mDao;
        String [] users = {};

        PopulateDbAsync(UserRoomDatabase db) {
            mDao = db.userDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            if (mDao.getAnyUser().length < 1) {
                for (int i = 0; i <= users.length - 1; i++) {
                    User user = new User(users[i]);
                    mDao.insert(user);
                }
            }
            return null;
        }
    }
}