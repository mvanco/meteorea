package cz.funtasty.meteorea.entity

import android.arch.persistence.room.*
import android.os.Parcelable
import cz.funtasty.meteorea.Config
import kotlinx.android.parcel.Parcelize
import org.osmdroid.api.IGeoPoint
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.YEAR
import cz.funtasty.meteorea.api.MeteoriteApi as MeteoriteApi

/**
 * Initializations are here for testing
 */
@Parcelize
@Entity(tableName = "meteorite")
data class Meteorite (
        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: String,

        @ColumnInfo(name = "fall")
        var fall: String = "Fell",

        @ColumnInfo(name = "type")
        val type: String = "Point",

        @ColumnInfo(name = "latitude")
        val mLatitude: Double = 0.0,

        @ColumnInfo(name = "longitude")
        val mLongitude: Double = 0.0,

        @ColumnInfo(name = "mass")
        var mass: Int = 0,

        @ColumnInfo(name = "name")
        var name: String = "",

        @ColumnInfo(name = "nametype")
        var nametype: String = "",

        @ColumnInfo(name = "recclass")
        var recclass: String = "",

        @ColumnInfo(name = "reclat")
        var reclat: String = "",

        @ColumnInfo(name = "reclong")
        var reclong: String = "",

        @ColumnInfo(name = "year")
        var year: Int = 1900
): Parcelable, IGeoPoint {
        @Ignore
        override fun getLatitude(): Double {
                return mLatitude
        }

        @Ignore
        override fun getLongitude(): Double {
                return mLongitude
        }

        override fun getLatitudeE6(): Int { return -1 }  // Not used

        override fun getLongitudeE6(): Int { return -1 }  // Not used

        constructor(meteoriteApi: MeteoriteApi): this(
            id = meteoriteApi.id,
            fall = meteoriteApi.fall,
            type = meteoriteApi.geolocation?.type ?: "",
            mLatitude = meteoriteApi.geolocation?.coordinates?.get(0) ?: 0.0,
            mLongitude = meteoriteApi.geolocation?.coordinates?.get(1) ?: 0.0,
            name = meteoriteApi.name,
            nametype = meteoriteApi.nametype,
            recclass = meteoriteApi.recclass ?: "",
            reclat = meteoriteApi.reclat ?: ""
    ) {
            try {
                    mass = meteoriteApi.mass?.toInt() ?: 0
            } catch (e: NumberFormatException) {
                    mass = 0
            }

            try {
                    val df = SimpleDateFormat(Config.DATE_FORMAT, Locale.GERMAN)
                    val date = df.parse(meteoriteApi.year)
                    val cal = Calendar.getInstance()
                    cal.time = date
                    year = cal.get(YEAR)
            } catch (e: Exception) {
                    year = -1
            }
    }
}