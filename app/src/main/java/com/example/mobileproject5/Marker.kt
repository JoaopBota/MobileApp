package com.example.mobileproject5

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marker_table")
data class Marker(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val type: String
)