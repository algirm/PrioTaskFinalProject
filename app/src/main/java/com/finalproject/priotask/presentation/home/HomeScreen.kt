package com.finalproject.priotask.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finalproject.priotask.R
import com.finalproject.priotask.domain.model.Priority
import com.finalproject.priotask.domain.model.Task
import com.finalproject.priotask.ui.theme.LightBlue
import com.finalproject.priotask.ui.theme.PrioTaskTheme
import com.finalproject.priotask.util.formatDate
import java.util.Date

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState = HomeUiState(),
    onAddTaskButtonClick: () -> Unit = {},
    onSortingTypeClick: (SortState) -> Unit = {},
    onRefresh: () -> Unit = {},
    onTaskClick: (Task) -> Unit = {}
) {
    val pullRefreshState = rememberPullRefreshState(uiState.isRefreshing, onRefresh)
    val listState = rememberLazyListState()
    val isCollapsed: Boolean by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 1 }
    }

    Scaffold(
        topBar = {
            Surface(
                elevation = 2.dp
            ) {
                CollapsingSortSection(
                    modifier = Modifier
                        .padding(18.dp, 8.dp, 18.dp, 8.dp)
                        .fillMaxWidth(),
                    isCollapsed = isCollapsed,
                    sortState = uiState.sortState,
                    onSortingTypeClick = onSortingTypeClick
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .pullRefresh(pullRefreshState)
        ) {
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
                            .padding(18.dp, 0.dp, 18.dp, 0.dp)
                            .fillMaxWidth(),
                        sortState = uiState.sortState,
                        onSortingTypeClick = onSortingTypeClick
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                }
                items(items = uiState.tasks) { task ->
                    TaskCard(
                        modifier = Modifier.padding(horizontal = 18.dp),
                        task = task,
                        onClick = onTaskClick
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    Spacer(modifier = Modifier.height(100.dp))
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
            PullRefreshIndicator(
                uiState.isRefreshing,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun CollapsingSortSection(
    modifier: Modifier = Modifier,
    isCollapsed: Boolean = true,
    sortState: SortState,
    onSortingTypeClick: (SortState) -> Unit
) {
    AnimatedVisibility(visible = isCollapsed) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                modifier = Modifier
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                onClick = { onSortingTypeClick(SortState.All) },
                shape = RoundedCornerShape(12.dp),
                enabled = sortState !is SortState.All,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.LightGray,
                    contentColor = MaterialTheme.colors.primary,
                    disabledBackgroundColor = MaterialTheme.colors.primary,
                    disabledContentColor = MaterialTheme.colors.onPrimary
                ),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 6.dp)
            ) {
                Text(text = "All", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
            Button(
                modifier = Modifier
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                onClick = { onSortingTypeClick(SortState.Time) },
                shape = RoundedCornerShape(12.dp),
                enabled = sortState !is SortState.Time,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.LightGray,
                    contentColor = MaterialTheme.colors.primary,
                    disabledBackgroundColor = MaterialTheme.colors.primary,
                    disabledContentColor = MaterialTheme.colors.onPrimary
                ),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 6.dp)
            ) {
                Text(text = "Time", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
            Button(
                modifier = Modifier
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                onClick = { onSortingTypeClick(SortState.Priority) },
                shape = RoundedCornerShape(12.dp),
                enabled = sortState !is SortState.Priority,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.LightGray,
                    contentColor = MaterialTheme.colors.primary,
                    disabledBackgroundColor = MaterialTheme.colors.primary,
                    disabledContentColor = MaterialTheme.colors.onPrimary
                ),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 6.dp)
            ) {
                Text(text = "Priority", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    task: Task,
    onClick: (Task) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(12.dp),
        onClick = { onClick(task) }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .padding(end = 10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = task.name,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    maxLines = 1
                )
                Text(
                    text = task.description,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .padding(start = 6.dp),
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            modifier = Modifier
                                .padding(end = 1.dp),
                            text = task.deadline.formatDate("dd MMM yyyy"),
                            fontSize = 10.sp,
                            color = LightBlue,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = task.deadline.formatDate("hh:mm a"),
                            fontSize = 10.sp,
                            color = LightBlue,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.7f)
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(vertical = 3.dp),
                        text = when (task.priority) {
                            Priority.High -> "Tinggi"
                            Priority.Low -> "Rendah"
                            Priority.Moderate -> "Sedang"
                        },
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary,
                        fontSize = 10.sp
                    )
                }
            }
        }
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
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
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
            Text(
                text = "Hello, ${fullName ?: "User"}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
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
        HomeScreen(uiState = HomeUiState(tasks = provideDummyTasks()))
    }
}

private fun provideDummyTasks(amount: Int = 10): List<Task> {
    val tasks = arrayListOf<Task>()
    repeat(amount) {
        tasks.add(dummyTask)
    }
    return tasks
}

val dummyTask = Task(
    "1",
    "Tugas RPL",
    "LK10P DESKRIPsi nya adalah bla blab aladasdasdsadasdsadsadadsadasdsadsadsadsa asdasdsad adasd asdsa dsad sadasdsa das d",
    Priority.Moderate,
    Date(1685169340925),
    Date(1685169340925)
)