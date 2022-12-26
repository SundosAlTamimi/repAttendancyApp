package com.example.employeetime.RoomInterface;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.employeetime.Model.Setting;

import java.util.List;

@Dao
public interface UserDaoSetting {

    @Query("SELECT * FROM SETTING_TABLE")
            public List<Setting> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Setting> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(Setting... users);

    @Query("DELETE FROM SETTING_TABLE")
    void deleteAll();
}
