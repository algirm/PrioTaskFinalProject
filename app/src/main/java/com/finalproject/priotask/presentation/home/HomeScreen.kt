package com.finalproject.priotask.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finalproject.priotask.R
import com.finalproject.priotask.ui.theme.PrioTaskTheme

@Composable
fun HomeScreen(
    uiState: HomeUiState = HomeUiState()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp, vertical = 24.dp)
    ) {
        LazyColumn {
            item {
                HomeHeader()
            }
            item {
                Row {

                }
            }
            item {
                TaskContentList()
            }
        }
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Add Task",
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
fun TaskContentList(

) {

}

@Composable
fun HomeHeader(

) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color.Gray)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(text = "Hello, User", fontWeight = FontWeight.Bold)
            Text(text = "algirmhs@gmail.com", color = Color.Gray, fontSize = 12.sp)
        }
        IconButton(onClick = { /* Handle settings button click */ }) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings"
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun HomeScreenPreview() {
    PrioTaskTheme {
        HomeScreen()
    }
}