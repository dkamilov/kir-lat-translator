package com.damir.android.translator.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.damir.android.translator.db.entity.Favorite

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite")
    fun getFavorites(): LiveData<List<Favorite>>
}