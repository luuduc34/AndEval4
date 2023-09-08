package com.technifutur.andeval4.dbModel

import androidx.room.Entity
@Entity(primaryKeys = ["expenseId", "typeId"])
data class ExpType(
    var expenseId: Long,
    var typeId: Long
)
