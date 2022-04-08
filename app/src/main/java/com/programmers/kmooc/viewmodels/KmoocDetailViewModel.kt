package com.programmers.kmooc.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.programmers.kmooc.Const.const
import com.programmers.kmooc.models.Lecture
import com.programmers.kmooc.models.LectureList
import com.programmers.kmooc.repositories.KmoocRepository

class KmoocDetailViewModel(private var repository: KmoocRepository) : ViewModel() {
    var LecturedetailLiveData = MutableLiveData<Lecture>()
    init {
        repository = KmoocRepository()
        LecturedetailLiveData = repository.getdetailLectureLiveData()
    }
    fun detail(courseId: String) {
        repository.detail(const.serviceKey,courseId) { this.LecturedetailLiveData.postValue(it)
        }
    }
}

class KmoocDetailViewModelFactory(private val repository: KmoocRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KmoocDetailViewModel::class.java)) {
            return KmoocDetailViewModel(repository) as T
        }
        throw IllegalAccessException("Unkown Viewmodel Class")
    }
}