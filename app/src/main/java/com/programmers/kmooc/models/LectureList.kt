package com.programmers.kmooc.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LectureList(
    @SerializedName("pagination")
    val pagination: Pagination?,
    @SerializedName("results")
    var lectures: List<Lecture>
) : Serializable {
    companion object {
        val EMPTY = LectureList(null, emptyList())
    }
}

data class Pagination (
    @SerializedName("count")
    val count: Int,
    @SerializedName("num_pages")
    val numPages: Int,
    @SerializedName("previous")
    val previous: String,
    @SerializedName("next")
    val next: String
)
