package com.mezsoft.geosol.common.location
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

const val SPEECH_REQUEST_CODE = 0

@Composable
fun SpeechRecognitionScreen() {
    val context = LocalContext.current as ComponentActivity

    // Initialize the ActivityResultLauncher
    val speechLauncher: ActivityResultLauncher<Intent> =
        context.activityResultRegistry.register(
            "key",
            context,
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            // Handle the result here
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                val data: Intent? = result.data
                val spokenText: String? =
                    data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)

                // Do something with spokenText.
            }
        }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { displaySpeechRecognizer(speechLauncher) }) {
                Text("Start Speech Recognition")
            }
        }
    }
}

// Create an intent that can start the Speech Recognizer activity
private fun displaySpeechRecognizer(speechLauncher: ActivityResultLauncher<Intent>) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
    }

    // Launch the activity using the ActivityResultLauncher
    speechLauncher.launch(intent)
}
