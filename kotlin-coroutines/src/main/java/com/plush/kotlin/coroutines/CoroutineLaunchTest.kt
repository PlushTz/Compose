package com.plush.kotlin.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

/**
 * Desc:
 * @author LiJinTao
 * Created on 2026/5/8
 * Email: lijt@eetrust.com
 */
suspend fun main() {
    coroutineScope {
        launchAll()
    }

    performBackgroundWork()
}

fun CoroutineScope.launchAll() {
    this.launch {
        println("1")
    }
    this.launch {
        println("2")
    }
}

suspend fun performBackgroundWork() = coroutineScope {
    this.launch {
        delay(100.milliseconds)
        println("Sending notification in background")
    }

    println("Scope coroutine")
}