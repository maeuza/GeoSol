package com.mezsoft.geosol.common.download

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mezsoft.geosol.data.dbLocal.AppDatabase
import com.mezsoft.geosol.data.dbLocal.dao.PlantDao
import com.mezsoft.geosol.data.dbLocal.entity.TblPlant
import com.mezsoft.geosol.data.model.PlantModel
import com.mezsoft.geosol.ui.theme.GreenForte
import com.mezsoft.testlocationoutdoor.data.DownloadCallback
import com.mezsoft.testlocationoutdoor.data.FileDownloadState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun LocationDownloadOrig(newPlants: List<PlantModel>) {
    var fileDownloadState by remember { mutableStateOf(FileDownloadState(url = "")) }
    var downloadProgressState by remember { mutableFloatStateOf(0f) }
    var locationDataList :List<PlantModel> =  listOf()
    val callback = remember {
        object : DownloadCallback {
            override fun onDownloadProgress(progress: Float) {
                // Actualiza el estado de downloadProgressState
                GlobalScope.launch(Dispatchers.Main) {
                    delay(20000)
                    downloadProgressState = progress

                }
            }
            override fun onDownloadComplete() {
                // Puedes realizar acciones adicionales cuando la descarga esté completa
                println("Descarga completa. Realizar acciones adicionales aquí.")
            }

            override fun onDownloadError(error: String) {
                // Maneja el error, por ejemplo, muestra un mensaje al usuario
                // showToast(error)
            }
        }
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
        // .padding(16.dp)
    ) {
        Button(
            onClick = {

                coroutineScope.launch(Dispatchers.IO){
                    // Actualizar el estado antes de la descarga
                    fileDownloadState = fileDownloadState.copy(downloading = true)

                  //  locationDataList = getLocationDataFromRoom( )

                    locationDataList = newPlants
                        downloadFile(fileDownloadState.url, locationDataList, callback, context)
                    fileDownloadState = fileDownloadState.copy(downloading = false)
                }
                println("locationDataList$locationDataList")
            },

            colors= ButtonDefaults.buttonColors(containerColor = GreenForte),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)


        ) {
            Text("Descargar archivo")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "En Download del celular: location_data(+número).csv",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold

        )
        // Verificamos si el estado de descarga se está actualizando correctamente
        println("Estado de descarga: ${fileDownloadState.downloading}")


        if (fileDownloadState.downloading) {
            LinearProgressIndicator(
                progress = downloadProgressState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)
            )
        }
    }
}


private suspend fun downloadFile(
    url: String,
    locationDataList: List<PlantModel>,
    callback: DownloadCallback,
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
                    callback.onDownloadError("Error: ${e.message}")
                    Toast.makeText(context, "Error al escribir el archivo: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                callback.onDownloadError("Error: ${e.message}")
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
