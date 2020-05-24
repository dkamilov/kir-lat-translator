package com.damir.android.translator.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.damir.android.translator.R
import com.damir.android.translator.data.db.entity.Favorite
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoritesAdapter(
    private val onItemDeleteClicked: (favorite: Favorite) -> Unit
): ListAdapter<Favorite, FavoriteHolder>(
    FavoriteDiffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_favorite, parent, false)
        return FavoriteHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite, onItemDeleteClicked)
    }
}

class FavoriteHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val sendMessage: TextView = itemView.text_send
    private val receiveMessage: TextView = itemView.text_receive
    private val btnTrash: ImageView = itemView.btn_trash

    fun bind(favorite: Favorite, onItemDeleteClicked: (favorite: Favorite) -> Unit) {
        sendMessage.text = favorite.sendMessage
        receiveMessage.text = favorite.receiveMessage
        btnTrash.setOnClickListener {
            onItemDeleteClicked(favorite)
        }
    }
}

object FavoriteDiffUtil : DiffUtil.ItemCallback<Favorite>(){

    override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem == newItem
    }

}