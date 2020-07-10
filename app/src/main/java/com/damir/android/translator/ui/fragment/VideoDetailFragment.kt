package com.damir.android.translator.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.damir.android.translator.R
import com.damir.android.translator.utils.load
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_video_detail.*
import kotlin.math.abs

class VideoDetailFragment: Fragment(R.layout.fragment_video_detail) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = requireArguments()
        videoViewContainer.load(args.getString(EXTRA_MOVIE_IMAGE))
        videoTitle.text = args.getString(EXTRA_MOVIE_TITLE)
        videoDesc.text = args.getString(EXTRA_MOVIE_DESCRIPTION)


        video_motion_layout.setTransitionListener(object: MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {}

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                val fragmentSearch = requireParentFragment() as SearchFragment
                fragmentSearch.mainMotionLayout.progress = abs(p3)
            }
        })
        video_motion_layout.transitionToEnd()
    }

    companion object {
        const val EXTRA_MOVIE_TITLE = "movie_title"
        const val EXTRA_MOVIE_DESCRIPTION = "movie_description"
        const val EXTRA_MOVIE_IMAGE = "movie_image"

        fun newInstance(title: String, desc: String, url: String): Fragment {
            val fragment = VideoDetailFragment()
            val bundle = Bundle().apply {
                putString(EXTRA_MOVIE_TITLE, title)
                putString(EXTRA_MOVIE_DESCRIPTION, desc)
                putString(EXTRA_MOVIE_IMAGE, url)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}