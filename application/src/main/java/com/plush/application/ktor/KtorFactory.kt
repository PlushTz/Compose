package com.plush.application.ktor

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod

/**
 * Desc:
 * @author LiJinTao
 * Created on 2026/5/16
 * Email: lijt@eetrust.com
 */
object KtorFactory {
    val httpClient by lazy {
        HttpClient {
            expectSuccess = true
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("TAG", message)
                    }
                }
                level = LogLevel.ALL
            }
        }
    }

    suspend fun requestKtor(): HttpResponse {
        val response = httpClient.request("https://ktor.io/") {
            method = HttpMethod.Get
        }
        return response
    }
}