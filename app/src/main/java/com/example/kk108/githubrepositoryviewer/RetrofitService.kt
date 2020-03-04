package com.example.kk108.githubrepositoryviewer

import android.util.Log
import com.example.kk108.githubrepositoryviewer.data.ApiInterface
import com.example.kk108.githubrepositoryviewer.data.fromEntity
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {

    companion object {
        private val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        var gitHubApi = retrofit?.create(ApiInterface::class.java)
    }

    fun getUsersRepos(name: String, pageNumber: Int) =
            gitHubApi?.getUsersReposList(name, pageNumber, REPOS_PER_PAGE)?.map { fromEntity(it) }

    fun findRepo(name: String, pageNumber: Int) =
            gitHubApi?.findRepo(name, pageNumber, REPOS_PER_PAGE)?.map { fromEntity(it) }

}

const val REPOS_PER_PAGE = 10