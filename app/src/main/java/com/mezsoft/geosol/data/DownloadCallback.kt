package com.mezsoft.testlocationoutdoor.data

interface DownloadCallback {
    fun onDownloadProgress(progress: Float)
    fun onDownloadComplete()
    fun onDownloadError(error: String)
}