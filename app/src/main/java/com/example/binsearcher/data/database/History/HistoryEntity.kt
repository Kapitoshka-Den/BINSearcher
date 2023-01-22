package com.example.binsearcher.data.database.History

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.binsearcher.data.database.History.HistoryEntity.Companion.HISTORY_NAME


@Entity(tableName = HISTORY_NAME)
data class HistoryEntity (
    @PrimaryKey(autoGenerate = true)
    val Id:Int = 0,

    @ColumnInfo
    val Bin:String
){
    companion object{
        const val HISTORY_NAME = "History"
    }
}