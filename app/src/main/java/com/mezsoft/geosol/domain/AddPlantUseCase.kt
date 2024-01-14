package com.mezsoft.geosol.domain

import com.mezsoft.geosol.data.model.PlantModel
import com.mezsoft.geosol.data.repository.PlantRepository
import javax.inject.Inject

class AddPlantUseCase @Inject constructor(private val plantRepository: PlantRepository) {

    suspend operator fun invoke(plantModel: PlantModel) {
        plantRepository.add(plantModel)
    }

}