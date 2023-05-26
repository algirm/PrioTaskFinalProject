package com.finalproject.priotask.presentation.home

sealed class HomeUiIntent {
    object SortingAllClicked : HomeUiIntent()
    object SortingTimeClicked : HomeUiIntent()
    object SortingPriorityClicked : HomeUiIntent()
    object RefreshContent : HomeUiIntent()
}
