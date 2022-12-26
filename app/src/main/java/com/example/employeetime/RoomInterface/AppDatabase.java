package com.example.employeetime.RoomInterface;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.employeetime.Model.DataStatus;
import com.example.employeetime.Model.Setting;
import com.example.employeetime.Model.TransactionTable;


@Database(entities = {Setting.class, DataStatus.class, TransactionTable.class}, version =  6, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDaoSetting SettingTable();
    public abstract UserDaoTableStatus dataStatusTable();
    public abstract UserDaoTransactionTable daoTransactionTable();

    private static AppDatabase InstanceDatabase;
    private static String DatabaseName="EmployTimeControlDB";


    static final Migration FROM_1_TO_2 = new Migration(5, 6) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE Repo
//                    ADD COLUMN createdAt TEXT");
        }
    };

    public static synchronized AppDatabase getInstanceDatabase(Context context) {



        if (InstanceDatabase == null) {

            InstanceDatabase = Room.databaseBuilder(context, AppDatabase.class, DatabaseName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .fallbackToDestructiveMigrationFrom(36, 37, 38, 39,40)
                    .build();

        }

        return InstanceDatabase;

    }

}

