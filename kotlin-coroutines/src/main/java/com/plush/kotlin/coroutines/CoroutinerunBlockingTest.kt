package com.plush.kotlin.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

/**
 * Desc:
 * @author LiJinTao
 * Created on 2026/5/8
 * Email: lijt@eetrust.com
 */
fun main() {
    val readItem = MyRepository.readItem()
    println("readItem: $readItem")
}


interface Repository {
    fun readItem(): Int
}

object MyRepository : Repository {
    override fun readItem(): Int {
        return runBlocking {
            myReadItem()
        }
    }
}

suspend fun myReadItem(): Int {
    delay(100.milliseconds)
    return 4
}