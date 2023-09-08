package com.technifutur.andeval4.dbDao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.technifutur.andeval4.dbModel.Type

@Dao
interface TypeDao {
    @Query("SELECT * FROM Type")
    fun getAll(): List<Type>
    @Query("SELECT * FROM Type WHERE typeId IN (:typeIds)" )
    fun loadAllByIds(typeIds: IntArray): List<Type>
    @Query("SELECT * FROM Type WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Type
    @Insert(onConflict = OnConflictStrategy .REPLACE)
    fun insert(type: Type)
    @Insert
    fun insertAll(vararg types: Type)
    @Delete
    fun delete(type: Type)
}