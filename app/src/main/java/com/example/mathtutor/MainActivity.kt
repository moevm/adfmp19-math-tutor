package com.example.mathtutor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mathtutor.DB.Answer
import com.example.mathtutor.DB.DBHelper
import com.example.mathtutor.DB.Lesson
import com.example.mathtutor.DB.Task
import kotlinx.android.synthetic.main.activity_main.*


//TODO Поставить нормальные ID
class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isFirstRun()) {
            fillUpDatabase()
            runTutorialTest()
        }

        button3.setOnClickListener {
            val intent = Intent(this@MainActivity, LessonActivity::class.java)
            startActivity(intent)
        }

    }

    private fun isFirstRun(): Boolean {
        return getPreferences(Context.MODE_PRIVATE).getBoolean(tutorialKey, true)
    }

    private fun runTutorialTest() {
        getPreferences(Context.MODE_PRIVATE).edit().putBoolean(tutorialKey, false).apply()
        val intent = Intent(this@MainActivity, TestActivity::class.java)
        intent.putExtra("lesson", dbHelper.getStartLesson())
        startActivity(intent)
    }

    private fun fillUpDatabase() {
        dbHelper = DBHelper(this)

        var lesson_id = dbHelper.insertLesson(Lesson("Входной тест", R.drawable.lesson1, null, 0))
        dbHelper.insertTask(
            Task(
                "184.3 + 9.825", lesson_id,
                arrayListOf(
                    Answer("194.125", 0),
                    Answer("600", 0),
                    Answer("14.84", 0),
                    Answer("-14", 0)
                ), 0,
                0
            )
        )
        dbHelper.insertTask(
            Task(
                "128.3 - 0.5", lesson_id,
                arrayListOf(
                    Answer("123.3", 0),
                    Answer("100", 0),
                    Answer("127.8", 0),
                    Answer("128.8", 0)
                ), 2,
                0
            )
        )
        dbHelper.insertTask(
            Task(
                "x^2+4^2=5^2", lesson_id,
                arrayListOf(
                    Answer("2", 0),
                    Answer("4", 0),
                    Answer("3", 0),
                    Answer("8", 0)
                ), 2,
                0
            )
        )


        lesson_id = dbHelper.insertLesson(Lesson("Урок на тему\n\"Десятичные дроби\"", R.drawable.lesson1, null, 0))
        dbHelper.insertTask(
            Task(
                "184.3 + 9.825", lesson_id,
                arrayListOf(
                    Answer("194.125", 0),
                    Answer("600", 0),
                    Answer("14.84", 0),
                    Answer("-14", 0)
                ), 0,
                0
            )
        )
        dbHelper.insertTask(
            Task(
                "128.3 - 0.5", lesson_id,
                arrayListOf(
                    Answer("123.3", 0),
                    Answer("100", 0),
                    Answer("127.8", 0),
                    Answer("128.8", 0)
                ), 2,
                0
            )
        )
        lesson_id = dbHelper.insertLesson(Lesson("Урок на тему\n\"Теорема пифагора\"", R.drawable.lesson2, null, 0))
        dbHelper.insertTask(
            Task(
                "x^2+4^2=5^2", lesson_id,
                arrayListOf(
                    Answer("2", 0),
                    Answer("4", 0),
                    Answer("3", 0),
                    Answer("8", 0)
                ), 2,
                0
            )
        )
    }

}
