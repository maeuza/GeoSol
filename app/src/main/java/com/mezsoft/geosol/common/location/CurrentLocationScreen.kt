package com.mezsoft.geosol.common.location



import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.mezsoft.geosol.common.extensions.Extensions
import com.mezsoft.geosol.ui.screens.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("MissingPermission")

@Composable
fun CurrentLocationScreen(sharedViewModel: SharedViewModel = hiltViewModel()) {
    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    val context = LocalContext.current
    val extensions = Extensions(context)
    var locationPermissionGranted by remember { mutableStateOf(false) }



    LaunchedEffect(key1 = locationPermissionGranted) {
        if (!locationPermissionGranted) {
            extensions.showToast("No hya permisos concedios, confirme el permiso")
            return@LaunchedEffect
        }

        // Aquí puedes continuar con la lógica después de que los permisos son concedidos
        // Por ejemplo, podrías navegar a otra pantalla
    }

    PermissionBox(
        permissions = permissions,
        requiredPermissions = listOf(permissions.first())
    ) { grantedPermissions ->
        locationPermissionGranted = grantedPermissions.contains(Manifest.permission.ACCESS_FINE_LOCATION)
        if (locationPermissionGranted) {
            CurrentLocationContent(
                usePreciseLocation = true, // Usa true porque ya verificaste los permisos
                sharedViewModel = sharedViewModel
            )
        } else {
            // Puedes mostrar un mensaje o realizar otra acción cuando los permisos no son concedidos
            extensions.showToast("No hya permisos concedios, confirme el permiso")
        }
    }
}


/*
PermissionBox(
        permissions = permissions,
        requiredPermissions = listOf(permissions.first())
    ) { grantedPermissions ->
        locationPermissionGranted = grantedPermissions.contains(Manifest.permission.ACCESS_FINE_LOCATION)
        if (locationPermissionGranted) {
            CurrentLocationContent(
                usePreciseLocation = true, // Usa true porque ya verificaste los permisos
                sharedViewModel = sharedViewModel
            )
        } else {
            // Puedes mostrar un mensaje o realizar otra acción cuando los permisos no son concedidos
            Text("No se concedieron los permisos de ubicación")
        }
    }
}
*/

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@Composable
fun CurrentLocationContent(
    usePreciseLocation: Boolean,
    sharedViewModel: SharedViewModel,

    ) {
    println("entro a curretn location contenct")
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val locationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
  var locationInfo by remember {
        mutableStateOf("")
    }

    //To get more accurate or fresher device location use this method
     val timestamp = System.currentTimeMillis()
                val date = Date(timestamp)
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
                val formattedDate = sdf.format(date)

    scope.launch(Dispatchers.IO) {
        val priority = if (usePreciseLocation) {
            Priority.PRIORITY_HIGH_ACCURACY
        } else {
            Priority.PRIORITY_BALANCED_POWER_ACCURACY
        }
        val result = locationClient.getCurrentLocation(
            priority,
            CancellationTokenSource().token,
        ).await()
        result?.let { fetchedLocation ->
            println ("entro por el result " )
            val  latitude=fetchedLocation.latitude
            val  longitude=fetchedLocation.longitude
            val  altitude=fetchedLocation.altitude
            locationInfo =
                 "Ubicación actual es \n" + "lat : ${fetchedLocation.latitude}\n" +
                         "long : ${fetchedLocation.longitude}\n" + "fecha ${System.currentTimeMillis()}" +
                         "  + $formattedDate"

            sharedLocationState.currentLatitude = latitude.toString()
            sharedLocationState.currentLongitude = longitude.toString()
            sharedLocationState.currentAltitude = altitude.toString()

            println("locationInfo$locationInfo")

            scope.launch {
                sharedViewModel.locGranted()
            }
         //   onLocationReceived(latitude.toString(), longitude.toString(), altitude.toString())
        }
        println("shared location " +sharedLocationState.currentLatitude)
    }}

@Composable
fun isLocationEnabled(): Boolean {
    val context = LocalContext.current
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}