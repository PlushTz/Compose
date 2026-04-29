package com.example.compose.ui.douyin

import android.content.Context
import android.net.Uri
import androidx.compose.animation.core.copy
import androidx.lifecycle.ViewModel
import com.example.compose.data.DouyinUiState
import com.example.compose.data.DownloadStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Desc:
 * @author lijt
 * Created on 2026/4/29
 * Email: lijt@eetrust.com
 */
@HiltViewModel
class DouyinViewModel @Inject constructor(private val extractor: DouyinExtractor) : ViewModel() {
    private val _uiState = MutableStateFlow(DouyinUiState())
    val uiState = _uiState.asStateFlow()

    fun onUrlChanged(url: String) {
        _uiState.value = _uiState.value.copy(url = url)
    }

    fun clearUrl() {
        _uiState.value = _uiState.value.copy(url = "", status = DownloadStatus.IDLE)
    }

    fun startDownload(context: Context) {
        val currentUrl = _uiState.value.url
        if (currentUrl.isBlank()) return

        _uiState.value = _uiState.value.copy(status = DownloadStatus.PARSING, progress = 0f)

        extractor.extractVideoUrl(currentUrl) { videoUrl ->
            if (videoUrl == null) {
                _uiState.value = _uiState.value.copy(status = DownloadStatus.IDLE)
                return@extractVideoUrl
            }

            _uiState.value = _uiState.value.copy(status = DownloadStatus.DOWNLOADING)
            extractor.downloadVideo(context, videoUrl, object : DouyinExtractor.DownloadCallback {
                override fun onProgress(p: Int) {
                    _uiState.value = _uiState.value.copy(progress = p / 100f)
                }

                override fun onSuccess(uri: Uri?) {
                    _uiState.value = _uiState.value.copy(status = DownloadStatus.SUCCESS, progress = 1f)
                }

                override fun onFailure(e: Exception) {
                    _uiState.value = _uiState.value.copy(status = DownloadStatus.IDLE)
                }
            })
        }
    }
}