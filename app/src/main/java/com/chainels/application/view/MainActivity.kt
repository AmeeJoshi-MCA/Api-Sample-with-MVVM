package com.chainels.application.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.chainels.application.R
import com.chainels.application.adapter.TimelineAdapter
import com.chainels.application.databinding.ActivityMainBinding
import com.chainels.application.model.TimeLine
import com.chainels.application.utils.CheckValidation
import com.chainels.application.viewmodel.TimelineViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var timelineAdapter: TimelineAdapter
    private lateinit var timelineViewModel: TimelineViewModel
    private lateinit var listTimeline: MutableList<TimeLine>
    private var lastPositionScroll: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activity = this

        setupUI()
        setData()
        setObserver()

    }

    private fun setupUI() {

        timelineViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(application)
        )[TimelineViewModel::class.java]

        binding.apply {
            viewModel = timelineViewModel
        }

        binding.lifecycleOwner = this

        listTimeline = mutableListOf()

        timelineAdapter = TimelineAdapter(listTimeline, timelineViewModel)

        binding.swipeContainer.setOnRefreshListener {
            setData()
        }

        binding.recyclerViewTimeline.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && dy != 0) {
                    // Load more results here
                    lastPositionScroll = listTimeline.size
                    timelineViewModel.callTimelineToLoadMoreData(listTimeline.last().score)
                }
            }
        })
    }

    private fun setData() {
        binding.swipeContainer.isRefreshing = true
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewTimeline.itemAnimator = null

        if (CheckValidation.isConnected(this)) {
            binding.recyclerViewTimeline.visibility = View.VISIBLE
            timelineViewModel.timelineFirstResponse.observe(this) { t ->
                listTimeline.clear()
                t?.let { listTimeline.addAll(it) }
                binding.recyclerViewTimeline.adapter = timelineAdapter
                binding.progressBar.visibility = View.GONE
            }
        } else {
            binding.recyclerViewTimeline.visibility = View.GONE
        }
        binding.swipeContainer.isRefreshing = false
    }

    private fun setObserver() {

        timelineViewModel.timelineNextResponse.observe(this) { t ->
                t?.let { listTimeline.addAll(it) }
                binding.recyclerViewTimeline.adapter?.notifyItemInserted(lastPositionScroll-1)
                binding.progressBar.visibility = View.GONE
        }

        timelineViewModel.itemFavoriteClick.observe(this) { msg ->
            if (msg.toInt() != 0) {
                val timeline = listTimeline.find { it.id == msg }
                timeline!!.is_interested = !timeline.is_interested
                binding.recyclerViewTimeline.adapter?.notifyItemChanged(timelineViewModel.currentPosition)
            } else {
                Toast.makeText(this, R.string.error_msg_no_internet, Toast.LENGTH_SHORT).show()
            }
        }

        timelineViewModel.itemDetailClick.observe(this) { msgId ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("msgId", msgId)
            startActivity(intent)
        }
    }

}