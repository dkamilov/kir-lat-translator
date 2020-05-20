package com.damir.android.translator.ui

import android.content.Context
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.damir.android.translator.R
import com.damir.android.translator.utils.TimeUtils
import kotlinx.android.synthetic.main.custom_video_view.view.*

class CustomVideoView(context: Context): ConstraintLayout(context) {

    private val handlerTime = Handler()

    private var isControlsVisible = false
    private var isPlaying = false
    private var isPaused = false
    private var currentVideoPosition = 0
    private lateinit var runnable: Runnable

    var videoTitle: String? = null
    var videoPath: String? = null
        set(value) {
            field = value
            setVideoView()
        }

    init {
        inflate(context, R.layout.custom_video_view, this)
        setFrameVideoClickListener()
        setPlayPauseClickListener()
        setSeekBar()
    }

    private fun setVideoView() {
        videoView.setOnPreparedListener {
            text_video_title.text = videoTitle
            seekBar_video.max = it.duration
            text_time_duration.text = TimeUtils.millsToMin(it.duration)
        }
        videoView.setOnCompletionListener {
            videoView.seekTo(0)
            seekBar_video.progress = 0
            pause()
            handlerTime.removeCallbacks(runnable)
        }
        videoView.setOnErrorListener { mp, what, extra ->
            false
        }
    }

    private fun listenVideoPlaying() {
        runnable = Runnable {
            seekBar_video.progress = videoView.currentPosition
            handlerTime.postDelayed(runnable, 1000)
        }
        handlerTime.postDelayed(runnable, 1000)
    }

    private fun setFrameVideoClickListener() {
        frame_video.setOnClickListener {
            isControlsVisible = if(isControlsVisible) {
                showControls(false)
                false
            } else {
                showControls(true)
                true
            }
        }
    }

    private fun setPlayPauseClickListener() {
        btn_play_pause.setOnClickListener {
            when {
                isPlaying -> pause()
                isPaused -> resume()
                else -> play()
            }
        }
    }

    private fun play() {
        isPlaying = true
        setPlayPauseIcon()
        videoView.setVideoPath(videoPath)
        videoView.start()
        listenVideoPlaying()
    }

    private fun resume() {
        isPlaying = true
        isPaused = false
        setPlayPauseIcon()
        videoView.start()
        videoView.seekTo(currentVideoPosition)
    }

    private fun pause() {
        isPlaying = false
        isPaused = true
        currentVideoPosition = videoView.currentPosition
        videoView.pause()
        setPlayPauseIcon()
    }

    private fun setPlayPauseIcon() {
        if(isPlaying) btn_play_pause.setImageResource(R.drawable.ic_pause)
        else btn_play_pause.setImageResource(R.drawable.ic_play)
    }

    private fun setSeekBar() {
        seekBar_video.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                text_time_current.text = TimeUtils.millsToMin(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                currentVideoPosition = seekBar?.progress ?: 0
                videoView.seekTo(currentVideoPosition)
            }

        })
    }

    private fun showControls(isVisible: Boolean) {
        if(isVisible) {
            this.text_video_title.visibility = View.GONE
            this.btn_play_pause.visibility = View.GONE
            this.layout_video_duration.visibility = View.GONE
            this.seekBar_video.visibility = View.GONE
        } else {
            this.text_video_title.visibility = View.VISIBLE
            this.btn_play_pause.visibility = View.VISIBLE
            this.layout_video_duration.visibility = View.VISIBLE
            this.seekBar_video.visibility = View.VISIBLE
        }
    }
}