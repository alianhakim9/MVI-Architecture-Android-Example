package com.alian.mvi.di

import com.alian.mvi.repository.MainRepository
import com.alian.mvi.retrofit.BlogRetrofit
import com.alian.mvi.retrofit.NetworkMapper
import com.alian.mvi.room.BlogDao
import com.alian.mvi.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(
        blogDao: BlogDao,
        blogRetrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository {
        return MainRepository(
            blogDao, blogRetrofit, cacheMapper, networkMapper
        )
    }
}