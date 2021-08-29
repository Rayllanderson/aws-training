package com.rayllanderson.aws.core

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.hateoas.JsonError
import jakarta.inject.Singleton

@Singleton
class HttpClientResponseExceptionHandler: ExceptionHandler<HttpClientResponseException, HttpResponse<JsonError>> {

    override fun handle(request: HttpRequest<*>?, e: HttpClientResponseException?): HttpResponse<JsonError> {
        return HttpResponse.status<JsonError?>(e?.status).body(JsonError(e?.message ?: "Ocorreu um erro"))
    }

}