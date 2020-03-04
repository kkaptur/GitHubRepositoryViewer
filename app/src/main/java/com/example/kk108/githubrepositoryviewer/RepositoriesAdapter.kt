package com.example.kk108.githubrepositoryviewer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kk108.githubrepositoryviewer.data.GitHubRepository
import kotlinx.android.synthetic.main.repo_item_layout.view.*

class RepositoriesAdapter() : RecyclerView.Adapter<RepositoryItemViewHolder>() {

    fun setItems(newItems: MutableList<GitHubRepository>) = items.addAll(newItems)
    fun clearItems() = items.clear()
    private val items: MutableList<GitHubRepository> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryItemViewHolder {
        val repoView = LayoutInflater.from(parent.context).inflate(R.layout.repo_item_layout, parent, false) as ViewGroup
        return RepositoryItemViewHolder(repoView)
    }

    override fun getItemCount(): Int =
            items.size


    override fun onBindViewHolder(holder: RepositoryItemViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.repo_item_title.text = item.name
        holder.itemView.repo_item_description.text = item.description
    }
}