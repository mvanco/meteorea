package cz.funtasty.meteorea.entity

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.reactivex.Flowable

@Dao
interface MeteoriteDao {
    @Query("SELECT * FROM meteorite WHERE year > :fromYear ORDER BY mass ASC")
    fun getAll(fromYear: Int = 2010): Flowable<List<Meteorite>>

    @Update
    fun updateAll(vararg meteorites: Meteorite)

    @Insert
    fun insert(meteorite: Meteorite): Long

    @Insert
    fun insertAll(vararg meteorites: Meteorite)

    @Query("DELETE FROM meteorite")
    fun deleteAll()
}