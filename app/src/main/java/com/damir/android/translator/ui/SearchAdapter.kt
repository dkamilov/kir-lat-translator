package com.damir.android.translator.ui

import android.graphics.Color
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.toSpannable
import androidx.recyclerview.widget.RecyclerView
import com.damir.android.translator.R
import com.damir.android.translator.data.db.entity.Video
import com.damir.android.translator.utils.load
import kotlinx.android.synthetic.main.item_search.view.*
import java.util.*

class SearchAdapter(
    private val onItemClickListener: (video: Video) -> Unit
) : RecyclerView.Adapter<VideoHolder>() {

    private val videos = mutableListOf<Video>()
    private val filteredVideos = mutableListOf<Video>()
    private var filter = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_search, parent, false)
        return VideoHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = filteredVideos.size

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        holder.bind(filteredVideos[position], filter)
    }

    fun setItems(items: List<Video>) {
        filteredVideos.addAll(items)
        videos.clear()
        videos.addAll(items)
        notifyDataSetChanged()
    }

    fun filter(text: String) {
        filter = text.toLowerCase(Locale.ROOT)
        if(filter.isBlank()) {
            setFilteredItems(videos)
            return
        }
        filteredVideos.clear()
        for(video in videos) {
            if(video.title
                    .toLowerCase(Locale.ROOT)
                    .contains(filter)) {
                filteredVideos.add(video)
            }
        }
        notifyDataSetChanged()
    }

    private fun setFilteredItems(items: List<Video>) {
        filteredVideos.clear()
        filteredVideos.addAll(items)
        notifyDataSetChanged()
    }
}

class VideoHolder(itemView: View, private val onItemClickListener: (video: Video) -> Unit) : RecyclerView.ViewHolder(itemView) {

    private val videoTitle = itemView.textVideoTitle
    private val videoPoster = itemView.imageVideoPoster

    fun bind(video: Video, filter: String) {
        val spannableTitle = video.title.toSpannable()
        val (start, end) = findStartEndForSpannable(video.title, filter)
        spannableTitle.setSpan(
            ForegroundColorSpan(Color.GREEN),
            start,
            end,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        videoTitle.text = spannableTitle
        videoPoster.load(video.posterPath)
        itemView.setOnClickListener {
            onItemClickListener(video)
        }
    }

    private fun findStartEndForSpannable(text: String, filter: String): Pair<Int, Int> {
        var start: Int
        var end: Int
        val filterSize = filter.length // star 3
        text.forEachIndexed { ind, char ->
            if(text.substring(ind, filterSize + ind)
                    .toLowerCase(Locale.ROOT) == filter
            ) {
                start = ind
                end = filterSize + ind
                return Pair(start, end)
            }
        }
        return Pair(0, 0)
    }
}