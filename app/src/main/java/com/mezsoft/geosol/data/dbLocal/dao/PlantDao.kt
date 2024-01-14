package com.mezsoft.geosol.data.dbLocal.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mezsoft.geosol.data.dbLocal.entity.TblPlant
import kotlinx.coroutines.flow.Flow


@Dao
interface PlantDao {

    @Query("SELECT * from TblPlant ")
    fun getPlant(): Flow<List<TblPlant>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlant(tblPlant: TblPlant)

    @Update
    suspend fun updatePlant(tblPlant: TblPlant)

    @Delete
    suspend fun deletePlant(tblPlant: TblPlant)

    @Query("DELETE  FROM TblPlant")
    suspend fun deleteAll()

    @Query("DELETE  FROM TblPlant WHERE idLote =:lote AND idPalma=:palma")
    suspend fun deleteOrderOut(lote:Int,palma:Int)
}