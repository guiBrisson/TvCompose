package me.brisson.tvcompose.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.brisson.tvcompose.data.remote.MovieRepositoryImpl
import me.brisson.tvcompose.domain.repository.MovieRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bidsMovieRepository(
        movieRepository: MovieRepositoryImpl
    ): MovieRepository

}
