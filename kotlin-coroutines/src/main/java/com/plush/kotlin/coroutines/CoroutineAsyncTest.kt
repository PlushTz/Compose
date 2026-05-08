package com.plush.kotlin.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

/**
 * Desc:
 * @author LiJinTao
 * Created on 2026/5/8
 * Email: lijt@eetrust.com
 */
suspend fun main() = withContext(Dispatchers.Default) {
    val firstPage = this.async {
        delay(50.milliseconds)
        println("firstPage: ${Thread.currentThread().name}")
        "第一页"
    }

    val secondPage = this.async {
        delay(100.milliseconds)
        println("secondPage: ${Thread.currentThread().name}")
        "第二页"
    }

    val pageAreEqual = firstPage.await() == secondPage.await()

    println("Page are equal: $pageAreEqual")

    println("我会先执行吗?")
}