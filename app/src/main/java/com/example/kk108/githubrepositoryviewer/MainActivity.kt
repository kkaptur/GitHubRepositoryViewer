package com.example.kk108.githubrepositoryviewer

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kk108.githubrepositoryviewer.LoadingReposStatus.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: GitHubViewerViewModel
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RepositoriesAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(GitHubViewerViewModel::class.java)
        viewManager = LinearLayoutManager(this)
        viewAdapter = RepositoriesAdapter()
        recyclerView = repos_recycler_view.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.dataLoadingStatus().observe(this, Observer {
            with(repos_info_view) {
                when (it) {
                    LOADED -> visibility = GONE
                    EMPTY -> {
                        text = context.getString(R.string.no_repos_found)
                        visibility = VISIBLE
                    }
                    LOADING -> {
                        text = context.getString(R.string.loading_state)
                        visibility = VISIBLE
                    }
                }
            }

        })
        viewModel.repositoriesListChange().observe(this, Observer {
            viewAdapter.setItems(it.toMutableList())
            viewAdapter.notifyDataSetChanged()
        })
    }

    private fun setupListeners() {
        search_user_button.setOnClickListener {
            viewAdapter.clearItems()
            viewModel.searchUser(repos_edit_text.text.toString())
        }
        search_repo_button.setOnClickListener {
            viewAdapter.clearItems()
            viewModel.searchRepository(repos_edit_text.text.toString())
        }
    }
}

const val APP_TAG = "GITHUB_VIEWER_LOG"