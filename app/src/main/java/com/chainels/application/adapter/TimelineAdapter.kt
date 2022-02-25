package com.chainels.application.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chainels.application.R
import com.chainels.application.databinding.ItemTimelineBinding
import com.chainels.application.model.TimeLine

class TimelineAdapter(
    private var list: MutableList<TimeLine>, private val itemListener: ItemListener
) : RecyclerView.Adapter<TimelineAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val mDeveloperListItemBinding = DataBindingUtil.inflate<ItemTimelineBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_timeline, viewGroup, false
        )
        return ViewHolder(mDeveloperListItemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTimelineBinding.model = list[i]
        viewHolder.itemTimelineBinding.listener = itemListener
        viewHolder.itemTimelineBinding.modelPosition=i
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(var itemTimelineBinding: ItemTimelineBinding) :
        RecyclerView.ViewHolder(itemTimelineBinding.root)

    interface ItemListener {
        fun onIemClickedForFavorite(timeline: TimeLine,position:Int)
        fun onIemClicked(mid: Long)
 }

}