package com.sou.myrepo.utility

import com.sou.myrepo.data.response.GithubIssueResponse
import com.sou.myrepo.data.response.GithubRepoSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/repositories")
    suspend fun searchRepositories(@Query("q") query: String): Response<GithubRepoSearchResponse>

    @GET("repos/{org}/{repo}/issues")
    suspend fun getIssues(
        @Path("org") orgName: String,
        @Path("repo") repoName: String
    ): Response<List<GithubIssueResponse>>
}