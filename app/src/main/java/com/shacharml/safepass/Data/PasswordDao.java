package com.shacharml.safepass.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.shacharml.safepass.Entities.Password;

import java.util.List;

@Dao
public interface PasswordDao {

    @Insert
    void insert(Password password);

//    @Query("DELETE FROM password_table WHERE userId = :userId ")
    @Query("DELETE FROM password_table")
    void deleteAll();

    @Delete
    void delete(Password password);

    @Update
    void update(Password password);

    /**
     * mast have for the recycler view
//     * @param userId
     * @return LiveData of the Passwords List
     */
//    @Query("SELECT * FROM password_table WHERE userId = :userId ORDER BY name ASC")
    @Query("SELECT * FROM password_table ORDER BY name ASC")
    LiveData<List<Password>> getPasswordsList ();
//    LiveData<List<Password>> getPasswordsList (int userId);
}
