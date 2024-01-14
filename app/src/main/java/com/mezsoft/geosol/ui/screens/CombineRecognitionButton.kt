package com.mezsoft.geosol.ui.screens

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CombinedRecognitionButton(
    onClick: () -> Unit,
    activationPhrase: String
) {
    val spokenTextState = remember { mutableStateOf<String?>(null) }
    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val spokenText: String? =
                    data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull()

                spokenTextState.value = spokenText

                // Handle the recognized speech text.
                spokenText?.let {
                    println("Recognized text: $spokenText")
                    if (spokenTextMatchesActivationPhrase(spokenText, activationPhrase)) {
                        // Realiza la acción deseada cuando la frase coincide
                        onClick()
                    }
                }
            }
        }
    )

    Button(
        onClick = {
            speechLauncher.launch(createSpeechIntent())
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = Color.White
        ),
        modifier = Modifier
            .height(50.dp)
            .padding(16.dp)
    ) {
        Icon(imageVector = Icons.Default.Phone, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Start Speech Recognition")
    }
}


fun spokenTextMatchesActivationPhrase(spokenText: String?, activationPhrase: String): Boolean {
    // Puedes personalizar la lógica de coincidencia según tus necesidades
    return spokenText?.contains(activationPhrase, ignoreCase = true) == true
}

fun createSpeechIntent(): Intent {
    return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
    }
}
