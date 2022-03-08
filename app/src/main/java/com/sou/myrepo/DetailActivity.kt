package com.sou.myrepo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isGone
import com.sou.myrepo.databinding.ActivityDetailBinding
import com.sou.myrepo.extensions.loadImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var binding: ActivityDetailBinding

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    companion object {
        const val BODY_KEY = "BODY_KEY"
        const val AVATAR_URL_KEY = "AVATAR_URL_KEY"
        const val USERNAME_KEY = "USERNAME_KEY"
        const val NUMBER_KEY = "NUMBER_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val issueOwner = intent.getStringExtra(USERNAME_KEY) ?: kotlin.run {
            toast("Issue Owner 이름이 없습니다.")
            finish()
            return
        }

        val ownerProfile = intent.getStringExtra(AVATAR_URL_KEY) ?: kotlin.run {
            toast("Issue Owner 프로필이 없습니다.")
            finish()
            return
        }
        val issueDescription = intent.getStringExtra(BODY_KEY) ?: kotlin.run {
            toast("Issue Description 이 없습니다.")
            finish()
            return
        }
        val issueNumber = intent.getStringExtra(NUMBER_KEY) ?: kotlin.run {
            toast("Issue Number 이 없습니다.")
            finish()
            return
        }

        launch {
            setData(issueOwner, ownerProfile, issueDescription)

        }

        supportActionBar?.setTitle("#${issueNumber}")

        showLoading(true)
    }

    private fun setData(name: String, profile: String, description: String) = with(binding) {
        ownerProfileImageView.loadImage(profile, 42f)
        ownerNameAndRepoNameTextview.text = name
        descriptionTextView.text = description
    }

    private fun showLoading(isShown: Boolean) = with(binding) {
        progressBar.isGone = isShown
    }

    private fun Context.toast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}