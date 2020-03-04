package com.example.kk108.githubrepositoryviewer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kk108.githubrepositoryviewer.LoadingReposStatus.*
import com.example.kk108.githubrepositoryviewer.data.GitHubRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class GitHubViewerViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private var retrofitService: RetrofitService = RetrofitService()
    private var pageNumber = 1

    val reposList = listOf<GitHubRepository>()
    private val dataLoadingStatus = MutableLiveData<LoadingReposStatus>()
    fun dataLoadingStatus(): LiveData<LoadingReposStatus> = dataLoadingStatus
    private val repositoriesListChange = MutableLiveData<List<GitHubRepository>>()
    fun repositoriesListChange(): LiveData<List<GitHubRepository>> = repositoriesListChange

    fun searchRepository(textInput: String) = searchRepo { retrofitService.findRepo(textInput, pageNumber) }
    fun searchUser(textInput: String) = searchRepo { retrofitService.getUsersRepos(textInput, pageNumber) }

    private fun searchRepo(function: () -> Observable<List<GitHubRepository>>?) {
        function.invoke()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe { dataLoadingStatus.value = LOADING }
                ?.subscribe(
                        {
                            dataLoadingStatus.value =
                                    if (it.isNotEmpty()) LOADED
                                    else EMPTY
                            repositoriesListChange.value = it
                        },
                        {
                            dataLoadingStatus.value = EMPTY
                            Log.e(APP_TAG, it.toString())
                        }
                )?.addTo(compositeDisposable)
    }
}

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(compositeDisposable)
}

enum class LoadingReposStatus { LOADED, EMPTY, LOADING }