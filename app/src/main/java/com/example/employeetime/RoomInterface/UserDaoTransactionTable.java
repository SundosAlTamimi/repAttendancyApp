package com.example.employeetime.RoomInterface;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.employeetime.Model.DataStatus;
import com.example.employeetime.Model.TransactionTable;
import com.example.employeetime.Model.inOutTrans;

import java.util.List;

@Dao
public interface UserDaoTransactionTable {

    @Query("SELECT * FROM TRANSACTION_TABLE order by  cast(TRANS_KIND as unsigned)")
            public List<TransactionTable> getAll();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<TransactionTable> users);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertUsers(TransactionTable... users);

    @Query("DELETE FROM TRANSACTION_TABLE")
    void deleteAll();
    @Query("UPDATE TRANSACTION_TABLE SET ShowInDay = :show WHERE TRANS_KIND = :transKind")
    void UpdateShow(String transKind,String show);

    @Query("UPDATE TRANSACTION_TABLE SET ShowInDay = :show where ShowInDay IS NULL")
    void UpdateAllShow(String show);

    @Query("SELECT * FROM TRANSACTION_TABLE where TRANS_KIND = :transKind")
    public List<TransactionTable> GetIfShow(String transKind);
    @Query("SELECT t1.IN_OUT as k ,t2.EVENT_NO as a FROM TRANSACTION_TABLE t1 , DATA_STATUS_TABLE t2 where t2.EVENT_NO=t1.TRANS_KIND")
    public List<inOutTrans> getInOutTra();
}
