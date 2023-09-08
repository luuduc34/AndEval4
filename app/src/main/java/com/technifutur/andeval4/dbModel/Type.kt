package com.technifutur.andeval4.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Type(
    @PrimaryKey(autoGenerate = true)
    val typeId: Long = 0,
    val name: String
)
