package com.example.mathtutor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


//TODO Поставить нормальные ID
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isFirstRun()) {
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
        intent.putExtra("topic", "Входной тест")
        intent.putExtra("task", "sin^2(43)+cos^2(43)")
        startActivity(intent)
    }

}
