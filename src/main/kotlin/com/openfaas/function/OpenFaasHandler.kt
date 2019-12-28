package com.openfaas.function

import com.openfaas.function.util.OpenFaasSecrets
import com.openfaas.function.util.logger
import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.lens.Query
import org.http4k.lens.nonEmptyString

class OpenFaasHandler : HttpHandler {

    private val cityInputLens = Query.nonEmptyString().required("city")

    override fun invoke(request: Request): Response {
        val city = cityInputLens.extract(request)

        logger { "Extracted 'city' query string from request: $city" }
        val apiKey = OpenFaasSecrets().getSecretValue("openweather-key")

        val getCurrentWeather = GetCurrentWeather(apiKey)
        val clientResponse = getCurrentWeather(city) ?: return Response(Status.INTERNAL_SERVER_ERROR, "unknown error")

        val handlerResponseLens = Body.auto<OpenWeatherCurrentResponseDTO>().toLens()

        return Response(Status.OK)
            .with(handlerResponseLens of clientResponse)
    }
}
