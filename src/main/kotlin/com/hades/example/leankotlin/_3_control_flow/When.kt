package com.hades.example.leankotlin._3_control_flow

fun main() {
    when_expresion()
}

// When, START
//  similar to `Swith` in C-like language
fun when_expresion() {
    run {
        var x = 20;
        when_demo(x)
        x = 1
        when_demo(x)
        x = 2
        when_demo(x)
    }

    // no else when all cases are covered
    run {
        val x = Bit.ONE
        val value = when (x) {
            Bit.ZERO -> 0
            Bit.ONE -> 1
            // all cases are covered, so else is not needed.
        }
        println(value)
    }

    run {
        val x = Bit.ONE
        when (x) {
            Bit.ZERO -> println("Bit is 0")
            Bit.ONE -> println("Bit is 1")
            // all cases are covered, so else is not needed.
        }
    }

    // else
    run {
        val x = Bit.ONE
        when (x) {
            Bit.ZERO -> println("Bit is 0")
            else -> println("Bit is not 1")
        }
    }

    // combine conditions
    run {
        var x = 1;
        when (x) {
            0, 1 -> println("x = 0 or x = 1")
            else -> println("x is others")
        }
    }


    // Use any expression (not only constants) as branch conditions
    run {
        val x = 2
        val s = "2"

        when (x) {
            s.toInt() -> println("s encodes x")
            else -> println("s not encodes x")
        }
    }

    // check a value for being in or !in a range or a collection
    run {
        val x = 5
        val validNums = IntArray(3) {
            it * 5
        }
        println(validNums.contentToString())
        when (x) {
            in 1..10 -> println("x is in range") // 这个条件满足，就退出了。不会再继续判断其他条件
            in validNums -> println("x is in valid nums")
            !in 10..20 -> println("x is outside range")
            else -> println("non of the range")
        }
    }

    // check type : is or !is
    run {
        var x: Any = 5;
        println(hasPrefix(x));

        x = "prefix697544"
        println(hasPrefix(x))
    }
}

fun when_demo(x: Any) {
    when (x) {
        1 -> println("x = 1")
        2 -> {
            println("x = 2[1]")
            println("x = 2[2]")
        }

        else -> {
            println("x is neither 1 nor 2")
        }
    }
}

fun hasPrefix(x: Any) = when (x) {
    is String -> x.startsWith("prefix")
    else -> false
}

enum class Bit {
    ZERO,
    ONE
}

// When, END