package com.damir.android.translator.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.damir.android.translator.FavoritesAdapter
import com.damir.android.translator.R
import com.damir.android.translator.db.entity.Favorite
import com.damir.android.translator.utils.setToolbarTitle
import com.damir.android.translator.vm.FavoritesViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment: Fragment(R.layout.fragment_favorites){

    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var favoritesViewModel: FavoritesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle(R.string.favorite)
        setFavoritesRecycler()
        setObservers()
    }

    private fun setFavoritesRecycler() {
        favoritesAdapter = FavoritesAdapter {
            favoritesViewModel.deleteFavorite(it)
        }
        recyclerFavorites.adapter = favoritesAdapter
    }

    private fun setObservers() {
        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        favoritesViewModel.favorites.observe(viewLifecycleOwner, Observer {
            showNoFavorites(it)
            updateAdapter(it)
        })
    }

    private fun showNoFavorites(favorites: List<Favorite>) {
        if(favorites.isNullOrEmpty())
            text_no_favorite.visibility = View.VISIBLE
        else
            text_no_favorite.visibility = View.GONE
    }

    private fun updateAdapter(favorites: List<Favorite>) {
        favoritesAdapter.submitList(favorites)
    }
}