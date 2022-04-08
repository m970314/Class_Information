package com.programmers.kmooc.activities.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.programmers.kmooc.KmoocApplication
import com.programmers.kmooc.R
import com.programmers.kmooc.activities.list.LecturesAdapter
import com.programmers.kmooc.databinding.ActivityKmookDetailBinding
import com.programmers.kmooc.models.Lecture
import com.programmers.kmooc.network.ImageLoader
import com.programmers.kmooc.utils.DateUtil
import com.programmers.kmooc.utils.toVisibility
import com.programmers.kmooc.viewmodels.KmoocDetailViewModel
import com.programmers.kmooc.viewmodels.KmoocDetailViewModelFactory

class KmoocDetailActivity : AppCompatActivity() {
    private val imageloder = ImageLoader
    companion object {
        const val INTENT_PARAM_COURSE_ID = "param_course_id"
    }
    private lateinit var binding: ActivityKmookDetailBinding
    private lateinit var viewModel: KmoocDetailViewModel
    private val date = DateUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val kmoocRepository = (application as KmoocApplication).kmoocRepository
        viewModel = ViewModelProvider(this, KmoocDetailViewModelFactory(kmoocRepository)).get(
            KmoocDetailViewModel::class.java
        )
        binding = ActivityKmookDetailBinding.inflate(layoutInflater)
        var id = intent.getStringExtra(INTENT_PARAM_COURSE_ID)
        var webview = binding.root.findViewById<WebView>(R.id.webView)
        viewModel.detail(id!!)
        webview.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
        binding.root.findViewById<View>(R.id.lectureNumber).findViewById<TextView>(R.id.descriptionTitle).text ="강의번호: "
        binding.root.findViewById<View>(R.id.lectureType).findViewById<TextView>(R.id.descriptionTitle).text ="강의분류: "
        binding.root.findViewById<View>(R.id.lectureOrg).findViewById<TextView>(R.id.descriptionTitle).text ="운영기관: "
        binding.root.findViewById<View>(R.id.lectureTeachers).findViewById<TextView>(R.id.descriptionTitle).text ="교수정보: "
        binding.root.findViewById<View>(R.id.lectureDue).findViewById<TextView>(R.id.descriptionTitle).text ="운영기간: "
        viewModel.LecturedetailLiveData.observe(this, Observer {
            val during = date.formatDate(it.start) + " ~ " + date.formatDate(it.end)
            binding.root.findViewById<View>(R.id.lectureNumber).findViewById<TextView>(R.id.description).text = it.number
            binding.root.findViewById<View>(R.id.lectureType).findViewById<TextView>(R.id.description).text =it.classfyName
            binding.root.findViewById<View>(R.id.lectureOrg).findViewById<TextView>(R.id.description).text =it.orgName
            binding.root.findViewById<View>(R.id.lectureTeachers).findViewById<TextView>(R.id.description).text =it.teachers
            binding.root.findViewById<View>(R.id.lectureDue).findViewById<TextView>(R.id.description).text = during
            binding.root.findViewById<ImageView>(R.id.lectureImage).setImageBitmap(imageloder.imagecache[it.media!!.image.large])
            webview.loadData(it.overview!!,"text/html; charset=utf-8","UTF-8")
        })
        setContentView(binding.root)
    }
}