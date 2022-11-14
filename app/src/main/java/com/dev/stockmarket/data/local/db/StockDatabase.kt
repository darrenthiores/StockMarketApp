package com.dev.stockmarket.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dev.stockmarket.data.local.dao.StockDao
import com.dev.stockmarket.data.local.dto.CompanyListingEntity

@Database(
    entities = [CompanyListingEntity::class],
    version = 1
)
abstract class StockDatabase: RoomDatabase() {

    abstract fun stockDao(): StockDao
}