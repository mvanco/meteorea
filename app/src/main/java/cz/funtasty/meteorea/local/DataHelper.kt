package cz.funtasty.meteorea.local

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
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
    private val df: DateFormat = SimpleDateFormat(Config.DATE_FORMAT, Locale.GERMAN)
    val updatedLiveData = MutableLiveData<List<Meteorite>>()

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
        Log.d("repo", "inserted ${meteorites.size} meteorites")
        db.meteoriteDao().insertAll(*meteorites.toTypedArray())
//        try {
//            meteoritesApi.forEach {
//                db.meteoriteDao().insert(Meteorite(it))
//            }
//        }
//        catch (e: Exception) {
//
//        }
//        finally {
//            updatedLiveData.value = meteoritesApi.map {Meteorite(it)}
//        }
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
        return false // TODO(Remove this)
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