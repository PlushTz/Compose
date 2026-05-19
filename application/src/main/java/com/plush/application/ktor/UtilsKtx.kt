package com.plush.application.ktor

import android.util.Log

/**
 * Desc:
 * @author LiJinTao
 * Created on 2026/5/18
 * Email: lijt@eetrust.com
 */
fun log(methodName: String) {
    Log.d("TAG", "$methodName -> [${Thread.currentThread().name}]")
}