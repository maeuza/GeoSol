package com.mezsoft.geosol.domain

import com.mezsoft.geosol.data.repository.PlantRepository
import javax.inject.Inject

class DeleteAllPlantUseCase @Inject constructor(private val plantRepository: PlantRepository) {

    suspend operator fun invoke() {
        plantRepository.deleteAll()
    }

}