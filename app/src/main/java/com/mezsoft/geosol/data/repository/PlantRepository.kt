package com.mezsoft.geosol.data.repository

import com.mezsoft.geosol.data.dbLocal.dao.PlantDao
import com.mezsoft.geosol.data.dbLocal.entity.TblPlant
import com.mezsoft.geosol.data.model.PlantModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PlantRepository @Inject constructor(private val plantDao: PlantDao) {

    val plants: Flow<List<PlantModel>> =
        plantDao.getPlant().map { items -> items.map { PlantModel(it.idLote,it.idPalma,
            it.latitudePlant,it.longitudePlant,it.altitudePlant,it.date) } }

    suspend fun add(plantModel: PlantModel) {
        plantDao.addPlant(plantModel.toData())
    }
   /* suspend fun delete(plantModel: Int, palma: Int) {
        plantDao.deletePlant(plantModel.toData())
        }*/
    suspend fun delete(lote: Int, palma: Int) {
        plantDao.deleteOrderOut(lote,palma)
    }
    suspend fun deleteAll() {
        plantDao.deleteAll()
    }

    private fun PlantModel.toData(): TblPlant {
        return TblPlant(this.idLote,this.idPalma,this.latitudePlant,this.longitudePlant,this.altitudePlant,
            this.date)
    }
}