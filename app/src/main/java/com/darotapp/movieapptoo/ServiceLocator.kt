package com.darotapp.movieapptoo

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.darotapp.movieapptoo.data.api.TheMovieDbInterface
import com.darotapp.movieapptoo.data.repository.MovieDetailsRepository

object ServiceLocator {
//
//    private var database: MovieDatabase? = null
    @Volatile
    var movieDetailsRepository:MovieDetailsRepository? = null
        @VisibleForTesting set

    private val lock = Any()
    private val apiService:TheMovieDbInterface?=null

    fun provideMoviesRepository(context: Context): MovieDetailsRepository? {
        synchronized(this) {
            return movieDetailsRepository ?: createMoveRepository(context)
        }

    }

    private fun createMoveRepository(context: Context): MovieDetailsRepository? {
        val newRepo = apiService?.let { MovieDetailsRepository(it) }

        return newRepo
    }
//
//    fun createLocalDataSource(context: Context): LocalDataSourceManager {
//        val database = database ?: createDataBase(context)
//        return LocalDataSourceManager(database.movieDao())
//    }
//
////    fun createLocalDataSourceForFavs(context: Context): LocalDataSourceManager {
////        val database = database ?: createDataBase(context)
////        return LocalDataSourceManager(null, database.favouriteDao())
////    }
//
//    private fun createDataBase(context: Context): MovieDatabase {
//        val result = Room.databaseBuilder(
//            context.applicationContext,
//            MovieDatabase::class.java, "MovieDb"
//        )
//            .fallbackToDestructiveMigration()
//            .build()
//        database = result
//        return result
//    }
}