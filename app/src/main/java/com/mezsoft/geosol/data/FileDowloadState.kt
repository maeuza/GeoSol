package com.mezsoft.testlocationoutdoor.data

data class FileDownloadState(
    val url: String,
    val progress: Float = 0f,
    val downloading: Boolean = false
)
