package com.damir.android.translator.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.damir.android.translator.R
import com.damir.android.translator.db.entity.Favorite
import com.damir.android.translator.ui.FavoritesAdapter
import com.damir.android.translator.utils.ThemeUtils
import com.damir.android.translator.utils.setToolbarTitle
import com.damir.android.translator.vm.FavoritesViewModel
import com.google.android.material.snackbar.Snackbar
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
        favoritesAdapter = FavoritesAdapter { favorite ->
            deleteFavorite(favorite.id)
            showSnackbarItemDeleted(favorite.id)
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

    private fun showSnackbarItemDeleted(favoriteId: Int) {
        Snackbar.make(favorites_root, R.string.msg_item_deleted, 4000)
            .setAction(R.string.action_undo) {
                undoFavoriteDeletion(favoriteId)
            }
            .setActionTextColor(ThemeUtils.getAttrColor(requireContext(), R.attr.colorAccent))
            .addCallback(object: Snackbar.Callback() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    if(event != DISMISS_EVENT_ACTION) {
                        deleteFavoritePermanently()
                    }
                }
            }).show()
    }

    private fun deleteFavorite(favoriteId: Int) {
        favoritesViewModel.deleteFavorite(favoriteId)
    }

    private fun deleteFavoritePermanently() {
        favoritesViewModel.deleteFavoritePermanently()
    }

    private fun undoFavoriteDeletion(favoriteId: Int) {
        favoritesViewModel.undoFavoriteDeletion(favoriteId)
    }

    private fun updateAdapter(favorites: List<Favorite>) {
        favoritesAdapter.submitList(favorites)
    }
}