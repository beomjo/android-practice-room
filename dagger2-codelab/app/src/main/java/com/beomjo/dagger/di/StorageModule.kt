package com.beomjo.dagger.di

import com.beomjo.dagger.storage.SharedPreferencesStorage
import com.beomjo.dagger.storage.Storage
import dagger.Binds
import dagger.Module

@Module
abstract class StorageModule {

    @Binds
    abstract fun provideStorage(storage: SharedPreferencesStorage): Storage
}