package com.mezsoft.geosol.ui.screens

import com.mezsoft.geosol.data.model.PlantModel

sealed interface PlantUiState {
    object Loading:PlantUiState
    data class Error(val throwable: Throwable):PlantUiState
    data class Success(val plants:List<PlantModel>) :PlantUiState
}