package com.sou.myrepo.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sou.myrepo.data.response.GithubIssueResponse
import com.sou.myrepo.databinding.ViewholderBannerItemBinding
import com.sou.myrepo.databinding.ViewholderRepositoryItemBinding

class RepositoryRecyclerAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val context = context

    companion object {
        const val CONTENT_TYPE: Int = 0
        const val BANNER_TYPE: Int = 1
    }

    private var repositoryList: List<GithubIssueResponse> = listOf()
    private lateinit var repositoryClickListener: (GithubIssueResponse) -> Unit

    inner class RepositoryItemViewHolder(
        private val binding: ViewholderRepositoryItemBinding,
        val searchResultClickListener: (GithubIssueResponse) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: GithubIssueResponse) = with(binding) {
            //todo 띄울 데이터 나타내기 (이슈 번호와 타이틀)
            numberTextView.text = "#${data.number.toString()}"
            titleTextView.text = data.title

        }

        fun bindViews(data: GithubIssueResponse) {
            binding.root.setOnClickListener {
                searchResultClickListener(data)
            }
        }
    }

    inner class BannerItemViewHolder(
        private val binding: ViewholderBannerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData() = with(binding) {
            Glide.with(bannerImageView)
                .load("https://i.postimg.cc/3J2zV84P/ad-modak.png")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(bannerImageView)
        }
        fun bindView(context: Context) {
            binding.bannerImageView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/The-SOU"))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            BANNER_TYPE -> {
                val view = ViewholderBannerItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                BannerItemViewHolder(view)
            }
            else -> {
                val view = ViewholderRepositoryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                RepositoryItemViewHolder(view, repositoryClickListener)
            }
        }

    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder.itemViewType) {
            CONTENT_TYPE -> {
                (holder as RepositoryItemViewHolder).bindData(repositoryList[position])
                (holder as RepositoryItemViewHolder).bindViews(repositoryList[position])
            }
            BANNER_TYPE -> {
                (holder as BannerItemViewHolder).bindData()
                (holder as BannerItemViewHolder).bindView(context)
            }

        }


    }

    override fun getItemCount(): Int = repositoryList.size

    override fun getItemViewType(position: Int): Int {
        if (position == 4) {
            return BANNER_TYPE
        }
        return CONTENT_TYPE
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setRepositoryList(
        searchResultList: List<GithubIssueResponse>,
        searchResultClickListener: (GithubIssueResponse) -> Unit
    ) {
        this.repositoryList = searchResultList
        this.repositoryClickListener = searchResultClickListener
        notifyDataSetChanged()
    }

}