package com.plush.kotlin.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

/**
 * Desc:协程作用域
 * @author LiJinTao
 * Created on 2026/5/8
 * Email: lijt@eetrust.com
 */
suspend fun main() {
    coroutineScope {
        this.launch {
            this.launch {
                delay(2.seconds)
                println("Child of the enclosing coroutine completed")
            }
            println("Child coroutine 1 completed")
        }

        this.launch {
            delay(1.seconds)
            println("Child coroutine 2 completed")
        }

        println("Coroutine scope completed")
    }
}