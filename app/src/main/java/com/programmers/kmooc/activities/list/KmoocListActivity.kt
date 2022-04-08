package com.programmers.kmooc.activities.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programmers.kmooc.Const.const
import com.programmers.kmooc.KmoocApplication
import com.programmers.kmooc.R
import com.programmers.kmooc.activities.detail.KmoocDetailActivity
import com.programmers.kmooc.databinding.ActivityKmookListBinding
import com.programmers.kmooc.models.Lecture
import com.programmers.kmooc.utils.toVisibility
import com.programmers.kmooc.viewmodels.KmoocListViewModel
import com.programmers.kmooc.viewmodels.KmoocListViewModelFactory

class KmoocListActivity : AppCompatActivity() {
    private var page : Int = 1
    private lateinit var lifecycleowner : LifecycleOwner
    private lateinit var binding: ActivityKmookListBinding
    private lateinit var viewModel: KmoocListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleowner = this
        val kmoocRepository = (application as KmoocApplication).kmoocRepository
        viewModel = ViewModelProvider(this, KmoocListViewModelFactory(kmoocRepository)).get(
            KmoocListViewModel::class.java
        )
        binding = ActivityKmookListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = LecturesAdapter()
            .apply { onClick = this@KmoocListActivity::startDetailActivity }
        binding.lectureList.layoutManager = LinearLayoutManager(this)
        binding.lectureList.adapter = adapter
        viewModel.list()

        binding.lectureList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount-1

                // 스크롤이 끝에 도달했는지 확인
                if(const.Page < const.maxPage) {
                    if (!binding.lectureList.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                        const.Page++
                        viewModel.list()
                    }
                }
            }
        })
        viewModel.LecturelistLiveData.observe(this, Observer {
            (binding.lectureList.adapter as LecturesAdapter).updateLectures(it.lectures)
            binding.pullToRefresh.setOnRefreshListener {
                (binding.lectureList.adapter as LecturesAdapter).updateLectures(it.lectures)
                binding.pullToRefresh.isRefreshing = false
            }
        })
        viewModel.progressVisible.observe(this, Observer {
            binding.progressBar.visibility = it.toVisibility()
        })
    }

    private fun startDetailActivity(lecture: Lecture) {
        startActivity(
            Intent(this, KmoocDetailActivity::class.java)
                .apply { putExtra(KmoocDetailActivity.INTENT_PARAM_COURSE_ID, lecture.id) }
        )
    }
}
