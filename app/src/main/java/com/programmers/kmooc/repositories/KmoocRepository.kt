package com.programmers.kmooc.repositories


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.programmers.kmooc.Const.const
import com.programmers.kmooc.models.Lecture
import com.programmers.kmooc.models.LectureList
import com.programmers.kmooc.network.KmoocContents
import com.programmers.kmooc.network.KmoocDetailContents
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KmoocRepository {

    /**
     * 국가평생교육진흥원_K-MOOC_강좌정보API
     * https://www.data.go.kr/data/15042355/openapi.do
     */
    private var kmmocinfoMutableLiveData: MutableLiveData<LectureList> = MutableLiveData()
    private var kmmocdetailinfoMutableLiveData: MutableLiveData<Lecture> = MutableLiveData()
    private var kmooccontents: KmoocContents
    private var kmoocdetailcontents: KmoocDetailContents
    companion object {
        private const val BASE_URL = "http://apis.data.go.kr/B552881/kmooc/"
    }
    init {
        val gson: Gson = GsonBuilder().setLenient().create()

        kmooccontents = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(KmoocContents::class.java)
        kmoocdetailcontents = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(KmoocDetailContents::class.java)
    }
    fun list(servicekey: String, Page: String, Org: String, Mobile : String, completed: (LectureList) -> Unit) {
        kmooccontents.searchkmooc(servicekey, Page, Org,Mobile)
            .enqueue(object : Callback<LectureList?> {
                override fun onResponse(
                    call: Call<LectureList?>,
                    response: Response<LectureList?>
                ) {
                    kmmocinfoMutableLiveData.postValue(response.body())
                    Log.d("asdfg",const.Page.toString())
                    completed(response.body()!!)
                }
                override fun onFailure(call: Call<LectureList?>, t: Throwable) {

                }
            })
    }

    fun next(currentPage: LectureList, completed: (LectureList) -> Unit) {

    }

    fun detail(servicekey: String,courseId: String, completed: (Lecture) -> Unit) {
        kmoocdetailcontents.searchdetailkmooc(servicekey,courseId)
            .enqueue(object : Callback<Lecture?> {
                override fun onResponse(
                    call: Call<Lecture?>,
                    response: Response<Lecture?>
                ) {
                    kmmocdetailinfoMutableLiveData.postValue(response.body())
                    completed(response.body()!!)
                }
                override fun onFailure(call: Call<Lecture?>, t: Throwable) {

                }
            })
    }
    fun getLectureLiveData(): MutableLiveData<LectureList> {
        return this.kmmocinfoMutableLiveData
    }
    fun getdetailLectureLiveData(): MutableLiveData<Lecture> {
        return this.kmmocdetailinfoMutableLiveData
    }
}