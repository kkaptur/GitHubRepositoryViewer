package com.example.kk108.githubrepositoryviewer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.kk108.githubrepositoryviewer.data.GitHubRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        var viewModel = ViewModelProvider(this).get(GitHubViewerViewModel::class.java)

        var repos: List<GitHubRepository>
        search_repo_button.setOnClickListener {
            RetrofitService().getReposList("eugenp")
                    ?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
                    ?.doOnSubscribe { Log.i(APP_TAG, "soOnSubscribe") }
                    ?.subscribe(
                            {
                                repos = it
                                Log.i(APP_TAG, "onSuccess")
                                Log.i(APP_TAG, "onSuccess" + it.toString())

                            },
                            {
                                Log.e(APP_TAG, "error")
                            }
                    )
        }
    }
}


const val APP_TAG = "GITHUB_VIEWER_LOG"