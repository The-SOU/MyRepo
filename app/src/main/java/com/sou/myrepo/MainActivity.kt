package com.sou.myrepo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.sou.myrepo.data.response.GithubIssueResponse
import com.sou.myrepo.databinding.ActivityMainBinding
import com.sou.myrepo.databinding.DialogRepoBinding
import com.sou.myrepo.utility.RetrofitUtil
import com.sou.myrepo.view.RepositoryRecyclerAdapter
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var binding: ActivityMainBinding

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var adapter: RepositoryRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initViews()
        bindViews()
    }

    private fun initAdapter() = with(binding) {
        adapter = RepositoryRecyclerAdapter(applicationContext)
    }

    private fun initViews() = with(binding) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun bindViews() = with(binding) {
        binding.titleTextView.setOnClickListener {
            showPopUp()
        }
    }

    private fun searchKeyboard(org: String, repo: String) = launch {
        withContext(Dispatchers.IO) {
            val response = RetrofitUtil.githubApiService.getIssues(org, repo)
            if (response.isSuccessful) {
                val body = response.body()
                withContext(Dispatchers.Main) {
                    Log.d("response", body.toString())
                    body?.let {
                        setData(it)
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setMessage("해당 Repository를 찾을 수 없습니다.")
                .setPositiveButton("확인") { _, _ ->
                }
                .show()
        }
    }


    private fun showPopUp() {
        val builder = AlertDialog.Builder(this)
        val buildItem = DialogRepoBinding.inflate(layoutInflater)
        val orgText = buildItem.orgEditText.text
        val repoText = buildItem.repoEdittext.text

        with(builder) {
            setView(buildItem.root)
            setPositiveButton("확인") { _, _ ->
                searchKeyboard(orgText.toString(), repoText.toString())
                Log.d("showpopup", "${orgText.toString()}, ${repoText.toString()}")
                binding.titleTextView.text = "${orgText.toString()} / ${repoText.toString()}"
            }
                .show()
        }
    }

    private fun setData(items: List<GithubIssueResponse>) {
        adapter.setRepositoryList(items) {
            startActivity(
                Intent(this, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.BODY_KEY, it.description)
                    putExtra(DetailActivity.AVATAR_URL_KEY, it.items.avatarUrl)
                    putExtra(DetailActivity.USERNAME_KEY, it.items.userName)
                    putExtra(DetailActivity.NUMBER_KEY, it.number.toString())
                }
            )
        }
    }
}