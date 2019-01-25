package com.example.android.bluetoothlegatt;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class UserRepository {

    private UserDao mUserDao;
    private LiveData<List<User>> mAllUsers;

    UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
        mAllUsers = mUserDao.getAllUsers();
    }

    LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public void insert (User user) {
        new insertAsyncTask(mUserDao).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void deleteAll()  {
        new deleteAllUsersAsyncTask(mUserDao).execute();
    }

    private static class deleteAllUsersAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao mAsyncTaskDao;

        deleteAllUsersAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public void deleteUser(User user)  {
        new deleteUserAsyncTask(mUserDao).execute(user);
    }

    private static class deleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao mAsyncTaskDao;

        deleteUserAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.deleteUser(params[0]);
            return null;
        }
    }


}
