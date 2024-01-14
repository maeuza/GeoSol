package com.mezsoft.geosol.ui.screens.main


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mezsoft.geosol.R
import com.mezsoft.geosol.ui.theme.Green1
import com.mezsoft.geosol.ui.theme.Green2
import com.mezsoft.geosol.ui.theme.Green3
import com.mezsoft.geosol.ui.theme.GreenLogo

@Composable
    fun MainScreen(onNavigateToPo : () -> Unit,
                   onNavigateToHa : () -> Unit,
                   onNavigateToWo : () -> Unit) {
        // Column para organizar los elementos
        Column(
            modifier = Modifier
                .fillMaxSize(),
            //    .padding(16.dp), // Agrega padding a la columna
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_logo),
                contentDescription = "Header",
                modifier = Modifier.size(300.dp).padding(5.dp)
                    //.align(Alignment.CenterHorizontally)
            )
            // Título de la pantalla
         /*   Text(
                text = "ACTIVIDAD",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally),
                        fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp

            )*/

          /*  val navBut: () -> Unit = { onNavigateToPo() }
            var color: Color = Green1
            var text: String = "Polinización"
            BoxButton( navBut,color, text   )
            // Box 1 con color
            val navBut1: () -> Unit = { onNavigateToHa() }
            color = Green2
            text = "Cosecha"
            BoxButton( navBut1,color, text   )
            val navBut2: () -> Unit = { onNavigateToWo() }
            color = Green3
            text = "Labor"
            BoxButton( navBut2,color, text   )*/


            val navBut: () -> Unit = { onNavigateToPo() }
            var color: Color = Green1
            var text: String = "Polinización"
            BoxButton( navBut,color, text   )  // Box1
            // Box 1 con color
            val navBut1: () -> Unit = { onNavigateToHa() }
            color = Green3
            text = "Cosecha"
            BoxButton( navBut1,color, text   )  // Box2
            val navBut2: () -> Unit = { onNavigateToWo() }
            color = GreenLogo
            text = "Labor"
            BoxButton( navBut2,color, text   )   // Box3
        }


}

@Composable
fun BoxButton(navBut: () -> Unit, color: Color, text:String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(color)
            .padding(16.dp)
            .clickable {
                navBut
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = White,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(start = 5.dp).align(Alignment.Center)
        )
    }
}
