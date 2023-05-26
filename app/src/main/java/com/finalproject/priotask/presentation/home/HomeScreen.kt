package com.finalproject.priotask.presentation.home

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finalproject.priotask.R
import com.finalproject.priotask.ui.theme.PrioTaskTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState = HomeUiState(),
    onAddTaskButtonClick: () -> Unit = {}
) {
    val refreshing = remember { false }
    val pullRefreshState = rememberPullRefreshState(refreshing, { })
    val listState = rememberLazyListState()
    val isCollapsed: Boolean by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 1 }
    }

    val context = LocalContext.current
    val testClick: (String) -> Unit = { message ->
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            CollapsingSortSection(
                modifier = Modifier
                    .padding(18.dp, 12.dp, 18.dp, 32.dp)
                    .fillMaxWidth(),
                isCollapsed = isCollapsed,
                sortState = uiState.sortState
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .pullRefresh(pullRefreshState)
        ) {
            PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    HomeHeader(
                        Modifier.padding(horizontal = 18.dp),
                        uiState.user?.fullName,
                        uiState.user?.email
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    CollapsingSortSection(
                        modifier = Modifier
                            .padding(18.dp, 12.dp, 18.dp, 32.dp)
                            .fillMaxWidth(),
                        sortState = uiState.sortState
                    )
                }
                items(10) {
                    TaskCard(
                        modifier = Modifier.padding(horizontal = 18.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            Button(
                onClick = onAddTaskButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp, start = 18.dp, end = 18.dp),
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
}

@Composable
fun CollapsingSortSection(
    modifier: Modifier = Modifier,
    isCollapsed: Boolean = true,
    sortState: SortState
) {
    AnimatedVisibility(visible = isCollapsed) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                modifier = Modifier,
                onClick = { },
                shape = RoundedCornerShape(12.dp),
                enabled = sortState !is SortState.All,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.LightGray,
                    contentColor = MaterialTheme.colors.primary,
                    disabledBackgroundColor = MaterialTheme.colors.primary,
                    disabledContentColor = MaterialTheme.colors.onPrimary
                )
            ) {
                Text(text = "All")
            }
            Button(
                modifier = Modifier,
                onClick = { },
                shape = RoundedCornerShape(12.dp),
                enabled = sortState !is SortState.Time,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.LightGray,
                    contentColor = MaterialTheme.colors.primary,
                    disabledBackgroundColor = MaterialTheme.colors.primary,
                    disabledContentColor = MaterialTheme.colors.onPrimary
                )
            ) {
                Text(text = "Time")
            }
            Button(
                modifier = Modifier,
                onClick = { },
                shape = RoundedCornerShape(12.dp),
                enabled = sortState !is SortState.Priority,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.LightGray,
                    contentColor = MaterialTheme.colors.primary,
                    disabledBackgroundColor = MaterialTheme.colors.primary,
                    disabledContentColor = MaterialTheme.colors.onPrimary
                )
            ) {
                Text(text = "Priority")
            }
        }
    }
}

@Composable
fun TaskCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(12.dp)
    ) {

    }
}

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    fullName: String?,
    email: String?
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_person_24),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(text = "Hello, ${fullName ?: "User"}", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = email ?: "user@email.com", color = Color.Gray, fontSize = 12.sp)
        }
        IconButton(onClick = { /* Handle settings button click */ }) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PrioTaskTheme {
        HomeScreen()
//        HomeHeader(
//            fullName = "Hahihuheho",
//            email = "asdasdasda@gmail.com"
//        )
    }
}