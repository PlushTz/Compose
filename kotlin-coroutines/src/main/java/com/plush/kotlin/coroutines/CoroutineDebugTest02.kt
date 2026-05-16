package com.plush.kotlin.coroutines

/**
 * Desc:
 * @author LiJinTao
 * Created on 2026/5/16
 * Email: lijt@eetrust.com
 */
import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
fun main() {
    newSingleThreadContext("Ctx1").use { ctx1 ->
        newSingleThreadContext("Ctx2").use { ctx2 ->
            runBlocking(ctx1) {
                log("Started in ctx1")
                withContext(ctx2) {
                    log("Working in ctx2")
                }
                log("Back to ctx1")
            }
        }
    }
}