package com.hades.example.leankotlin.standard_library._5_time_measurement

import com.hades.example.leankotlin.concepts._1_types._2_data_type._1_basic_types._1_5_file_name._custom_class_name.f
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.microseconds
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

// https://kotlinlang.org/docs/time-measurement.html
// Time measurement
fun main() {
//    test1_1()
//    test1_2()
//    test1_3()
//    test1_4()
    test1_5()
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

}

/**
 * Measure time
 */

/**
 * Measure time - Measure code execution time
 */
/**
 * Measure time - Mark moments in time
 */

/**
 * Measure time - Measure differences in time
 */

/**
 * Time sources
 */
/**
 * Time sources - Default time sources per platform
 */
/**
 * Time sources - Create time source
 */