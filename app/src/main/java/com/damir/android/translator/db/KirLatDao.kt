package com.damir.android.translator.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.damir.android.translator.db.entity.KirLat

@Dao
interface KirLatDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMessage(kirLat: KirLat)

    @Query("SELECT * FROM kirLat")
    fun getMessages(): LiveData<List<KirLat>>
}