package com.finalproject.priotask.presentation.add_edit

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finalproject.priotask.R
import com.finalproject.priotask.common.LabeledTextField
import com.finalproject.priotask.common.TimePickerDialog
import com.finalproject.priotask.domain.model.Priority
import com.finalproject.priotask.domain.model.Task
import com.finalproject.priotask.presentation.home.dummyTask
import com.finalproject.priotask.ui.theme.PrioTaskTheme
import com.finalproject.priotask.util.PlaceholderTransformation
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    isEdit: Boolean = false,
    task: Task? = null,
    uiState: AddEditUiState = AddEditUiState(),
    onArrowBackClick: () -> Unit = {},
    onAddEditClick: (Task) -> Unit = {},
    onDoneTaskClick: (String) -> Unit = {}
) {
    Log.d("TAG", "AddEditScreen: Asuww isEdit is $isEdit")
    var namaTugasText by remember {
        mutableStateOf(task?.name ?: "")
    }
    var deskripsiTugasText by remember {
        mutableStateOf(task?.description ?: "")
    }

    var expandedPriority by remember {
        mutableStateOf(false)
    }
    var selectedPriority: Priority by remember {
        mutableStateOf(task?.priority ?: Priority.Low)
    }

    val dateFormatter = remember { SimpleDateFormat("dd MMM yyyy", Locale.ROOT) }
    val timeFormatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = task?.deadline?.time ?: Date().time
    )

    var initHour = 0
    var initMinute = 0
    task?.let {
        val cal = Calendar.getInstance()
        cal.clear()
        cal.time = it.deadline
        cal.clear(Calendar.SECOND)
        initHour = cal.get(Calendar.HOUR_OF_DAY)
        initMinute = cal.get(Calendar.MINUTE)
    }

    val timePickerState = rememberTimePickerState(
        initialHour = initHour,
        initialMinute = initMinute,
        false
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp, horizontal = 18.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp, top = 12.dp)
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
                    text = if (isEdit) "Edit Task" else "Add Task",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            LabeledTextField(
                value = namaTugasText,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                placeHolder = "Nama Tugas",
                onValueChange = { namaTugasText = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            LabeledTextField(
                value = deskripsiTugasText,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                placeHolder = "Deskripsi Tugas",
                onValueChange = { deskripsiTugasText = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(0.6f),
                onExpandedChange = { expandedPriority = !expandedPriority },
                expanded = expandedPriority
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = 4.dp,
                    shape = RoundedCornerShape(24.dp)
                ) {
                    TextField(
                        readOnly = true,
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Priority") },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Gray,
                            disabledTextColor = Color.Transparent,
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        visualTransformation = PlaceholderTransformation(
                            when (selectedPriority) {
                                Priority.High -> "Tinggi"
                                Priority.Low -> "Rendah"
                                Priority.Moderate -> "Sedang"
                            }
                        ),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPriority) }
                    )

                    ExposedDropdownMenu(
                        expanded = expandedPriority,
                        onDismissRequest = { expandedPriority = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                expandedPriority = false
                                selectedPriority = Priority.High
                            }
                        ) {
                            Text(text = "Tinggi")
                        }
                        DropdownMenuItem(
                            onClick = {
                                expandedPriority = false
                                selectedPriority = Priority.Moderate
                            }
                        ) {
                            Text(text = "Sedang")
                        }
                        DropdownMenuItem(
                            onClick = {
                                expandedPriority = false
                                selectedPriority = Priority.Low
                            }
                        ) {
                            Text(text = "Rendah")
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(32.dp))

            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(0.6f),
                onExpandedChange = {
                    showDatePicker = true
                },
                expanded = false
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = 4.dp,
                    shape = RoundedCornerShape(24.dp),
                ) {
                    val calendar = Calendar.getInstance()
                    calendar.clear()
                    calendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                    calendar.set(Calendar.MINUTE, timePickerState.minute)
                    calendar.clear(Calendar.SECOND)
                    TextField(
                        readOnly = true,
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Tenggat Waktu") },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Gray,
                            disabledTextColor = Color.Transparent,
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        visualTransformation = PlaceholderTransformation(
                            "${
                                dateFormatter.format(
                                    Date(datePickerState.selectedDateMillis ?: Date().time)
                                )
                            }\n(${timeFormatter.format(calendar.time)})"
                        ),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) }
                    )
                }
            }

            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = {

                    },
                    confirmButton = {
                        TextButton(
                            onClick = {

                                showDatePicker = false
                                showTimePicker = true
                            }
                        ) {
                            Text(text = "OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {

                                showDatePicker = false
                            }
                        ) {
                            Text(text = "Cancel")
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState
                    )
                }
            }
            if (showTimePicker) {
                TimePickerDialog(
                    onCancel = {

                        showTimePicker = false
                    },
                    onConfirm = {

                        showTimePicker = false
                    }
                ) {
                    TimePicker(
                        state = timePickerState
                    )
                }
            }

            if (uiState.isLoading) {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    val taskNew = Task(
                        id = task?.id ?: "",
                        name = namaTugasText,
                        description = deskripsiTugasText,
                        priority = selectedPriority,
                        deadline = provideDate(
                            datePickerState.selectedDateMillis,
                            timePickerState.hour,
                            timePickerState.minute
                        ),
                        createdAt = Date()
                    )
                    onAddEditClick(taskNew)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.6f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (isEdit) "Done Edit" else "Add Task",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            if (isEdit) {
                Button(
                    onClick = {
                        task?.let { onDoneTaskClick(it.id) }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.4f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF54B435),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Done Task",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

private fun provideDate(
    dateLong: Long?,
    hour: Int,
    minute: Int
): Date {
    val calendar = Calendar.getInstance()
    calendar.time = Date(dateLong!!)
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    calendar.clear(Calendar.SECOND)
    return calendar.time
}

@Preview(showBackground = true)
@Composable
fun AddEditScreenPreviewAdd() {
    PrioTaskTheme {
        AddEditScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditScreenPreviewEdit() {
    PrioTaskTheme {
        AddEditScreen(isEdit = true, task = dummyTask)
    }
}