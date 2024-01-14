package com.mezsoft.geosol.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SharedViewModel @Inject constructor(
): ViewModel() {


    private val _locGranted = MutableLiveData(false)
    val locGranted: LiveData<Boolean> = _locGranted

    fun locGranted(){
        _locGranted.value = true
    }

}