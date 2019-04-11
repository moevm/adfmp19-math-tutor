package com.example.mathtutor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test_result.*

class TestResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_result)

        textView4.text = intent.getStringExtra("topic")
        textView5.text = intent.getStringExtra("task")

        button4.setOnClickListener {
            startActivity(Intent(this@TestResultActivity, MainActivity::class.java))
        }
        button5.setOnClickListener {
            startActivity(Intent(this@TestResultActivity, LessonActivity::class.java))
        }
    }
}
