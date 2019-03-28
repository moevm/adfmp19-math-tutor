package com.example.mathtutor.DB

class Task(
    val task: String,
    val lesson_id: Long,
    val answers: ArrayList<Answer>,
    val right_answer: Long,
    val id: Long
)