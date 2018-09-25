package cz.funtasty.meteorea.local

import android.arch.persistence.room.*
import cz.funtasty.meteorea.api.MeteoriteApi as MeteoriteApi

/**
 * Initializations are here for testing
 */
@Entity(tableName = "meteorite")
data class Meteorite(
        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: String,

        @ColumnInfo(name = "fall")
        var fall: String = "Fell",

        @ColumnInfo(name = "type")
        val type: String = "Point",

        @ColumnInfo(name = "latitude")
        val latitude: Double = 0.0,

        @ColumnInfo(name = "longitude")
        val longitude: Double = 0.0,

        @ColumnInfo(name = "mass")
        var mass: String = "0",

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
        var year: String = "2000"
) {
    constructor(meteoriteApi: MeteoriteApi): this(
            id = meteoriteApi.id,
            fall = meteoriteApi.fall,
            type = meteoriteApi.geolocation?.type ?: "",
            latitude = meteoriteApi.geolocation?.coordinates?.get(0) ?: 0.0,
            longitude = meteoriteApi.geolocation?.coordinates?.get(1) ?: 0.0,
            mass = meteoriteApi.mass ?: "",
            name = meteoriteApi.name,
            nametype = meteoriteApi.nametype,
            recclass = meteoriteApi.recclass ?: "",
            reclat = meteoriteApi.reclat ?: "",
            reclong = meteoriteApi.reclong ?: "",
            year = meteoriteApi.year ?: ""
    )
}