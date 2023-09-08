package com.technifutur.andeval4.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.technifutur.andeval4.dbDao.ExpTypeDao
import com.technifutur.andeval4.dbDao.ExpenseDao
import com.technifutur.andeval4.dbDao.TypeDao
import com.technifutur.andeval4.dbModel.Converters
import com.technifutur.andeval4.dbModel.ExpType
import com.technifutur.andeval4.dbModel.Expense
import com.technifutur.andeval4.dbModel.Type

@Database(entities = [Expense::class, Type::class, ExpType::class],version = 1)
@TypeConverters(Converters::class)
abstract class DataBase: RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun typeDao(): TypeDao
    abstract fun expTypeDao(): ExpTypeDao
}