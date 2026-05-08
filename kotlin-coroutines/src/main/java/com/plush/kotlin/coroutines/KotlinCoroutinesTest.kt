package com.plush.kotlin.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Desc:
 * @author LiJinTao
 * Created on 2026/5/8
 * Email: lijt@eetrust.com
 */
suspend fun main() {
    withContext(Dispatchers.Default) {
        this.launch {
            greet()
        }
    }
}

suspend fun greet() {
    println("The greet() on the thread: ${Thread.currentThread().name}")
    delay(1000L)
}