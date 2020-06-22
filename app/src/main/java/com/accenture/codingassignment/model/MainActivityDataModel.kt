package com.accenture.codingassignment.model

// Parent Class of Api Response Type
data class MainActivityDataModel (
    val rows: List<MainActivivityAdapterListModel>,
    val title: String
)