package com.example.mathtutor.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


internal class DBHelper(context: Context) : SQLiteOpenHelper(context, "myDB", null, 1) {

    private val LOG_TAG: String = "DB_OPERATION"

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(LOG_TAG, "--- onCreate database ---")
        db.execSQL(
            """
            CREATE TABLE Lesson (
                id integer PRIMARY KEY AUTOINCREMENT,
                topic string,
                content string,
                last_visit timestamp
            );
            """
        )
        db.execSQL(
            """
            CREATE TABLE Task (
                lesson_id integer,
                task string,
                task_id integer PRIMARY KEY AUTOINCREMENT
            );
            """
        )

        db.execSQL(
            """
            CREATE TABLE Answer (
                task_id integer,
                text text,
                answer_id integer PRIMARY KEY AUTOINCREMENT
            );
            """
        )
        db.execSQL(
            """
            CREATE TABLE Right_Answer (
                task_id integer,
                answer_id integer
            );
            """
        )
        Log.d(LOG_TAG, "--- Database created ---")
    }

    fun insertLesson(lesson: Lesson): Long {
        val cv = ContentValues()
        cv.put("topic", lesson.topic)
        cv.put("content", lesson.content)
        cv.putNull("last_visit")

        val db = writableDatabase
        val rowID = db.insert("Lesson", null, cv)
        db.close()
        Log.d(LOG_TAG, "row inserted, ID = $rowID, lesson: $lesson")
        return rowID
    }

    fun insertTask(task: Task): Long {
        val cv = ContentValues();
        cv.put("lesson_id", task.lesson_id)
        cv.put("task", task.task)

        val db = writableDatabase
        val rowID = db.insert("Task", null, cv)
        Log.d(LOG_TAG, "row inserted, ID = $rowID, task: $task");

        task.answers.forEachIndexed { i, answer ->
            val answer_id = insertAnswer(answer, rowID)
            if (i == task.right_answer.toInt()) {
                insertRightAnswer(rowID, answer_id)
            }
        }
        db.close()

        return rowID
    }

    private fun insertAnswer(answer: Answer, task_id: Long): Long {
        val cv = ContentValues()
        cv.put("task_id", task_id)
        cv.put("text", answer.text)

        val db = writableDatabase
        val rowID = db.insert("Answer", null, cv)
        db.close()
        Log.d(LOG_TAG, "row inserted, ID = $rowID, answer: $answer")

        return rowID
    }

    private fun insertRightAnswer(task_id: Long, answer_id: Long): Long {
        val cv = ContentValues()
        cv.put("task_id", task_id)
        cv.put("answer_id", answer_id)

        val db = writableDatabase
        val rowID = db.insert("Right_Answer", null, cv)
        db.close()
        Log.d(LOG_TAG, "row inserted, ID = $rowID")

        return rowID
    }


    fun readLesson(topic: String): Lesson? {
        val selectByTopic = "SELECT * FROM Lesson WHERE topic = '$topic'"
        val cursor = readableDatabase.rawQuery(selectByTopic, null)
        if (cursor.moveToFirst()) {
            cursor.close()
            return createLesson(cursor)
        }
        cursor.close()
        return null
    }

    fun getStartLesson(): Lesson {
        val selectRarelyVisited = "SELECT * FROM Lesson WHERE id = 1"
        val c = readableDatabase.rawQuery(selectRarelyVisited, null)
        if (c.moveToFirst()) {
            val lesson = createLesson(c)
            updateLesson(lesson)
            c.close()
            return lesson
        } else {
            c.close()
            throw IllegalStateException()
        }
    }

    fun getLesson(): Lesson {
        val selectRarelyVisited = "SELECT * FROM Lesson WHERE id != 1 ORDER BY last_visit ASC"
        val c = readableDatabase.rawQuery(selectRarelyVisited, null)
        if (c.moveToFirst()) {
            val lesson = createLesson(c)
            updateLesson(lesson)
            c.close()
            return lesson
        } else {
            c.close()
            throw IllegalStateException()
        }
    }

    fun getTasks(lesson: Lesson): ArrayList<Task> {
        val selectTasks = "SELECT * FROM Task WHERE lesson_id = ${lesson.id}"
        val c = readableDatabase.rawQuery(selectTasks, null)
        val tasks = ArrayList<Task>()
        if (c.moveToFirst()) {
            do {
                val id = c.getLong(2)
                tasks.add(Task(c.getString(1), lesson.id, getAnswers(id), getRightAnswer(id), id))
            } while (c.moveToNext())
        }
        c.close()
        return tasks
    }

    private fun getAnswers(task_id: Long): ArrayList<Answer> {
        val selectAnswers = "SELECT * FROM Answer WHERE task_id = $task_id"
        val c = readableDatabase.rawQuery(selectAnswers, null)
        val answers = ArrayList<Answer>()
        if (c.moveToFirst()) {
            do {
                answers.add(Answer(c.getString(1), c.getLong(2)))
            } while (c.moveToNext())
        }
        c.close()
        return answers
    }

    private fun getRightAnswer(task_id: Long): Long {
        val selectRightAnswer = "SELECT * FROM Right_Answer WHERE task_id = $task_id"
        val c = readableDatabase.rawQuery(selectRightAnswer, null)
        if (c.moveToFirst()) {
            return c.getLong(1)
        }
        c.close()
        throw IllegalStateException()
    }

    private fun updateLesson(lesson: Lesson) {
        Log.d(LOG_TAG, "--- Update mytable: ---")
        val cv = ContentValues();
        cv.put("last_visit", System.currentTimeMillis())

        val updCount = writableDatabase.update(
            "Lesson", cv, "id = ?", arrayOf(lesson.id.toString())
        )
        Log.d(LOG_TAG, "updated rows count = $updCount")
    }

    private fun createLesson(cursor: Cursor): Lesson {
        val id = cursor.getLong(0)
        val topic = cursor.getString(1)
        val content = cursor.getInt(2)
        var date: Long? = null
        if (!cursor.isNull(3)) {
            date = cursor.getLong(3)
        }
        val lesson = Lesson(topic, content, date, id)
        Log.d(LOG_TAG, lesson.toString())
        return lesson
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}