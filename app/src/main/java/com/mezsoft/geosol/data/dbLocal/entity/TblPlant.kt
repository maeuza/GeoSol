package com.mezsoft.geosol.data.dbLocal.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["idLote", "idPalma"])
data class TblPlant(
    val idLote:Int,
    val idPalma: Int,
    val latitudePlant:String,
    val longitudePlant:String,
    val altitudePlant: String,
    val date:String
)
