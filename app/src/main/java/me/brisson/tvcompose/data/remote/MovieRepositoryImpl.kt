package me.brisson.tvcompose.data.remote

import arrow.core.Either
import arrow.core.raise.either
import me.brisson.tvcompose.domain.DomainError
import me.brisson.tvcompose.domain.model.Movie
import me.brisson.tvcompose.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
) : MovieRepository {
    override suspend fun getCategoryList(): Either<DomainError, List<String>> = either {
        movieApi.loadCategories()
    }

    override suspend fun getFeaturedMovieList(): Either<DomainError, List<Movie>> = either {
        movieApi.loadFeaturedMovieList()
    }

    override suspend fun findMovieById(id: Long): Either<DomainError, Movie?> = either {
        movieApi.findMovieById(id)
    }

    override suspend fun getMovieListByCategory(
        category: String,
    ): Either<DomainError, List<Movie>> = either {
        movieApi.getMovieListByCategory(category)
    }
}
