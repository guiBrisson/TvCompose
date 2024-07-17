package me.brisson.tvcompose.domain

sealed interface DomainError {
    val message: String
}