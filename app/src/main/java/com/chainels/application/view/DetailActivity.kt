package com.chainels.application.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.chainels.application.databinding.ActivityDetailBinding
import com.chainels.application.model.TimeLine
import com.chainels.application.utils.CheckValidation
import com.chainels.application.viewmodel.TimelineDetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var timelineViewModel: TimelineDetailViewModel
    private var msgId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activity = this

        setupUI()
        setObserver()
    }

    private fun setupUI() {

        timelineViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(application)
        )[TimelineDetailViewModel::class.java]

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            msgId = bundle.getLong("msgId")
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    private fun setObserver() {

        if (CheckValidation.isConnected(this)) {
            timelineViewModel.callDetailTimeline(msgId)
        }

        timelineViewModel.getTimelineDetail().observe(this) {
            if (it is TimeLine) {
                binding.lifecycleOwner = this
                binding.apply {
                    viewModelDetail = timelineViewModel
                    modelDetail = it
                }
                binding.txtContent.text = HtmlCompat.fromHtml(it.content!!, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}