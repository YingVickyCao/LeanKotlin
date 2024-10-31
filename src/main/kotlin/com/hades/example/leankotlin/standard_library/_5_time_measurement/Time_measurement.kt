package com.hades.example.leankotlin.standard_library._5_time_measurement

import com.hades.example.leankotlin.concepts._1_types._2_data_type._1_basic_types._1_5_file_name._custom_class_name.f
import java.awt.SystemColor
import kotlin.time.*
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.microseconds
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds

// https://kotlinlang.org/docs/time-measurement.html
// Time measurement
fun main() {
//    test1_1()
//    test1_2()
//    test1_3()
//    test1_4()
//    test1_5()
//    test2_1()
//    test2_2()
//    test2_3()
    test3_1()
    test3_2()
}
/**
 * Calculate duration
 */

/**
 * Calculate duration - Create duration
 */
private fun test1_1() {
    fun test1_1_example1() {
        val negativeNanosecond: Duration = (-1).nanoseconds
        println(negativeNanosecond) // -1ns

        val fiveHundredMilliseconds: Duration = 500.milliseconds
        println(fiveHundredMilliseconds) // 500ms

        val zeroSeconds: Duration = 0.seconds
        println(zeroSeconds) // 0s

        val tenMinutes: Duration = 10.minutes
        println(tenMinutes) // 10m

        val oneHour: Duration = 1.hours
        println(oneHour) // 1h

        val infiniteDays: Duration = Double.POSITIVE_INFINITY.days
        println(infiniteDays) // Infinity

        val negativeInfiniteDays: Duration = Double.NEGATIVE_INFINITY.days
        println(negativeInfiniteDays) // -Infinity
    }

    fun test1_1_example2() {
        // perform basic arithmetic with Duration
        val fiveSeconds: Duration = 5.seconds
        val thirtySeconds: Duration = 30.seconds

        println(fiveSeconds + thirtySeconds) // 35s
        println(thirtySeconds - fiveSeconds) // 25s
        println(fiveSeconds * 2) //10s
        println(thirtySeconds / 2) // 15s
        println(thirtySeconds / fiveSeconds) // 6.0
        println(-thirtySeconds) // -30s
        println((-thirtySeconds).absoluteValue) // 30s
    }
    test1_1_example1()
    test1_1_example2()
}

/**
 * Calculate duration - Get string representation
 */
private fun test1_2() {
    // configure the output

    fun test1_2_example1() {
        // Print in seconds with 2 decimal places
        println(5887.milliseconds.toString(DurationUnit.SECONDS, 2)) // 5.89s
    }

    fun test1_2_example2() {
        // Get an ISO-8601-compatible string
        println(86420.seconds.toIsoString()) // PT24H0M20S
        println(60.seconds.toIsoString()) // PT1M
    }
    test1_2_example1()
    test1_2_example2()
}

/**
 * Calculate duration - Convert duration
 */
private fun test1_3() {
    // convert Duration into diferent DurationUnit
    fun test1_3_example1() {
        val thirtyMinutes: Duration = 30.minutes
        println(thirtyMinutes.inWholeSeconds) // 1800
    }

    fun test1_3_example2() {
        println(270.seconds.toDouble(DurationUnit.MINUTES)) // 4.5
        println(60.seconds.toDouble(DurationUnit.MINUTES)) // 1.0
        println(61.seconds.toDouble(DurationUnit.MINUTES)) // 1.0166666666666666
    }
    test1_3_example1()
    test1_3_example2()
}

/**
 * Calculate duration - Compare duration
 */
private fun test1_4() {
    fun test1_4_example1() {
        // To check if Duration object are equal
        val thirtyMinutes: Duration = 30.minutes
        val halfHour: Duration = 0.5.hours
        println(thirtyMinutes == halfHour) // true
    }

    fun test1_4_example2() {
        // To compare Duration objects
        println(3000.microseconds < 25000.nanoseconds) // false
    }
    test1_4_example1()
    test1_4_example2()
}

/**
 * Calculate duration - Break duration into components
 */
private fun test1_5() {
    val thirtyMinutes: Duration = 30.minutes
    println(thirtyMinutes.toComponents { hours: Long, minutes: Int, seconds: Int, nanoseconds: Int -> "${hours}h:${minutes}m:${seconds}s:${nanoseconds}ns" }) // 0h:30m:0s:0ns
}

/**
 * Measure time
 */

/**
 * Measure time - Measure code execution time
 */
private fun test2_1() {
    fun test2_1_example1() {
        // To measure the time taken to execute a block of code,use the measureTime inline function
        val timeTaken = measureTime {
            Thread.sleep(100)
        }
        println(timeTaken) // 105.258792ms
    }

    fun test2_1_example2() {
        // To measure the time taken to execute a block of code and return the value of the block of code, use inline function measureTimedValue.
        val (value, timeTaken) = measureTimedValue {
            Thread.sleep(100)
            42
        }
        println(value) // 42
        println(timeTaken) // 102.910125ms
    }
    // By default, both functions use a monotonic time source.
    test2_1_example1()
    test2_1_example2()
}

/**
 * TODO:Measure time - Mark moments in time
 */
private fun test2_2() {
    // To mark a specific moment in time, use the TimeSource interface and the markNow() function to create a TimeMark:
    val timeSource = TimeSource.Monotonic
    val mark = timeSource.markNow()
    println(mark) // ValueTimeMark(reading=1333)
}

/**
 * Measure time - Measure differences in time
 */
private fun test2_3() {
    fun test2_3_example1() {
        // To measure differences between TimeMark objects from the same time source, use the subtraction operator (-).
        // To compare TimeMark objects from the same time source, use the comparison operators (<, >).
        val timeSource = TimeSource.Monotonic
        val mark1 = timeSource.markNow()
        Thread.sleep(500)
        val mark2 = timeSource.markNow()
        repeat(4) { n ->
            val mark3 = timeSource.markNow()
            val elapsed1 = mark3 - mark2
            val elapsed2 = mark2 - mark1
            println("Measurement 1.${n + 1}:elapsed1=$elapsed1,elapsed2=$elapsed2,diff=${elapsed2 - elapsed1}")
        }
        println(mark2 > mark1)

        // log:
        // Measurement 1.1:elapsed1=6.667us,elapsed2=503.958292ms,diff=503.951625ms
        // Measurement 1.2:elapsed1=35.976542ms,elapsed2=503.958292ms,diff=467.981750ms
        // Measurement 1.3:elapsed1=36.031417ms,elapsed2=503.958292ms,diff=467.926875ms
        // Measurement 1.4:elapsed1=36.077292ms,elapsed2=503.958292ms,diff=467.881ms
        // true
    }

    fun test2_3_example2() {
        // To check if a deadline has passed or a timeout has been reached, use the hasPassedNow() and hasNotPassedNow() extension functions:
        val timeSource = TimeSource.Monotonic
        val mark1 = timeSource.markNow()
        val fiveSeconds: Duration = 5.seconds
        val mark2 = mark1 + fiveSeconds

        // It hasn't pass 5 seconds yet.
        println(mark2.hasPassedNow()) // false

        Thread.sleep(6000) // wait 6 seconds
        println(mark2.hasPassedNow()) // true
    }
    test2_3_example1()
    test2_3_example2()
}

/**
 * Time sources
 */
/**
 * Time sources - Default time sources per platform
 */
private fun test3_1() {
    // Use different time source
    // in Android, System.nanoTime() only counts time while the device is active. It loses track of time when the device enters deep sleep.
    println(System.nanoTime()) // 4305728161625

    // To keep track of time while the device is in deep sleep, you can create a time source that uses SystemClock.elapsedRealtimeNanos():
//    object RealtimeMonotonicTimeSource:AbstractLongTimeSource(DurationUnit.NANOSECONDS){
//        override fun read(): Long {
//            return SystemClock.elapsedRealtimeNanos()
//        }
//
//    }
//    val elapsed:Duration = RealtimeMonotonicTimeSource.measureTime {
//        Thread.sleep(100)
//    }
//    println(elapsed) // e.g. 103 ms
}

/**
 * Time sources - Create time source
 */
private fun test3_2() {

}