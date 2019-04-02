package com.example.mathtutor

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random

class TaskGenerator {
    data class SimpleTask(val questionString: String, val answerString: String)

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

        return when(operation) {
            Operation.ADD -> SimpleTask("$a + $b", "${a + b}")
            Operation.SUB -> SimpleTask("$a - $b", "${a - b}")
            Operation.MUL -> SimpleTask("$a * $b", "${a * b}")
            Operation.DIV -> SimpleTask("$a / $b", "${a / b}")
        }
    }

    fun generatePythagoreanTask(): SimpleTask {
        val pythagoreanTriples = arrayOf(
            Triple(3, 4, 5),
            Triple(5, 12, 13),
            Triple(8, 15, 17),
            Triple(7, 24, 25)
        )

        val randomTriple= pythagoreanTriples.random()

        val chooseElem = Random.nextInt(3)

        return when(chooseElem) {
            0 -> SimpleTask("x^2 + ${randomTriple.second}^2 = ${randomTriple.third}^2", "${randomTriple.first}")
            1 -> SimpleTask("${randomTriple.first}^2 + x^2 = ${randomTriple.third}^2", "${randomTriple.second}")
            2 -> SimpleTask("${randomTriple.first}^2 + ${randomTriple.second}^2 = x^2", "${randomTriple.third}")
            else -> SimpleTask("x^2 + ${randomTriple.second}^2 = ${randomTriple.third}^2", "${randomTriple.first}")
        }
    }
}