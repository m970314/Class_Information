package com.programmers.kmooc.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.programmers.kmooc.Const.const
import com.programmers.kmooc.models.LectureList
import com.programmers.kmooc.repositories.KmoocRepository

class KmoocListViewModel(private var repository: KmoocRepository) : ViewModel() {
    var LecturelistLiveData = MutableLiveData<LectureList>()
    var progressVisible = MutableLiveData<Boolean>()
    init {
        repository = KmoocRepository()
        LecturelistLiveData = repository.getLectureLiveData()
    }
    fun list() {
        progressVisible.postValue(true)
        repository.list(
            const.serviceKey,
                const.Page.toString(),
                const.Org,
                const.Mobile.toString()) {
            progressVisible.postValue(false)
        }
    }

    fun next() {
        val currentLectureList = LectureList.EMPTY
        repository.next(currentLectureList) { lectureList ->
        }
    }
}

class KmoocListViewModelFactory(private val repository: KmoocRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KmoocListViewModel::class.java)) {
            return KmoocListViewModel(repository) as T
        }
        throw IllegalAccessException("Unkown Viewmodel Class")
    }
}