package com.chainels.application.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chainels.application.adapter.TimelineAdapter
import com.chainels.application.api.AppConstant
import com.chainels.application.model.TimeLine
import com.chainels.application.repositories.TimelineRepository
import kotlinx.coroutines.launch
import java.net.URL

class TimelineViewModel(application: Application) : AndroidViewModel(application),
    TimelineAdapter.ItemListener {

    private var listTimeline = MutableLiveData<ArrayList<TimeLine>>()
    private var listTimelineLoadMore = MutableLiveData<ArrayList<TimeLine>>()
    private var timelineRepository: TimelineRepository? = TimelineRepository.getInstance(application)
    var itemFavoriteClick = MutableLiveData<Long>()
    var itemDetailClick = MutableLiveData<Long>()
    var currentPosition: Int = 0

    val timelineFirstResponse: LiveData<ArrayList<TimeLine>> get() = listTimeline
    val timelineNextResponse: LiveData<ArrayList<TimeLine>> get() = listTimelineLoadMore

    init {
        viewModelScope.launch {
            listTimeline =
                timelineRepository!!.getMutableTimelineLiveData(AppConstant.toId, listTimeline)
        }
    }

    fun callTimelineToLoadMoreData(score: Long) {
        viewModelScope.launch {
            timelineRepository!!.getMutableTimelineLiveData(score, listTimelineLoadMore)
        }
    }

    private fun callLikeTimeline(mId: Long) {
        viewModelScope.launch {
            val likeApiResponse = timelineRepository!!.callLikeApiForMessage(mId)
            itemFavoriteClick.value = likeApiResponse.peekContent()
        }
    }

    private fun callDisLikeTimeline(mId: Long) {
        viewModelScope.launch {
            val likeApiResponse = timelineRepository!!.callDisLikeApiForMessage(mId)
            itemFavoriteClick.value = likeApiResponse.peekContent()
        }
    }

    override fun onIemClickedForFavorite(timeline: TimeLine, position: Int) {
        currentPosition = position
        if (timeline.is_interested) {
            callDisLikeTimeline(timeline.id)
        } else {
            callLikeTimeline(timeline.id)
        }
    }

    override fun onIemClicked(mid: Long) {
        itemDetailClick.value = mid
    }

}