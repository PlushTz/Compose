package com.example.compose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Desc:
 * @author lijt
 * Created on 2025/11/4
 * Email: lijt@eetrust.com
 */
class TestViewModel : ViewModel() {
    private val _index = MutableLiveData(0)
    val index: LiveData<Int> = _index

    fun onIndexChange(newName: Int) {
        _index.value = newName
    }
}