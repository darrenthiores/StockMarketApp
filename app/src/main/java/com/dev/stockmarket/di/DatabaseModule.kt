package com.dev.stockmarket.di

import android.content.Context
import androidx.room.Room
import com.dev.stockmarket.data.local.db.StockDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): StockDatabase =
        Room.databaseBuilder(
            context,
            StockDatabase::class.java,
            "stock.db"
        ).fallbackToDestructiveMigration()
            .build()

}