package com.alian.mvi.repository

import com.alian.mvi.model.Blog
import com.alian.mvi.retrofit.BlogRetrofit
import com.alian.mvi.retrofit.NetworkMapper
import com.alian.mvi.room.BlogDao
import com.alian.mvi.room.CacheMapper
import com.alian.mvi.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {

    suspend fun getBlog(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        try {
            val networkBlogs = blogRetrofit.get()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            // cache the data
            for (blog in blogs) {
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cacheBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cacheBlogs)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}
