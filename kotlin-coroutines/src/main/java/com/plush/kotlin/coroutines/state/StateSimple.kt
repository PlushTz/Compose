package com.plush.kotlin.coroutines.state

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * Desc:
 * @author LiJinTao
 * Created on 2026/5/19
 * Email: lijt@eetrust.com
 */
//var counter = AtomicInteger(0)
var counter = 0

@OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
val counterContext = newSingleThreadContext("CounterContext")

val mutex = Mutex()

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
//            withContext(counterContext) {
            mutex.withLock {
                counter++
            }
//            }
        }
    }
    println("Counter = $counter")
}

suspend fun massiveRun(action: suspend () -> Unit) {
    val n = 100  // number of coroutines to launch
    val k = 1000 // times an action is repeated by each coroutine
    val time = measureTimeMillis {
        coroutineScope { // scope for coroutines
            repeat(n) {
                launch {
                    repeat(k) { action() }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}