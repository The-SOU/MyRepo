package com.sou.myrepo.utility

import com.sou.myrepo.data.Url
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtil {
    val githubApiService: GithubApiService by lazy { getGithubRetrofit().create(GithubApiService::class.java) }

    private fun getGithubRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Url.GITHUB_API_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }
}