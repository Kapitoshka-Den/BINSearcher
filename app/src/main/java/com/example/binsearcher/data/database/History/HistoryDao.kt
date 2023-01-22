package com.example.binsearcher.data.database.History

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.binsearcher.data.database.History.HistoryEntity.Companion.HISTORY_NAME

@Dao
interface HistoryDao {

    @Insert
    fun insertBinToHistory(entity: HistoryEntity)

    @Query("SELECT * from $HISTORY_NAME")
    fun getHistory():List<HistoryEntity>

}