package com.example.mathtutor

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random

object TaskGenerator {
    data class SimpleTask(val questionString: String, val answerIndex: Int, val answers: ArrayList<String>)

    enum class Operation {
        ADD,
        SUB,
        MUL,
        DIV
    }

    fun getRandomDouble(): BigDecimal {
        return BigDecimal(Random.nextDouble(-200.0, 200.0)).setScale(1, RoundingMode.HALF_UP)
    }

    fun getRandomNatural(): Int {
        return Random.nextInt(1, 25)
    }

    fun generateSimpleTask(): SimpleTask {
        val a = getRandomDouble()
        val b = getRandomDouble()
        val operation = Operation.values().random()

        val answers = ArrayList<String>()
        (0..3).map {
            getRandomDouble().toString()
        }.toCollection(answers)

        val pair = when (operation) {
            Operation.ADD -> Pair("$a + $b", "${a + b}")
            Operation.SUB -> Pair("$a - $b", "${a - b}")
            Operation.MUL -> Pair("$a * $b", "${a * b}")
            Operation.DIV -> Pair("$a / $b", "${a / b}")
        }

        answers.add(pair.second)
        answers.shuffle()
        return SimpleTask(pair.first, answers.indexOf(pair.second), answers)

    }

    fun generatePythagoreanTask(): SimpleTask {
        val pythagoreanTriples = arrayOf(
            Triple(3, 4, 5),
            Triple(5, 12, 13),
            Triple(8, 15, 17),
            Triple(7, 24, 25)
        )

        val randomTriple = pythagoreanTriples.random()

        val chooseElem = Random.nextInt(3)

        val answers = ArrayList<String>()
        (0..3).map {
            Random.nextInt(1, 30).toString()
        }.toCollection(answers)

        val pair = when (chooseElem) {
            0 -> Pair("x^2 + ${randomTriple.second}^2 = ${randomTriple.third}^2", "${randomTriple.first}")
            1 -> Pair("${randomTriple.first}^2 + x^2 = ${randomTriple.third}^2", "${randomTriple.second}")
            2 -> Pair("${randomTriple.first}^2 + ${randomTriple.second}^2 = x^2", "${randomTriple.third}")
            else -> Pair("x^2 + ${randomTriple.second}^2 = ${randomTriple.third}^2", "${randomTriple.first}")
        }

        answers.add(pair.second)
        answers.shuffle()
        return SimpleTask(pair.first, answers.indexOf(pair.second), answers)
    }

    fun generateAnyTask(): SimpleTask =
        when (Random.nextInt(1)) {
            0 -> generatePythagoreanTask()
            1 -> generateSimpleTask()
            else -> generateSimpleTask()
        }

}

fun main() {
    (0..3).forEach { print(it) }
}