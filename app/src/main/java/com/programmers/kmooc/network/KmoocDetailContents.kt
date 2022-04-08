package com.programmers.kmooc.network

import com.programmers.kmooc.models.Lecture
import com.programmers.kmooc.models.LectureList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface KmoocDetailContents {
    @GET(value = "courseDetail")
    fun searchdetailkmooc(
        @Query(value = "serviceKey", encoded = true) servicekey: String,
        @Query(value = "CourseId", encoded = true) CourseId: String,
    ): Call<Lecture>
}