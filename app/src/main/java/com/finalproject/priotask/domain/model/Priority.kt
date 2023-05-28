package com.finalproject.priotask.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class Priority : Parcelable {
    object High : Priority()

    object Moderate : Priority()

    object Low : Priority()
}