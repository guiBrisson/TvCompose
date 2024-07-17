package me.brisson.tvcompose.domain.repository

import arrow.core.Either
import me.brisson.tvcompose.domain.DomainError
import me.brisson.tvcompose.domain.model.Movie

interface MovieRepository {
    suspend fun getCategoryList(): Either<DomainError, List<String>>
    suspend fun getFeaturedMovieList(): Either<DomainError, List<Movie>>
    suspend fun findMovieById(id: Long): Either<DomainError, Movie?>
    suspend fun getMovieListByCategory(category: String): Either<DomainError, List<Movie>>
}
