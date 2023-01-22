package com.example.binsearcher.data.database

import android.content.Context
import androidx.room.Room
import com.example.binsearcher.data.database.History.HistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideDataBaseRoom(@ApplicationContext context: Context): DataBase =
        Room.databaseBuilder(context.applicationContext, DataBase::class.java, "history_database")
            .fallbackToDestructiveMigration().allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun provideHistoryDao(dataBase: DataBase): HistoryDao = dataBase.historyDao()
}