package com.example.kk108.githubrepositoryviewer.data

import com.google.gson.annotations.SerializedName

class GitHubRepositoryEntity(
        @SerializedName("description")
        val description: String?,
        @SerializedName("name")
        val name: String?
)
