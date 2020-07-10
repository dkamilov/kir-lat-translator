package com.damir.android.translator.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.damir.android.translator.R
import com.damir.android.translator.data.db.entity.Video
import com.damir.android.translator.ui.SearchAdapter
import com.damir.android.translator.utils.setToolbarTitle
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment: Fragment(R.layout.fragment_search) {

    private lateinit var searchAdapter : SearchAdapter
    private val akimDesc =
        "Молодого мажора по имени Азамат, привыкшего к комфортной городской жизни, " +
        "по стечению непредвиденных обстоятельств, отправляют работать Акимом в отдаленный посёлок - Таза Булак. … " +
        "С первого дня пребывания в ауле, новоиспечённый Аким мечтает о том, чтобы вернуться в город."
    private val videos = listOf(
        Video(
            0,
            "Star Wars",
            akimDesc,
            "http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4",
            "https://st.kp.yandex.net/im/poster/2/6/8/kinopoisk.ru-Star-Wars_3A-Episode-VII-The-Force-Awakens-2682546.jpg"
        ),
        Video(
            1,
            "Avatar 2009",
            akimDesc,
            "http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4",
            "https://st.kp.yandex.net/im/poster/1/2/3/kinopoisk.ru-Avatar-1239230.jpg"
        ),
        Video(
            2,
            "Black Panther 2019",
            akimDesc,
            "http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4",
            "https://st.kp.yandex.net/im/poster/3/0/2/kinopoisk.ru-Black-Panther-3022346.jpg"
        ),
        Video(
            3,
            "Avengers Endgame 2019",
            akimDesc,
            "http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4",
            "https://v1.popcornnews.ru/k2/news/970/upload/news/852177961693.jpg"
        ),
        Video(
            4,
            "Akim 2020",
            akimDesc,
            "http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4",
            "https://i.ytimg.com/vi/tYme1c9lscE/maxresdefault.jpg"
        )

    )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)
        setToolbarTitle(R.string.search)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchAdapter = SearchAdapter {
            if(childFragmentManager.findFragmentById(R.id.frag_container) == null) {
                val fragment =
                    VideoDetailFragment
                    .newInstance(it.title, it.description, it.posterPath)
                childFragmentManager
                    .beginTransaction()
                    .replace(R.id.frag_container, fragment)
                    .commit()
            }
        }
        recyclerSearch.adapter = searchAdapter
        updateItems(videos)

        edit_search.doOnTextChanged { text, start, count, after ->
            searchAdapter.filter(text.toString())
        }
    }

    private fun updateItems(items: List<Video>) {
        searchAdapter.setItems(items)
    }
}