package com.damir.android.translator.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.damir.android.translator.Video

class SearchAdapter: ListAdapter<Video, VideoHolder>(VideoDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val view = CustomVideoView(parent.context)
        return VideoHolder(view)
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class VideoHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val view = itemView as CustomVideoView

    fun bind(video: Video) {
        view.videoTitle = video.title
        view.videoPath = video.videoPath
    }
}

object VideoDiffUtil: DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem == newItem
    }
}