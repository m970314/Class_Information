package com.programmers.kmooc.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Lecture(
    @SerializedName("id")
    val id: String,                 // 아이디
    @SerializedName("number")
    val number: String,             // 강좌번호
    @SerializedName("name")
    val name: String,               // 강좌명
    @SerializedName("classfy_name")
    val classfyName: String,        // 강좌분류
    @SerializedName("middle_classfy_name")
    val middleClassfyName: String,  // 강좌분류2
    @SerializedName("short_description")
    val shortDescription: String,   // 짧은 설명
    @SerializedName("org_name")
    val orgName: String,            // 운영기관
    @SerializedName("start")
    val start: Date,                // 운영기간 시작
    @SerializedName("end")
    val end: Date,                  // 운영기간 종료
    @SerializedName("teachers")
    val teachers: String? = null,   // 교수진
    @SerializedName("overview")
    val overview: String? = null,   // 상제정보(html)
    @SerializedName("media")
    val media: Media?
) : Serializable {
    companion object {
        val EMPTY = Lecture(
            "", "", "", "", "",
            "", "", Date(), Date(), null, null, null
        )
    }
}

data class Media(
    @SerializedName("image")
    val image: Image
)

data class Image(
    @SerializedName("small")
    val small: String,           // 강좌 썸네일 (media>image>small)
    @SerializedName("large")
    val large: String,           // 강좌 이미지 (media>image>large)
)