package cz.funtasty.meteorea.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.reactivex.Flowable

@Dao
interface MeteoriteDao {
    @Query("SELECT * FROM meteorite")
    fun getAll(): Flowable<List<Meteorite>>

    @Update
    fun updateAll(vararg meteorites: Meteorite)

    @Insert
    fun insert(meteorite: Meteorite): Long

    @Insert
    fun insertAll(vararg meteorites: Meteorite)

    @Query("DELETE FROM meteorite")
    fun deleteAll()

//    @Query("SELECT * FROM user")
//    fun getAll(): List<User>
//
//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
//
//    @Insert
//    fun insertAll(vararg users: User)
//
//    @Delete
//    fun delete(user: User)
}