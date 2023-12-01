package com.example.jsonwithretrofit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SubCategoryDao {
    @Query("SELECT * FROM SubCategory")
    fun getAllSubCategories(): List<SubCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(subCategories: List<SubCategory>)
}