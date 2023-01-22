package com.example.binsearcher.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.binsearcher.data.database.History.HistoryDao
import com.example.binsearcher.data.database.History.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}