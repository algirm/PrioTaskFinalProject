package com.finalproject.priotask.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finalproject.priotask.R
import com.finalproject.priotask.common.LabeledTextField
import com.finalproject.priotask.ui.theme.PrioTaskTheme

@Composable
fun SettingsScreen(
    name: String? = null,
    fullName: String? = null,
    email: String? = null,
    onArrowBackClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 18.dp, vertical = 12.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            IconButton(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart),
                onClick = onArrowBackClick
            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Settings",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(
                    minHeight = 250.dp
                )
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                )
                Text(
                    modifier = Modifier,
                    text = "Hello, ${name ?: "User"}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }

        LabeledTextField(modifier = Modifier.fillMaxWidth(), label = "Nama", text = fullName ?: "")
        LabeledTextField(modifier = Modifier.fillMaxWidth(), label = "Email", text = email ?: "")

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(12.dp),
                onClick = onLogoutClick
            ) {
                Text(modifier = Modifier.padding(vertical = 4.dp), text = "Logout", fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    PrioTaskTheme {
        SettingsScreen()
    }
}