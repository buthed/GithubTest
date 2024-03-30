package com.tematihonov.githubtest.data.network

import com.tematihonov.githubtest.domain.models.responseSearch.ResponseSearch
import com.tematihonov.githubtest.domain.models.responseUser.ResponseUser
import com.tematihonov.githubtest.utils.RetrofitConstants.SEARCH
import com.tematihonov.githubtest.utils.RetrofitConstants.USERS
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(SEARCH)
    suspend fun getSearchUsers(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("q") query: String,
    ): ResponseSearch

    @GET("${USERS}{userLogin}")
    suspend fun getUser(
        @Path("userLogin") userLogin: String
    ): ResponseUser
}