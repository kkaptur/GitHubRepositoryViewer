package com.example.kk108.githubrepositoryviewer.data

import com.google.gson.annotations.SerializedName

class GitHubSearchEntity(
        @SerializedName("items")
        val repos: List<GitHubRepositoryEntity?>?
)