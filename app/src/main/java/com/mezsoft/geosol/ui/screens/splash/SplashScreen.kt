package com.mezsoft.tope.ui.screens.splash


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mezsoft.geosol.R
import com.mezsoft.geosol.ui.theme.Bluesplash
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(  onNavigate : () -> Unit){

     Splash( onNavigate)
}


    @Composable
private fun Splash(onNavigate: () -> Unit) {
    Column(
        modifier=Modifier.fillMaxSize().background(White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ){
        Box(
            modifier = Modifier.fillMaxWidth().height(80.dp).background(White),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_logo),
                contentDescription = "logo Geosol",
                Modifier.size(400.dp)

            )

        }
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter= painterResource(id = R.drawable.img_launcher_round),
            contentDescription = "logo Geosol",
            Modifier.size(800.dp)
        )
        LaunchedEffect(key1=true) {

            delay(1000)
            onNavigate()

        }
    }
}

