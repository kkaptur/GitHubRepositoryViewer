package com.example.kk108.githubrepositoryviewer.data

import android.util.Log

data class GitHubRepository(
        val description: String?,
        val name: String?
)

fun fromEntity(from: List<GitHubRepositoryEntity?>?): List<GitHubRepository> = from?.let { repos ->
    if (repos.isNotEmpty()) {
        repos.map {
            it?.let { repo ->
                GitHubRepository(
                        description = repo.description ?: EMPTY_STRING,
                        name = repo.name ?: EMPTY_STRING
                )
            }
                    ?: throw Exception("Cannot parse $from").also { Log.e("MAPPING_EXCEPTION", "Cannot parse $from") }
        }
    } else emptyList()
} ?: emptyList()

fun fromEntity(from: GitHubSearchEntity?): List<GitHubRepository> = from?.let { searchEntity ->
    if (searchEntity.repos != null && searchEntity.repos.isNotEmpty()) {
        fromEntity(searchEntity.repos)
    } else emptyList()
} ?: emptyList()

const val EMPTY_STRING = "void"