package com.plush.kotlin.coroutines.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

/**
 * Desc:
 * @author LiJinTao
 * Created on 2026/5/16
 * Email: lijt@eetrust.com
 */
fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        println("Emitting $i")
        emit(i)
    }
}.flowOn(Dispatchers.Default)

fun main() = runBlocking {
    simple06().onCompletion { cause -> if (cause != null) println("Flow completed exceptionally") }
        .catch { cause -> println("Caught exception $cause") }
        .collect { value -> println(value) }
}

fun simple02(): Sequence<Int> = sequence {
    for (i in 1..3) {
        Thread.sleep(100)
        yield(i)
    }
}

fun simple03(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100)
        emit(i)
        println("emit $i")
    }
}

fun simple04() = (1..3).asFlow()

fun simple05(): Flow<Int> = flow {
    try {
        emit(1)
        emit(2)
        emit(3)
        println("This line will not execute")
        emit(4)
        emit(5)
    } finally {
        println("Finally in numbers")
    }
}

suspend fun takeSimple() {
    simple05().take(2).collect {
        println(it)
    }
}

suspend fun reduceSimple() {
    val sum = (1..5).asFlow().map { it * it }.reduce { a, b ->
        a + b
    }
    println(sum)
}

fun simple06(): Flow<Int> = flow {
    emit(1)
    throw RuntimeException()
}