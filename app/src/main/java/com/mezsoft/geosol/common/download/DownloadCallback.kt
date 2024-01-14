package com.mezsoft.geosol.common.download

interface DownloadCallback {
    fun onDownloadProgress(progress: Float)
    fun onDownloadComplete()
    fun onDownloadError(error: String)
}