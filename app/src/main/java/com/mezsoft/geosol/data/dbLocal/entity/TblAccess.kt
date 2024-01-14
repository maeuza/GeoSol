package com.mezsoft.geosol.data.dbLocal.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TblAccess(

    @PrimaryKey @ColumnInfo(name="idAccess") val idAccess:Int,
    @ColumnInfo(name="urlServ") val urlServ:String,
    @ColumnInfo(name="tokenDb") val tokenDb:String,
    @ColumnInfo(name="locationLatitude") val locationLatitude:String,
    @ColumnInfo(name="locationLongitude") val locationLongitude:String,
    @ColumnInfo(name="cellphoneModel") val cellphoneModel:String,
    @ColumnInfo(name="cellphoneImei") val cellphoneImei:String



)
