package com.programmers.kmooc.network

import com.programmers.kmooc.models.LectureList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface KmoocContents {
    @GET(value = "courseList")
    fun searchkmooc(
        @Query(value = "serviceKey", encoded = true) servicekey: String,
        @Query(value = "Page", encoded = true) Page: String,
        @Query(value = "Org", encoded = true) Org: String,
        @Query(value = "Mobile", encoded = true) Mobile: String,
    ): Call<LectureList>
}