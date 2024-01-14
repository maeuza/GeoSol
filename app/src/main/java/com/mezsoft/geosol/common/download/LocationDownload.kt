package com.mezsoft.geosol.common.download

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.mezsoft.geosol.data.model.PlantModel
import com.mezsoft.testlocationoutdoor.data.FileDownloadState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun LocationDownload(newPlants: List<PlantModel>) {
    var fileDownloadState by remember { mutableStateOf(FileDownloadState(url = "")) }
    var downloadProgressState by remember { mutableStateOf(0f) }
    var locationDataList :List<PlantModel> =  listOf()
    println("lentro a descargar")


    val scope = rememberCoroutineScope()
    val context = LocalContext.current

                scope.launch(Dispatchers.IO){
                    // Actualizar el estado antes de la descarga


                    locationDataList = newPlants

                    downloadFile(locationDataList, context)

                }
                println("locationDataList$locationDataList")


        // Verificamos si el estado de descarga se está actualizando correctamente



}


private suspend fun downloadFile(
    locationDataList: List<PlantModel>,
    context: Context
) {

    withContext(Dispatchers.IO) {
        try {
            println("locationDataList en download$locationDataList")
            val csvContent = buildString {
                appendLine("lote,palma,lat,lon,alt, date")
                locationDataList.forEach { tblPlant ->
                    appendLine("${tblPlant.idLote},${tblPlant.idPalma}" +
                            ",${tblPlant.latitudePlant},${tblPlant.longitudePlant},${tblPlant.altitudePlant}" +
                            ",${tblPlant.date}"
                    )
                    println("latitude en cvs" + tblPlant.idLote + tblPlant.idPalma)
                }
            }
            println("csvContent$csvContent")

            val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            println("downloadsDirectory mostrar$downloadsDirectory")

            val baseFileName = "location_data.csv"
            val baseFileName1 = "location_data"
            val extension = ".csv"


            // Crear un nombre de archivo basado en la marca de tiempo única
            val timestamp = System.currentTimeMillis()
            val newFileName = "$baseFileName1-$timestamp$extension"

            println("newFileName  $newFileName")

            val externalFile = File(downloadsDirectory, newFileName)
            println("ver externalFile  $externalFile")

            if (!downloadsDirectory.exists()) {
                println("entro por no exist")
                downloadsDirectory.mkdirs()
            }

            try {
                FileOutputStream(externalFile).use { fileOutputStream ->
                    BufferedWriter(OutputStreamWriter(fileOutputStream)).use { bufferedWriter ->
                        bufferedWriter.write(csvContent)
                    }
                }
            } catch (e: Exception) {
                Log.e("LocationDownload", "Error al escribir el archivo: ${e.message}")

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error al escribir el archivo: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
