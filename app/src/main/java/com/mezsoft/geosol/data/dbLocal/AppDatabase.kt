package com.mezsoft.geosol.data.dbLocal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mezsoft.geosol.data.dbLocal.dao.PlantDao
import com.mezsoft.geosol.data.dbLocal.entity.TblPlant


@Database(
    entities=[TblPlant::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun plantDao(): PlantDao


}