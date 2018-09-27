package cz.funtasty.meteorea.entity

import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import cz.funtasty.meteorea.Application
import cz.funtasty.meteorea.Config
import cz.funtasty.meteorea.api.MeteoriteApi
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.DATE
import javax.inject.Inject

class DataHelper {
    private val DATABASE_UPDATED_DAY_KEY = "database_updated_day_key"
    private val df = SimpleDateFormat(Config.DATE_FORMAT, Locale.GERMAN) as DateFormat

    @Inject
    lateinit var mContext: Context

    @Inject
    lateinit var mSharedPref: SharedPreferences

    var db: Database

    companion object {
        val sInstance: DataHelper by lazy {
            DataHelper()
        }
    }

    private constructor() {
        Application.sInstance.component.inject(this)
        this.db = Room.databaseBuilder(mContext, Database::class.java, "database").build()
    }

    fun insertMeteorites(meteoritesApi: List<MeteoriteApi>) {
        val meteorites = meteoritesApi.map {Meteorite(it)}
        try {
            db.meteoriteDao().insertAll(*meteorites.toTypedArray())
        } catch (e: Exception) {

        } finally {

        }
    }

    fun clearDatabase() {
        db.meteoriteDao().deleteAll()
    }

    fun updateMeteorities(meteoritesApi: List<MeteoriteApi>) {
        clearDatabase()
        insertMeteorites(meteoritesApi)
    }

    fun setActualDatabase() {
        val currentDate = df.format(Date())
        mSharedPref.edit().putString(DATABASE_UPDATED_DAY_KEY, currentDate).apply()
    }

    fun isActualDatabase(): Boolean {
        val lastUpdate = mSharedPref.getString(DATABASE_UPDATED_DAY_KEY, "")
        if (lastUpdate.isEmpty()) return false
        val lastUpdateDate = df.parse(lastUpdate)

        val cal = java.util.Calendar.getInstance()
        cal.time = Date()
        cal.set(DATE, -1)
        val borderTime = cal.time

        return lastUpdateDate.after(borderTime)
    }
}