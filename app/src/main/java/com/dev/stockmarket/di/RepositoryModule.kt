package com.dev.stockmarket.di

import com.dev.stockmarket.data.repository.StockRepositoryImpl
import com.dev.stockmarket.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        NetworkModule::class,
        DatabaseModule::class
    ]
)
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(
        repositoryImpl: StockRepositoryImpl
    ): StockRepository
}