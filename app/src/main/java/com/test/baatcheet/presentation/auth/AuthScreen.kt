package com.test.baatcheet.presentation.auth


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.test.baatcheet.presentation.auth.components.AuthTextField
import com.test.baatcheet.ui.theme.BgColor
import com.test.baatcheet.ui.theme.MainColor
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.baatcheet.core.LoadingBox
import com.test.baatcheet.core.VerticalSpace
import com.test.baatcheet.data.network.NetworkResponse
import com.test.baatcheet.presentation.auth.event.AuthEvent
import com.test.baatcheet.presentation.auth.event.AuthType
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    viewModel: AuthViewModel = koinViewModel(),
    navToHome: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.checkAuthStatus()
        viewModel.event.collect { event ->
            when (event) {
                AuthEvent.NavToHome -> {
                    navToHome.invoke()
                }

                is AuthEvent.ShowToast -> {
                    Toast.makeText(context, "${event.msg}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    if (state.signInResponse is NetworkResponse.Loading || state.signUpResponse is NetworkResponse.Loading) {
        LoadingBox()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (state.authType) {
                            AuthType.SIGN_IN -> "Welcome Back"
                            AuthType.SIGN_UP -> "Create Account"
                        },
                        fontSize = 22.sp,
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        containerColor = BgColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state.authType == AuthType.SIGN_UP) {
                AuthTextField(
                    value = state.name,
                    onValueChange = {
                        viewModel.setName(it)
                    },
                    label = "Full Name",
                    leadingIcon = Icons.Default.Person
                )
                VerticalSpace()
            }

            AuthTextField(
                value = state.email,
                onValueChange = {
                    viewModel.setEmail(it)
                },
                label = "Email",
                leadingIcon = Icons.Default.Email,
                keyboardType = KeyboardType.Email
            )
            VerticalSpace()

            AuthTextField(
                value = state.password,
                onValueChange = {
                    viewModel.setPassword(it)
                },
                label = "Password",
                leadingIcon = Icons.Default.Lock,
                keyboardType = KeyboardType.Password,
                isPassword = true
            )
            VerticalSpace(22)

            Button(
                onClick = {
                    when (state.authType) {
                        AuthType.SIGN_IN -> viewModel.signIn()
                        AuthType.SIGN_UP -> viewModel.signUp()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MainColor),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    text = when (state.authType) {
                        AuthType.SIGN_IN -> "Sign in"
                        AuthType.SIGN_UP -> "Sign up"
                    },
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Text(
                text = when (state.authType) {
                    AuthType.SIGN_IN -> "Don't have an account? Sign up"
                    AuthType.SIGN_UP -> "Already have an account? Sign in"
                },
                color = MainColor,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        viewModel.switchAuthType()
                    }
            )
        }
    }
}
