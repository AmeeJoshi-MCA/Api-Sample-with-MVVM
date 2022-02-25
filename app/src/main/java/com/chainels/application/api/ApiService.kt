package com.chainels.application.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("companies/{communityId}/timeline?count=10")
    fun getTimeLine(@Header("Authorization") token: String,
                    @Path("communityId") communityId: Long,
                    @Query("to") toId: Long): Call<ResponseBody>

    @GET("messages/{msg_id}")
    fun getMessage(@Header("Authorization") token: String,
                    @Path("msg_id") msg_id: Long): Call<ResponseBody>

    @PUT("messages/{msg_id}/interested")
    fun callTimelineLike(@Header("Authorization") token: String,
                    @Path("msg_id") msg_id: Long): Call<ResponseBody>

    @DELETE("messages/{msg_id}/interested")
    fun callTimelineDisLike(@Header("Authorization") token: String,
                         @Path("msg_id") msg_id: Long): Call<ResponseBody>

}