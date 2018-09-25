package cz.funtasty.meteorea.api

/**
 * Initializations are here for testing
 */
data class GeolocationApi(
        val type: String = "Point",
        val coordinates: List<Double> = listOf(0.0, 0.0)
)