package com.example.mathtutor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.CheckBox
import com.example.mathtutor.DB.DBHelper
import com.example.mathtutor.DB.Lesson
import com.example.mathtutor.DB.Task
import kotlinx.android.synthetic.main.activity_test.*
import java.util.concurrent.TimeUnit


class TestActivity : AppCompatActivity() {

    private var isReady: Boolean = false

    private lateinit var dbHelper: DBHelper

    var rightAnswers = 0
    var rightAnswer = 0

    lateinit var tasks: ArrayList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val lesson = intent.getParcelableExtra<Lesson>("lesson")

        nameTask.text = lesson.topic

        dbHelper = DBHelper(this)

        tasks = dbHelper.getTasks(lesson)

        setTask(0)
    }

    private fun setTask(i: Int) {
        if (i == tasks.size) {
            finishUp()
            return
        }
        isReady = false

        checkBoxes.removeAllViews()
        val answers = ArrayList<CheckBox>()

        tasks[i].answers.forEachIndexed { j, answer ->
            val checkBox = CheckBox(this)
            checkBox.text = answer.text
            answers.add(checkBox)
            checkBoxes.addView(checkBox)

            if (answer.id == tasks[i].right_answer) {
                rightAnswer = j
            }
        }

        taskDesc.text = tasks[i].task

        ready.setOnClickListener {
            isReady = true
            check()
            setTask(i + 1)
        }

        runTimer()
    }

    private fun finishUp() {
        isReady = true

        val lesson = intent.getParcelableExtra<Lesson>("lesson")

        val intent = Intent(this@TestActivity, TestResultActivity::class.java)
        intent.putExtra("topic", lesson.topic)
        if (lesson.id.toInt() != 0) {
            intent.putExtra("task", "Правильных ответов $rightAnswers из ${tasks.size}")
        } else {
            intent.putExtra("task", "Входной тест пройден!\nПравильных ответов $rightAnswers из ${tasks.size}")
        }
        startActivity(intent)
    }

    private fun check() {
        var isRight = false

        for (i in 0 until checkBoxes.childCount) {
            val checkBox = checkBoxes.getChildAt(i) as CheckBox
            if (checkBox.isChecked && i != rightAnswer) {
                isRight = false
                break
            }
            if (checkBox.isChecked && i == rightAnswer) {
                isRight = true
            }
        }

        if (isRight) {
            rightAnswers++
        }
    }

    private fun runTimer() {
        progressBar.max = 45
        progressBar.progress = 0
        val t = Thread {
            while (progressBar.progress < progressBar.max && !isReady) {
                TimeUnit.MILLISECONDS.sleep(1000)
                ++progressBar.progress
            }
            if (!isReady) {
                isReady = true
                check()
            }
        }
        t.start()
    }

}
