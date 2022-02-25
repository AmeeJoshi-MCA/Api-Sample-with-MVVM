package com.chainels.application.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.chainels.application.api.ApiClient
import com.chainels.application.api.ApiService
import com.chainels.application.api.AppConstant
import com.chainels.application.model.TimeLine
import com.chainels.application.model.TimelineList
import com.chainels.application.utils.Event
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

class TimelineRepository(app: Context){

    private var instanceApi: ApiService

    init {
        ApiClient.init(app)
        instanceApi= ApiClient.instance
    }

    companion object{
        private var timelineRepository: TimelineRepository?=null
        @Synchronized
        fun getInstance(app: Context): TimelineRepository? {
            if (timelineRepository == null) {
                timelineRepository = TimelineRepository(app)
            }
            return timelineRepository
        }
    }

    fun getMutableTimelineLiveData(toId:Long,apiResponseForTimeline: MutableLiveData<ArrayList<TimeLine>>): MutableLiveData<ArrayList<TimeLine>> {

        instanceApi.getTimeLine(AppConstant.ACCESS_TOKEN,AppConstant.communityId,toId).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("error", t.message.toString())
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val responseBody = response.body()?.string()
                try {
                    val responseApi = Gson().fromJson(responseBody.toString(), TimelineList::class.java)
                    responseApi.let { apiResponseForTimeline.value = it as ArrayList<TimeLine> }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
        return apiResponseForTimeline
    }

    fun getMutableTimelineDetailLiveData(msgId : Long): MutableLiveData<TimeLine> {

        val mutableLiveData = MutableLiveData<TimeLine>()

        instanceApi.getMessage(AppConstant.ACCESS_TOKEN,msgId).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("error", t.message.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val responseBody = response.body()?.string()
                try {
                    val responseDetailApi = Gson().fromJson(responseBody.toString(), TimeLine::class.java)
                    responseDetailApi.let { mutableLiveData.value = it as TimeLine }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
        return mutableLiveData
    }


    fun callLikeApiForMessage (msgId : Long) : Event<Long> {

        var likeApiResponse = Event(msgId)

        instanceApi.callTimelineLike(AppConstant.ACCESS_TOKEN,msgId).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("error", t.message.toString())
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if(response.code() != 204){
                        likeApiResponse=Event(0)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
        return  likeApiResponse
    }

    fun callDisLikeApiForMessage (msgId : Long) : Event<Long> {

        var likeApiResponse = Event(msgId)

        instanceApi.callTimelineDisLike(AppConstant.ACCESS_TOKEN,msgId).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("error", t.message.toString())
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if(response.code() != 204){
                        likeApiResponse=Event(0)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
        return  likeApiResponse
    }

}