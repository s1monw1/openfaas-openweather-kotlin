package com.openfaas.function

import com.openfaas.function.util.logger
import org.http4k.client.JavaHttpClient
import org.http4k.core.*
import org.http4k.filter.ResponseFilters
import org.http4k.format.Jackson.auto
import java.math.BigDecimal

data class OpenWeatherWeather(val main: String, val description: String)
data class OpenWeatherMain(val temp: BigDecimal)
data class OpenWeatherResponse(val weather: List<OpenWeatherWeather>, val main: OpenWeatherMain)

data class OpenWeatherCurrentResponseDTO(
    val shortDescription: String,
    val longDescription: String,
    val temperature: BigDecimal
)

class GetCurrentWeather(private val apiKey: String) {

    private val openWeatherResponseLens = Body.auto<OpenWeatherResponse>().toLens()

    private val client: HttpHandler by lazy {
        val openWeatherResponseLogger = ResponseFilters.ReportHttpTransaction {
            logger { "Received content from OpenWeather: ${it.response.status} ${it.response.body}" }
        }

        openWeatherResponseLogger.then(JavaHttpClient())
    }

    operator fun invoke(city: String): OpenWeatherCurrentResponseDTO? {
        val response = client(getCurrentWeatherRequest(city))
        if (!response.status.successful) return null

        return openWeatherResponseLens.extract(response).also {
            logger { "Response from OpenWeather: $it" }
        }.asInternalDTO()
    }

    private fun getCurrentWeatherRequest(city: String) =
        Request(Method.GET, "https://api.openweathermap.org/data/2.5/weather")
            .query("q", city)
            .query("APPID", apiKey)
            .query("units", "metric")

}

private fun OpenWeatherResponse.asInternalDTO() =
    OpenWeatherCurrentResponseDTO(weather.first().main, weather.first().description, main.temp)