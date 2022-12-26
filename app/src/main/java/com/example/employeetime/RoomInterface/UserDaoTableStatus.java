package com.example.employeetime.RoomInterface;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.employeetime.Model.DataStatus;
import com.example.employeetime.Model.Setting;

import java.util.List;

@Dao
public interface UserDaoTableStatus {

    @Query("SELECT * FROM DATA_STATUS_TABLE")
            public List<DataStatus> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DataStatus> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(DataStatus... users);

    @Query("DELETE FROM DATA_STATUS_TABLE")
    void deleteAll();
}
