package com.damir.android.translator.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.damir.android.translator.db.entity.Favorite

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite WHERE toDelete = 0")
    fun getFavorites(): LiveData<List<Favorite>>

    @Query("UPDATE Favorite SET toDelete = 1 WHERE id =:favoriteId")
    suspend fun deleteFavorite(favoriteId: Int)

    @Query("DELETE FROM Favorite WHERE toDelete = 1")
    suspend fun deleteFavoritePermanently()

    @Query("UPDATE Favorite SET toDelete = 0 WHERE id =:favoriteId")
    suspend fun undoFavoriteDeletion(favoriteId: Int)
}