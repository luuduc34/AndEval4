package com.technifutur.andeval4.dbDao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.technifutur.andeval4.dbModel.Expense
import kotlinx.coroutines.flow.Flow
@Dao
interface ExpenseDao {
    @Query("SELECT * FROM Expense")
    fun getAll(): LiveData<List<Expense>> // live update with LiveData
    @Query("SELECT * FROM Expense WHERE expenseId IN (:expenseIds)")
    fun loadAllByIds(expenseIds: IntArray): List<Expense>
    @Query("SELECT * FROM Expense WHERE expenseId LIKE :id LIMIT 1")
    fun getById(id: Long): Expense
    @Query("SELECT * FROM Expense WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Expense
    @Insert(onConflict = OnConflictStrategy .REPLACE)
    fun insert(expense: Expense)
    @Insert
    fun insertAll(vararg expenses: Expense)
    @Delete
    fun delete(expense: Expense)
}