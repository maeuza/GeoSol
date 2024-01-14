package com.mezsoft.geosol.common.location


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class LocationState {
    var currentLatitude by mutableStateOf("")
    var currentLongitude by mutableStateOf("")
    var currentAltitude by mutableStateOf("")

}

val sharedLocationState = LocationState()