package com.tuccro.githubreader.presentation.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tuccro.githubreader.GetRepositoriesQuery
import com.tuccro.githubreader.R
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoriesListAdapter() :
    PagedListAdapter<GetRepositoriesQuery.Repository, RepositoriesListAdapter.RepositoryViewHolder>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        getItem(position)?.let { holder.bindRepository(it) }
    }

    class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText = itemView.tv_name
        private val languageText = itemView.tv_language
        private val forksText = itemView.tv_forks
        private val starsText = itemView.tv_stars

        fun bindRepository(repository: GetRepositoriesQuery.Repository) {
            with(repository) {
                nameText.text = name
                languageText.text =
                    itemView.resources.getString(R.string.language, primaryLanguage?.name)
                forksText.text = forkCount.toString()
                starsText.text = stargazers.totalCount.toString()
            }
        }
    }

    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<GetRepositoriesQuery.Repository> =
            object : DiffUtil.ItemCallback<GetRepositoriesQuery.Repository>() {
                override fun areItemsTheSame(
                    oldItem: GetRepositoriesQuery.Repository,
                    newItem: GetRepositoriesQuery.Repository
                ): Boolean {
                    return oldItem.url === newItem.url
                }

                override fun areContentsTheSame(
                    oldItem: GetRepositoriesQuery.Repository,
                    newItem: GetRepositoriesQuery.Repository
                ): Boolean {
                    return oldItem.equals(newItem)
                }
            }
    }
}