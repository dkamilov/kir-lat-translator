package com.damir.android.translator.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.damir.android.translator.R
import com.damir.android.translator.Video
import com.damir.android.translator.ui.SearchAdapter
import com.damir.android.translator.utils.setToolbarTitle
import kotlinx.android.synthetic.main.custom_video_view.*
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment: Fragment(R.layout.fragment_search) {

    private lateinit var searchAdapter: SearchAdapter
    private val videos = mutableListOf(
        Video(0, "Bodybuilding","http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"),
        Video(1, "Bodybuilding","http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"),
        Video(2, "Bodybuilding","http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4")
    )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)
        setToolbarTitle(R.string.search)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSearchRecycler()
    }

    private fun setSearchRecycler() {
        searchAdapter = SearchAdapter()
        recyclerSearch.adapter = searchAdapter
        searchAdapter.submitList(videos)
    }
}