package com.example.binsearcher.data.database.History

import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val historyDao: HistoryDao
) {

    fun addBin(historyEntity: HistoryEntity) {
        historyDao.insertBinToHistory(historyEntity)
    }

    fun getHistory(): List<HistoryEntity> {
        return historyDao.getHistory()
    }
}