package com.mezsoft.geosol.common.download


data class FileDownloadState(
    val url: String,
    val progress: Float = 0f,
    val downloading: Boolean = false
)