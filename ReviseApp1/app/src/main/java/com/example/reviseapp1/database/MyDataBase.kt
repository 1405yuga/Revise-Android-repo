package com.example.reviseapp1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataBaseModel::class], version = 1, exportSchema = false)
abstract class MyDataBase : RoomDatabase(){

    abstract fun databaseDao() : DatabaseDao

    companion object{
        @Volatile
        private var DB_INSTANCE: MyDataBase? = null

        fun getDatabase(context: Context): MyDataBase{
            return DB_INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context,MyDataBase::class.java,"my_database")
                    .fallbackToDestructiveMigration()
                    .build()

                DB_INSTANCE= instance
                instance
            }
        }
    }
}