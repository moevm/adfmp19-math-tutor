package com.example.mathtutor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mathtutor.DB.DBHelper
import kotlinx.android.synthetic.main.activity_lesson.*

//TODO Поставить нормальные ID
class LessonActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)

        dbHelper = DBHelper(this)

        val lesson = dbHelper.getLesson()

        imageView3.setImageResource(lesson.content)
        textView2.text = lesson.topic

        button.setOnClickListener {
            val intent = Intent(this@LessonActivity, TestActivity::class.java)
            intent.putExtra("lesson", lesson)
            startActivity(intent)
        }
    }
}
