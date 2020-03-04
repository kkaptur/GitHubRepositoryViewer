package com.example.kk108.githubrepositoryviewer.data

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {
    @GET("users/{user}/repos")
    fun getUsersReposList(
            @Path("user") user: String,
            @Query("page") pageNumber: Int,
            @Query("per_page") perPage: Int): Observable<List<GitHubRepositoryEntity>>

    @GET("search/repositories")
    fun findRepo(
            @Query("q") name: String,
            @Query("page") pageNumber: Int,
            @Query("per_page") perPage: Int): Observable<GitHubSearchEntity>
}