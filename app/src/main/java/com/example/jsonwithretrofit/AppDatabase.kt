package com.example.jsonwithretrofit

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SubCategory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subCategoryDao(): SubCategoryDao

    companion object {
        private var instance: AppDatabase? = null

       @Synchronized
        fun getInstance(ctx: Context): AppDatabase {
            if (instance == null){
                instance = Room.databaseBuilder(
                    ctx.applicationContext, AppDatabase::class.java,
                    "HelloDb"
                ).allowMainThreadQueries().
                build()
        }

            return instance!!
        }
    }
}
