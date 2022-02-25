package com.chainels.application.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chainels.application.model.TimeLine
import com.chainels.application.repositories.TimelineRepository
import kotlinx.coroutines.launch

class TimelineDetailViewModel(application: Application) : AndroidViewModel(application) {

    private var timeLineDetailData = MutableLiveData<TimeLine>()
    private var timelineRepository : TimelineRepository? = TimelineRepository.getInstance(application)

    fun callDetailTimeline(mId: Long){
        viewModelScope.launch {
            timeLineDetailData = timelineRepository!!.getMutableTimelineDetailLiveData(mId)
        }
    }

    fun getTimelineDetail(): MutableLiveData<TimeLine>{
        return timeLineDetailData
    }

}