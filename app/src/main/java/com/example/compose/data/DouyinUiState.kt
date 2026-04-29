package com.example.compose.data


data class DouyinUiState(
    val url: String = "",
    val status: DownloadStatus = DownloadStatus.IDLE,
    val progress: Float = 0f
)