package com.mezsoft.geosol.ui.screens.pollination

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.mezsoft.geosol.R
import com.mezsoft.geosol.common.download.LocationDownload
import com.mezsoft.geosol.common.location.sharedLocationState
import com.mezsoft.geosol.data.model.PlantModel
import com.mezsoft.geosol.ui.screens.PlantUiState
import com.mezsoft.geosol.ui.theme.GreenForte
import java.time.LocalDate
import java.time.LocalDateTime


@Composable
fun PollinationScreen(viewModel: PollinationViewModel = hiltViewModel()

) {
    val currentYear = LocalDate.now().year
    val currentMount= LocalDate.now().monthValue
    val state = viewModel.state.collectAsState()
    var plantModel = PlantModel()
    //var plantModel1  = listOf<PlantModel>()
    val resultScan: Pair<String, String>
    var lote = 0
    var palma = 0
    var down = " "
    val showButton: Boolean by viewModel.showButton.observeAsState(false)
    val okDownload: Boolean by viewModel.okDownload.observeAsState(false)
    val locGranted: Boolean by viewModel.okDownload.observeAsState(false)
    var newPlants:List<PlantModel> =  listOf()

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    println("loc granted polli " + locGranted)
    if(locGranted){
    val uiState by produceState<PlantUiState>(
        initialValue = PlantUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ){
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED){
            viewModel.uiState.collect{ value = it}
        }
    }
    if (currentYear<2025 && currentMount<2) {
        when (uiState) {
            is PlantUiState.Error -> {}
            PlantUiState.Loading -> {
                CircularProgressIndicator()
            }

            is PlantUiState.Success -> {
                PlantList((uiState as PlantUiState.Success).plants)
                newPlants = (uiState as PlantUiState.Success).plants
                println("new plans en succes$newPlants")
            }
        }


        println("locGranted$locGranted")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp, horizontal = 10.dp),

                //    horizontalAlignment = Alignment.CenterHorizontally,
                //   verticalArrangement = Arrangement.Center
            ) {
                Spacer(
                    modifier = Modifier
                        .height(15.dp)
                        .width(5.dp)
                )
                //   Text(text = state.value.details, fontSize=30.sp)


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.3f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_qr),
                        contentDescription = "logo Qr",
                        Modifier.size(300.dp)
                    )
                    //  Text(text = state.value.details)
                    Spacer(
                        modifier = Modifier.height(40.dp)
                    )
                    Button(
                        onClick = { viewModel.startScanning() },
                        modifier = Modifier
                            .width(260.dp)
                            .height(100.dp)
                    ) {
                        Text(text = "LEER", fontSize = 22.sp)

                    }
                }
                Spacer(
                    modifier = Modifier.height(10.dp)
                )
                FloatingActionButton(onClick = {
                    viewModel.onDelete()
                }) {
                    Icon(Icons.Filled.Delete, contentDescription = "")
                }
                Spacer(
                    modifier = Modifier.height(10.dp)
                )
                Text(text = state.value.details, fontSize = 30.sp)
                Spacer(
                    modifier = Modifier.height(10.dp)
                )
                Box(
                    modifier = Modifier
                        //.fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 4.dp),

                    ) {

                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        if (state.value.details.isNotEmpty()) {
                            Button(
                                onClick = {
                                    println("plantModel.idLote" + plantModel.idLote + plantModel.idPalma)
                                    if (plantModel.idLote != 0) {
                                        viewModel.deleteLotePalma(
                                            plantModel.idLote,
                                            plantModel.idPalma
                                        )
                                        plantModel = PlantModel(
                                            idLote = 0, idPalma = 0, latitudePlant = "",
                                            longitudePlant = "", altitudePlant = "", date = ""
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(60.dp)
                                    .weight(1f)
                            ) {
                                Text(text = "BORRAR Ultima", fontSize = 14.sp)
                            }
                        }
                        println("showButton antes descargar$showButton")

                        if (showButton) {
                            Button(
                                onClick = { viewModel.onDownload() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GreenForte
                                ),
                                modifier = Modifier
                                    .width(110.dp)
                                    .height(60.dp)
                                    .weight(1f)
                            ) {
                                Text(text = "DESCARGAR", fontSize = 12.sp)

                            }
                            println("new plans en btones$newPlants")
                            println("okdownlo$okDownload")
                            if (okDownload) {
                                println("newplants main$newPlants")
                                LocationDownload(newPlants)
                                viewModel.onNoDownload()
                            }
                        }
                        println("sho button  finalizar$showButton")
                        if (!showButton) {
                            if (state.value.details.isNotEmpty()) {
                                Button(
                                    onClick = {
                                        viewModel.onShowButtonClick()
                                        println("sho button dentro clic$showButton")
                                    },
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .width(110.dp)
                                        .height(60.dp)
                                        .weight(1f)
                                    //   .align(Alignment.BottomEnd)

                                ) {
                                    Text(text = "FINALIZAR", fontSize = 14.sp)

                                }
                            }
                        }

                    }
                }
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
            }
            println("state.value.details" + state.value.details)
            if (state.value.details.isNotEmpty()) {
                resultScan = parseScannedData(state.value.details)
                if (lote != 0) {

                }
                lote = resultScan.first.toInt()
                palma = resultScan.second.toInt()

                val currentDate = LocalDateTime.now().toString()
                //  currentDate = LocalDateTime.toString()

                println("current date$currentDate")

                println("paso por current loc")
                plantModel = PlantModel(
                    idLote = lote,
                    idPalma = palma,
                    latitudePlant = sharedLocationState.currentLatitude,
                    longitudePlant = sharedLocationState.currentLongitude,
                    altitudePlant = sharedLocationState.currentAltitude,
                    date = currentDate
                )
                println("paso por current loc ylleno planModel$plantModel")
                viewModel.addLotePalma(plantModel)
            }
        }
    }
}
@Composable
fun parseScannedData(data: String): Pair<String, String> {
    val loteRegex = Regex("lote: (\\d+)")
    val palmaRegex = Regex("palma: (\\d+)")

    val loteMatch = loteRegex.find(data)
    val palmaMatch = palmaRegex.find(data)
    var lote by remember { mutableStateOf("") }
    var palma by remember { mutableStateOf("") }
    lote = loteMatch?.groupValues?.getOrNull(1) ?: ""
    palma = palmaMatch?.groupValues?.getOrNull(1) ?: ""
    println("resultado lote$lote")
    println("resultado palma$palma")
    return Pair(lote, palma)
}
@Composable
fun PlantList(plants: List<PlantModel>) {
    LazyColumn {
        item {
            // Encabezado aquí
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(text = "Lote", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f))
                Text(text = "Palma", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f))
                Text(text = "Latitud", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Longitud", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Altitud", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Fecha", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f))
            }
        }
        items(plants, key = { it.date }) { plant ->
            ItemPlant(plant)
        }
    }
}


@Composable
fun ItemPlant(plantModel: PlantModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 13.dp, vertical = 6.dp)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            //  .horizontalScroll(rememberScrollState()),
            content = {
                item {
                    Text(
                        text = plantModel.idLote.toString(),
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .weight(1f),
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }
                item {
                    Text(
                        text = plantModel.idPalma.toString(),
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .weight(1f),
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
                item {
                    Text(
                        text = plantModel.latitudePlant,
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .weight(1f),
                        fontSize = 13.sp
                    )
                }
                item {
                    Text(
                        text = plantModel.longitudePlant,
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .weight(1f),
                        fontSize = 13.sp
                    )
                }
                item {
                    Text(
                        text = plantModel.altitudePlant,
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .weight(1f),
                        fontSize = 13.sp
                    )
                }
                item {
                    Text(
                        text = plantModel.date,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .weight(1f),
                        fontSize = 13.sp
                    )
                }
                // Agrega más elementos a la fila si es necesario
            }
        )
    }
}
