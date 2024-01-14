package com.mezsoft.geosol.ui.screens.pollination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mezsoft.geosol.data.model.PlantModel
import com.mezsoft.geosol.data.repository.MainRepo
import com.mezsoft.geosol.domain.AddPlantUseCase
import com.mezsoft.geosol.domain.DeleteAllPlantUseCase
import com.mezsoft.geosol.domain.DeletePlantUseCase
import com.mezsoft.geosol.domain.GetPlantUseCase
import com.mezsoft.geosol.ui.screens.MainScreenState
import com.mezsoft.geosol.ui.screens.PlantUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PollinationViewModel @Inject constructor(
    //  @ApplicationContext var context: Context,
    private val repo: MainRepo,
    private val addPlantUseCase: AddPlantUseCase,
    private val deletePlantUseCase: DeletePlantUseCase,
    private val deleteAllPlantUseCase: DeleteAllPlantUseCase,

    getPlantUseCase: GetPlantUseCase

):ViewModel() {


    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()
    private val _showButton = MutableLiveData<Boolean>()
    val showButton: LiveData<Boolean> = _showButton
    private val _okDownload = MutableLiveData<Boolean>()
    val okDownload: LiveData<Boolean> = _okDownload
    private val _locGranted = MutableLiveData<Boolean>()
    val locGranted: LiveData<Boolean> = _locGranted

    fun startScanning(){
        viewModelScope.launch {
            repo.startScanning().collect{
                if (!it.isNullOrBlank()){
                    _state.value = state.value.copy(
                        details = it
                    )
                }
            }
        }
    }
    fun onShowButtonClick() {
        _showButton.value = true
    }
    fun onCloseButtonClick() {
        _showButton.value = false
    }

    fun onDownload() {
        _okDownload.value = true
    }
    fun onNoDownload() {
        _okDownload.value = false
    }

    fun addLotePalma(plant: PlantModel){
        viewModelScope.launch {
            println("entro a actualizar")
            addPlantUseCase(plant)
        }
    }
    fun deleteLotePalma(lote:Int,palma:Int){
        viewModelScope.launch {
            deletePlantUseCase.invoke(lote, palma)
            println("entro a borrar uno$lote")
        }
    }
    val uiState: StateFlow<PlantUiState> = getPlantUseCase().map(PlantUiState::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PlantUiState.Loading)

    fun onDelete() {
        viewModelScope.launch {
            deleteAllPlantUseCase()
            println( "entro a borrar todo")
        }
    }
 /*   fun locGranted(){
        _locGranted.value = true
    }*/


}

