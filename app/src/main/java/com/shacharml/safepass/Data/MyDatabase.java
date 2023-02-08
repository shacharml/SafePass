package com.shacharml.safepass.Data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.shacharml.safepass.Entities.Password;
import com.shacharml.safepass.R;

@Database(entities = {Password.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    //For the Singleton
    private static MyDatabase instance;

    //synchronized = only one thread at a time can reach this function
    public static synchronized MyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "safe_pass_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

//    public abstract UserDao userDao();

    public abstract PasswordDao passwordDao();

    /**
     * insert data to our database to see in the recycler view
     */
private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void, Void> {

        private PasswordDao passwordDau;
//        private UserDao userDao;

        private  PopulateDbAsyncTask(MyDatabase db){
            passwordDau = db.passwordDao();
//            userDao = db.userDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
//            User u = new User("0543349455");
//            u.setId(1);
//            userDao.insert(u);
            passwordDau.insert(new Password("Spotify","123456", "www.google.com",String.valueOf(R.drawable.cl_spotify)));
            passwordDau.insert(new Password("Gmail","123456","www.gmail.com",String.valueOf(R.drawable.cl_gmail)));
            passwordDau.insert(new Password("Facebook","123456","www.facebook.com",String.valueOf(R.drawable.cl_facebook)));
            passwordDau.insert(new Password("Linkdin","123456","www.linkdin.com",String.valueOf(R.drawable.cl_linkedin)));
            return null;
        }
    }

}
