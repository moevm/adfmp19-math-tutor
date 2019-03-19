package com.example.mathtutor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lesson.*

//TODO Поставить нормальные ID
class LessonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)

        textView2.text = "Урок на тему\n\"Десятичные дроби\""
        button.setOnClickListener {
            val intent = Intent(this@LessonActivity, TestActivity::class.java)
            intent.putExtra("topic", "Задачи по теме\n\"Десятичные дроби\"")
            intent.putExtra("task", "184.3 + 9.825")
            startActivity(intent)
        }
    }
}
