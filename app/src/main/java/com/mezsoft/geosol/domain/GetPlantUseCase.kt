package com.mezsoft.geosol.domain

import com.mezsoft.geosol.data.model.PlantModel
import com.mezsoft.geosol.data.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlantUseCase @Inject constructor(private val plantRepository: PlantRepository) {

        operator fun invoke(): Flow<List<PlantModel>> = plantRepository.plants
}
