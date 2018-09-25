package cz.funtasty.meteorea.entity

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Meteorite::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun meteoriteDao(): MeteoriteDao
}