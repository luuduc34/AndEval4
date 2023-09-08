package com.technifutur.andeval4.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Long = 0,
    val date: Date,
    val name: String,
    val value: Float
)
