package com.finalproject.priotask.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatDate(pattern: String): String {
    return SimpleDateFormat(pattern, Locale.ROOT).format(this)
}