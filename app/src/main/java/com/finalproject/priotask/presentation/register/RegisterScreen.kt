package com.finalproject.priotask.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finalproject.priotask.R
import com.finalproject.priotask.common.FormTextField
import com.finalproject.priotask.ui.theme.PrioTaskTheme

@Composable
fun RegisterScreen(
    uiState: RegisterUiState = RegisterUiState(),
    onEmailTextChange: (String) -> Unit = {},
    onFullNameTextChange: (String) -> Unit = {},
    onPasswordTextChange: (String) -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onLoginHereClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.person_sit),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(48.dp)
                    )
                    Text(text = "PrioTask", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Membantu anda dalam memanajemen tugas-tugas kuliah",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        fontFamily = FontFamily.Default
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    FormTextField(
                        value = uiState.emailText,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        placeHolder = "Email",
                        onValueChange = onEmailTextChange
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    FormTextField(
                        value = uiState.fullNameText,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next
                        ),
                        placeHolder = "Nama Lengkap",
                        onValueChange = onFullNameTextChange
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    FormTextField(
                        value = uiState.passwordText,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        placeHolder = "Password",
                        onValueChange = onPasswordTextChange,
                        visualTransformation = PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            item {
                Row {
                    Text(text = "Sudah punya akun? ", color = Color.Gray)
                    Text(
                        text = "Login disini",
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray,
                        modifier = Modifier.clickable { onLoginHereClick() })
                }
            }
            item {
                if (uiState.isLoading) {
                    Box(modifier = Modifier.padding(vertical = 24.dp)) {
                        CircularProgressIndicator()
                    }
                } else {
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
            item {
                Button(
                    onClick = onRegisterClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "REGISTER",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(18.dp))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun RegisterScreenPreview() {
    PrioTaskTheme {
        RegisterScreen(uiState = RegisterUiState(isLoading = true))
    }
}