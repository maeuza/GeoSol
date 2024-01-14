package com.mezsoft.geosol.ui.screens.login
import android.app.Activity
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mezsoft.geosol.R
import com.mezsoft.geosol.common.location.CurrentLocationScreen
import com.mezsoft.geosol.common.location.sharedLocationState
import com.mezsoft.geosol.ui.screens.SharedViewModel
import com.mezsoft.geosol.ui.theme.Blue1_light
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen( onNavigate : () -> Unit,
                 loginViewModel: LoginViewModel = hiltViewModel(),
                 sharedViewModel: SharedViewModel = hiltViewModel()
                 ) {


    Box(
        Modifier
            .fillMaxSize()
            .padding(2.dp)

    ) {
        HeaderLogin()

        Login(Modifier.align(Alignment.Center), loginViewModel )
        CurrentLocationScreen(sharedViewModel)
        val locGranted: Boolean by sharedViewModel.locGranted.observeAsState(false)

        LaunchedEffect(key1=true) {

            delay(5000)
          onNavigate()
        }
    }
}

@Composable
fun HeaderLogin() {
    HeaderImage()
    Spacer(modifier = Modifier.padding(2.dp))
}
@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel) {

    val user: String by viewModel.user.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column(modifier = modifier) {
            //  HeaderImage(Modifier.align(Alignment.Top).size(200.dp))
            //  Spacer(modifier = Modifier.padding(16.dp))
            EmailField(user) { viewModel.onLoginChanged(it, password) }
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(password) { viewModel.onLoginChanged(user, it) }
            Spacer(modifier = Modifier.padding(8.dp))
            ForgotPassword(Modifier.align(Alignment.End))
            Spacer(modifier = Modifier.padding(16.dp))
            LoginButton(loginEnable) {
                coroutineScope.launch {
                    viewModel.onLoginSelected()
                }
            }
        }
    }
}

@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = { onLoginSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF4303),
            disabledContainerColor = Color(0xFFF78058),
            contentColor = White,
            disabledContentColor = White
        ), enabled = loginEnable
    ) {
        Text(text = "Iniciar sesión")
    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "Olvidaste la contraseña?",
        modifier = modifier.clickable { },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFB9600)
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = password, onValueChange = { onTextFieldChanged(it) },
        placeholder = { Text(text = "Contraseña") },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF636262),
            focusedContainerColor = Color(0xFFDEDDDD),
            unfocusedContainerColor = Blue1_light,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ))
}

@Composable
fun EmailField(user: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = user,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        placeholder = { Text(text = "Usuario") },
        //  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        /* colors = TextFieldDefaults.colors(
             focusedTextColor = Color(0xFF636262),
             focusedContainerColor = Color(0xFFDEDDDD),
             focusedIndicatorColor = Color.Transparent,
             unfocusedIndicatorColor = Color.Transparent
         )*/
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF636262),
            focusedContainerColor = Color(0xFFDEDDDD),
            unfocusedContainerColor = Blue1_light,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ))
}

@Composable
fun HeaderImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(White)
    ) {
        Column(
            // horizontalAlignment = Alignment.CenterHorizontally,
            // verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(0.dp) // Ajusta según sea necesario
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_logo),
                contentDescription = "Header",
                modifier = Modifier.size(400.dp).align(Alignment.CenterHorizontally)
            )

            //  Spacer(modifier = Modifier.height(8.dp)) // Ajusta según sea necesario

          /*  Image(
                painter = painterResource(id = R.drawable.img_tope_title_blue_or_min),
                contentDescription = "Header",
                modifier = Modifier.size(170.dp).align(Alignment.CenterHorizontally).padding(2.dp)
            )*/
        }
    }
}


@Composable
fun Header(modifier: Modifier) {
    val activity = LocalContext.current as Activity
    Icon(
        imageVector = Icons.Default.Close,
        contentDescription = "close app",
        modifier = modifier.clickable { activity.finish() })
}