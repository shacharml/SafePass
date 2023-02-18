package com.shacharml.safepass.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.shacharml.safepass.Data.MyDatabase;
import com.shacharml.safepass.Data.PasswordDao;
import com.shacharml.safepass.Entities.Password;

import java.util.List;

public class PasswordRepository {

    private final PasswordDao passwordDau;
    private final LiveData<List<Password>> allPasswords;

    public PasswordRepository(Application application) {
        passwordDau = MyDatabase.getInstance(application).passwordDao();
        allPasswords = passwordDau.getPasswordsList();
    }

    public void insert(Password password) {
        new PasswordRepository.InsertPasswordAsyncTask(passwordDau).execute(password);
    }

    public void updatePassword(Password password) {
        new PasswordRepository.UpdatePasswordAsyncTask(passwordDau).execute(password);
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                //do in background
//
//            }
//        });
    }

    public void deleteAll() {
        new PasswordRepository.DeleteAllPasswordAsyncTask(passwordDau).execute();
    }

    public void delete(Password password) {
        new PasswordRepository.DeletePasswordAsyncTask(passwordDau).execute(password);
    }

    public LiveData<List<Password>> getAllPasswords() {
        return allPasswords;
    }

    /**
     * because this functions can work in the same time
     * on the database we need way to create then a multi
     * threads or something like that
     * todo ask vadim if there is another way to do this and not duplicate code
     */
    private static class InsertPasswordAsyncTask extends AsyncTask<Password, Void, Void> {
        private final PasswordDao PasswordDau;

        public InsertPasswordAsyncTask(PasswordDao PasswordDau) {
            this.PasswordDau = PasswordDau;
        }

        @Override
        protected Void doInBackground(Password... passwords) {
            PasswordDau.insert(passwords[0]);
            return null;
        }
    }

    private static class UpdatePasswordAsyncTask extends AsyncTask<Password, Void, Void> {
        private final PasswordDao PasswordDau;

        public UpdatePasswordAsyncTask(PasswordDao PasswordDau) {
            this.PasswordDau = PasswordDau;
        }

        @Override
        protected Void doInBackground(Password... passwords) {
            PasswordDau.update(passwords[0]);
            return null;
        }
    }

    private static class DeleteAllPasswordAsyncTask extends AsyncTask<Void, Void, Void> {
        private final PasswordDao passwordDau;

        public DeleteAllPasswordAsyncTask(PasswordDao PasswordDau) {
            this.passwordDau = PasswordDau;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            passwordDau.deleteAll();
            return null;
        }
    }

    private static class DeletePasswordAsyncTask extends AsyncTask<Password, Void, Void> {
        private final PasswordDao passwordDau;

        public DeletePasswordAsyncTask(PasswordDao PasswordDau) {
            this.passwordDau = PasswordDau;
        }

        @Override
        protected Void doInBackground(Password... passwords) {
            passwordDau.delete(passwords[0]);
            return null;
        }
    }
}
