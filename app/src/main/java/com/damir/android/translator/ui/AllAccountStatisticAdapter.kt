package com.damir.android.translator.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.damir.android.translator.R
import com.damir.android.translator.data.FirebaseUserInfo
import com.damir.android.translator.utils.load
import kotlinx.android.synthetic.main.item_account_statistic.view.*

class AllAccountStatisticAdapter: RecyclerView.Adapter<AccountStatisticViewHolder>() {

    private val accounts = mutableListOf<FirebaseUserInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountStatisticViewHolder {
        val view =  LayoutInflater.from(parent.context)
            .inflate(R.layout.item_account_statistic, parent, false)
        return AccountStatisticViewHolder(view)
    }

    override fun getItemCount() = accounts.size

    override fun onBindViewHolder(holder: AccountStatisticViewHolder, position: Int) {
        val user = accounts[position]
        holder.bind(user)
    }

    fun setAccounts(items: List<FirebaseUserInfo>) {
        accounts.clear()
        accounts.addAll(items)
        notifyDataSetChanged()
    }
}

class AccountStatisticViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    @SuppressLint("SetTextI18n")
    fun bind(userInfo: FirebaseUserInfo) {
        itemView.image_user_photo.load(userInfo.photoUrl)
        itemView.text_username.text = userInfo.username
        itemView.text_words_count.text = userInfo.wordsCount.toString()
        itemView.text_time_spent.text = userInfo.spentTime?.div(60).toString() + " min"
    }
}