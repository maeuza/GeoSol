package com.mezsoft.geosol.ui.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mezsoft.geosol.R
import com.mezsoft.geosol.ui.theme.Bluesplash
import kotlinx.coroutines.delay

@Composable
fun SplashPresScreen( onNavigate : () -> Unit){

     val scale = remember {
         Animatable(0f)
     }
        Column(
            modifier = Modifier.fillMaxSize().background(Color.White).scale(scale.value)
            // horizontalAlignment = Alignment.CenterHorizontally,
            // verticalArrangement = Arrangement.Center,

        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = Modifier.fillMaxWidth().height(80.dp).background(Color.White),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_logo),
                    contentDescription = "logo Geosol",
                    Modifier.size(400.dp)

                )

            }
            // Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                androidx.compose.foundation.Canvas(modifier = Modifier
                    .size(800.dp),
                    onDraw = {
                        drawCircle(Bluesplash)
                    })
                Image(
                    painter = painterResource(id = R.drawable.img_logo),
                    contentDescription = "logo Tope",
                    Modifier.size(600.dp)
                        //.align(Alignment.Center)
                )
            }
            LaunchedEffect(key1=true) {
                scale.animateTo(
                    targetValue = 0.9f,
                    animationSpec = tween(
                    durationMillis = 800,
                    easing = {
                        OvershootInterpolator(8f)
                            .getInterpolation(it)
                    }
                    )
                )
                delay(1000)
                onNavigate()
            }


        }

}



