package com.ropero.helloandroid.data.task

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val hasReminder: Boolean
)