package com.mezsoft.geosol.domain

import com.mezsoft.geosol.data.repository.PlantRepository
import javax.inject.Inject

class DeletePlantUseCase @Inject constructor(private val plantRepository: PlantRepository) {

    suspend operator fun invoke(
        lote: Int,
        palma: Int
    ) {
        plantRepository.delete(lote,palma)
    }

}