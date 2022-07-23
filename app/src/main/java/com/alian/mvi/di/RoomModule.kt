package com.alian.mvi.di

import android.content.Context
import androidx.room.Room
import com.alian.mvi.room.BlogDao
import com.alian.mvi.room.BlogDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Provides
    @Singleton
    fun provideBlogDb(@ApplicationContext context: Context): BlogDatabase {
        return Room.databaseBuilder(
            context, BlogDatabase::class.java, BlogDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideBlogDao(database: BlogDatabase): BlogDao {
        return database.blogDao()
    }
}