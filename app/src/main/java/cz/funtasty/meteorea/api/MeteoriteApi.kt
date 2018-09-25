package cz.funtasty.meteorea.api

/**
 * Initializations are here for testing
 */
data class MeteoriteApi(
        var id: String,
        var fall: String = "Fell",
        var geolocation: GeolocationApi? = GeolocationApi(),
        var mass: String? = "0",
        var name: String = "",
        var nametype: String = "",
        var recclass: String? = "",
        var reclat: String? = "",
        var reclong: String? = "",
        var year: String? = "2000"
)