package com.nesquid.helloandroidgarcianestor.model

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val timeInMillis: Long,
    val hasReminder: Boolean
)