package com.example.mathtutor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.CheckBox
import kotlinx.android.synthetic.main.activity_test.*
import java.util.concurrent.TimeUnit


class TestActivity : AppCompatActivity() {

    private var isReady: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        nameTask.text = intent.getStringExtra("topic")
        taskDesc.text = intent.getStringExtra("task")

        if (nameTask.text == "Входной тест") {
            val checkBox1 = CheckBox(this)
            checkBox1.text = "1"
            val checkBox2 = CheckBox(this)
            checkBox2.text = "600"
            val checkBox3 = CheckBox(this)
            checkBox3.text = "tg(8)"
            checkBoxes.addView(checkBox1)
            checkBoxes.addView(checkBox2)
            checkBoxes.addView(checkBox3)

            ready.setOnClickListener {
                isReady = true
                val intent = Intent(this@TestActivity, TestResultActivity::class.java)
                intent.putExtra("topic", "Входной тест")
                intent.putExtra("task", "Входной тест пройден.\nГотов начать учиться?")
                startActivity(intent)
            }
        } else {
            val checkBox1 = CheckBox(this)
            checkBox1.text = "194.125"
            val checkBox2 = CheckBox(this)
            checkBox2.text = "600"
            val checkBox3 = CheckBox(this)
            checkBox3.text = "14.84"
            val checkBox4 = CheckBox(this)
            checkBox4.text = "-14"
            checkBoxes.addView(checkBox1)
            checkBoxes.addView(checkBox2)
            checkBoxes.addView(checkBox3)
            checkBoxes.addView(checkBox4)

            ready.setOnClickListener {
                isReady = true
                val intent = Intent(this@TestActivity, TestResultActivity::class.java)
                intent.putExtra("topic", "Задачи по теме\n\"Десятичные дроби\"")
                intent.putExtra("task", "Отлично! Решено 9 из 10 задач\nТы получаешь звездочку!")
                startActivity(intent)
            }
        }

        runTimer()
    }


    private fun runTimer() {
        val t = Thread {
            while (progressBar.progress < progressBar.max && !isReady) {
                TimeUnit.MILLISECONDS.sleep(100)
                ++progressBar.progress
            }
            if (!isReady) ready.callOnClick()
        }
        t.start()
    }

}
